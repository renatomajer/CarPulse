package hr.fer.carpulse.viewmodel

import android.app.Application
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import hr.fer.carpulse.domain.common.contextual.data.Coordinate
import hr.fer.carpulse.domain.common.trip.TripDetails
import hr.fer.carpulse.domain.repointerfaces.AssistantRepository
import hr.fer.carpulse.domain.usecase.driver.GetDriverDataUseCase
import hr.fer.carpulse.domain.usecase.trip.GetTripDetailsUseCase
import hr.fer.carpulse.domain.usecase.trip.contextual.data.GetTripRouteUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.Locale

class TripDetailsViewModel(
    private val getTripRouteUseCase: GetTripRouteUseCase,
    private val getTripDetailsUseCase: GetTripDetailsUseCase,
    private val getDriverDataUseCase: GetDriverDataUseCase,
    private val assistantRepository: AssistantRepository,
    application: Application
) : AndroidViewModel(application) {

    var tripCoordinates by mutableStateOf<List<Coordinate>?>(null)
        private set

    var isLoading by mutableStateOf(true)
        private set

    var tripDetails by mutableStateOf<TripDetails?>(null)
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
            driverId = getDriverDataUseCase().first().email
        }

        textToSpeech = TextToSpeech(this.getApplication(), ttsInitListener)
    }

    fun getTripCoordinates(tripUUID: String) {
        viewModelScope.launch {
            tripCoordinates = getTripRouteUseCase(tripUUID)
        }
    }

    fun getTripDetails(tripUUID: String) {
        viewModelScope.launch {
            tripDetails = getTripDetailsUseCase(tripUUID)
            isLoading = false
        }
    }

    fun getAssistantAnalysis(tripUUID: String) {
        viewModelScope.launch(Dispatchers.IO) {
            if (driverId == null) {
                return@launch
            }

            isFetchingAssistantResponse = true
            val assistantResponse =
                assistantRepository.getTripOverview(userId = driverId!!, tripId = tripUUID)
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