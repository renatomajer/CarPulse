package hr.fer.carpulse.viewmodel

import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech
import android.speech.tts.TextToSpeech.OnInitListener
import android.speech.tts.UtteranceProgressListener
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import hr.fer.carpulse.domain.common.assistant.AssistantRequest
import hr.fer.carpulse.domain.repointerfaces.AssistantRepository
import hr.fer.carpulse.domain.usecase.driver.GetDriverDataUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.Locale


class TalkWithAssistantViewModel(
    private val assistantRepository: AssistantRepository,
    private val getDriverDataUseCase: GetDriverDataUseCase,
    application: Application
) : AndroidViewModel(application) {

    var conversation by mutableStateOf<List<Any>>(emptyList())
        private set

    var isListening by mutableStateOf(false)
        private set

    var isFetchingAssistantResponse by mutableStateOf(false)
        private set

    var isSpeaking by mutableStateOf(false)
        private set

    private var driverId: String? = null

    private var textToSpeech: TextToSpeech? = null

    private val ttsInitListener = OnInitListener { status ->
        if (status == TextToSpeech.SUCCESS) {
            // TTS engine is successfully initialized.
            textToSpeech?.setLanguage(Locale.ENGLISH)

            textToSpeech?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                override fun onStart(utteranceId: String?) {
                    isSpeaking = true
                }

                override fun onDone(utteranceId: String?) {
                    isSpeaking = false
                }

                @Deprecated("Deprecated in Java")
                override fun onError(utteranceId: String?) {
                }
            })
        } else {
            // Failed to initialize TTS engine.
        }
    }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            driverId = getDriverDataUseCase().first().email
        }

        textToSpeech = TextToSpeech(this.getApplication(), ttsInitListener)
    }

    fun askAssistant(question: String?) {
        viewModelScope.launch(Dispatchers.IO) {

            if (question == null || driverId == null) {
                return@launch
            }

            val assistantRequest = AssistantRequest(
                question = question,
                userId = driverId!!
            )

            conversation = conversation + assistantRequest

            isFetchingAssistantResponse = true
            val assistantResponse = assistantRepository.askAssistant(assistantRequest)
            isFetchingAssistantResponse = false

            assistantResponse?.let {
                conversation = conversation + it.copy(response = it.response.trim())
                speak(it.response)
            }
        }
    }

    fun talk() {
        if (!SpeechRecognizer.isRecognitionAvailable(this.getApplication())) {
            // Speech recognition service NOT available
            return
        }
        val speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this.getApplication())

        speechRecognizer.setRecognitionListener(object : RecognitionListener {

            override fun onReadyForSpeech(params: Bundle?) {
                isListening = true
            }

            override fun onBeginningOfSpeech() {}

            override fun onRmsChanged(rmsdB: Float) {}

            override fun onBufferReceived(buffer: ByteArray?) {}

            override fun onEndOfSpeech() {}

            override fun onError(error: Int) {}

            override fun onPartialResults(partialResults: Bundle?) {}

            override fun onEvent(eventType: Int, params: Bundle?) {}

            override fun onResults(results: Bundle) {
                val data: ArrayList<String>? =
                    results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                Log.d("debug_log", "Speech recognition results received: $data")
                askAssistant(question = data?.toString())
                isListening = false
                speechRecognizer.destroy()
            }
        })

        val recognizerIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
        }

        speechRecognizer.startListening(recognizerIntent)
    }


    private fun speak(text: String) {
        textToSpeech?.speak(text, TextToSpeech.QUEUE_FLUSH, null, text.hashCode().toString())
    }

    override fun onCleared() {
        //TODO: kill assistant
        textToSpeech?.stop()
        textToSpeech?.shutdown()

        super.onCleared()
    }
}