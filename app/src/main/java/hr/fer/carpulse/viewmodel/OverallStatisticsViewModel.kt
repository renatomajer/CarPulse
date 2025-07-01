package hr.fer.carpulse.viewmodel

import android.app.Application
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import hr.fer.carpulse.domain.common.driver.DriverStatistics
import hr.fer.carpulse.domain.repointerfaces.AssistantRepository
import hr.fer.carpulse.domain.repointerfaces.DataStoreRepository
import hr.fer.carpulse.domain.usecase.driver.GetDriverDataUseCase
import hr.fer.carpulse.domain.usecase.driver.GetDriverStatisticsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.Locale

class OverallStatisticsViewModel(
    private val dataStoreRepository: DataStoreRepository,
    private val getDriverStatisticsUseCase: GetDriverStatisticsUseCase,
    private val getDriverDataUseCase: GetDriverDataUseCase,
    private val assistantRepository: AssistantRepository,
    application: Application
) : AndroidViewModel(application) {

    var driverStatistics by mutableStateOf<DriverStatistics?>(null)
        private set

    var isLoading by mutableStateOf(true)
        private set

    var carImageIndex by mutableStateOf(0)
        private set

    private var driverId: String? = null

    var isFetchingAssistantResponse by mutableStateOf(false)
        private set

    var isSpeaking by mutableStateOf(false)
        private set

    private var textToSpeech: TextToSpeech? = null

    private val ttsInitListener = TextToSpeech.OnInitListener { status ->
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
        viewModelScope.launch {
            carImageIndex = dataStoreRepository.retrieveCarImageIndex().first()
            driverId = getDriverDataUseCase().first().email
            driverStatistics = getDriverStatisticsUseCase()
            isLoading = false
        }

        textToSpeech = TextToSpeech(this.getApplication(), ttsInitListener)
    }

    fun getAssistantAnalysis() {
        viewModelScope.launch(Dispatchers.IO) {
            if (driverId == null) {
                return@launch
            }

            isFetchingAssistantResponse = true
            val assistantResponse =
                assistantRepository.getOverallStatisticsOverview(userId = driverId!!)
            isFetchingAssistantResponse = false

            assistantResponse?.let {
                speak(it)
            }
        }
    }

    private fun speak(text: String) {
        textToSpeech?.speak(text, TextToSpeech.QUEUE_FLUSH, null, text.hashCode().toString())
    }

    override fun onCleared() {
        textToSpeech?.stop()
        textToSpeech?.shutdown()

        super.onCleared()
    }
}