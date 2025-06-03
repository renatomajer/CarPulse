package hr.fer.carpulse.viewmodel

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hr.fer.carpulse.domain.common.assistant.AssistantRequest
import hr.fer.carpulse.domain.repointerfaces.AssistantRepository
import hr.fer.carpulse.domain.usecase.driver.GetDriverDataUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class TalkWithAssistantViewModel(
    private val assistantRepository: AssistantRepository,
    private val getDriverDataUseCase: GetDriverDataUseCase
) : ViewModel() {

    var conversation by mutableStateOf<List<Any>>(emptyList())
        private set

    var isListening by mutableStateOf(false)
        private set

    var fetchingResponse by mutableStateOf(false)
        private set

    private var driverId: String? = null

    init {
        viewModelScope.launch(Dispatchers.IO) {
            driverId = getDriverDataUseCase().first().email
        }
    }

    fun askAssistant(question: String?) {
        viewModelScope.launch(Dispatchers.IO) {

            if(question == null || driverId == null) {
                return@launch
            }

            val assistantRequest = AssistantRequest(
                question = question,
                userId = driverId!!
            )

            conversation = conversation + assistantRequest

            fetchingResponse = true
            val assistantResponse = assistantRepository.askAssistant(assistantRequest)
            fetchingResponse = false

            assistantResponse?.let {
                conversation = conversation + it.copy(response = it.response.trim())
            }
        }
    }

    fun talk(context: Context) {
        if (!SpeechRecognizer.isRecognitionAvailable(context)) {
            // Speech recognition service NOT available
            return
        }
        val speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context)

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
}