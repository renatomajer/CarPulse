package hr.fer.carpulse.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hr.fer.carpulse.domain.common.trip.StartStopSystem
import hr.fer.carpulse.domain.common.trip.TripReview
import hr.fer.carpulse.domain.usecase.driver.GetDriverDataUseCase
import hr.fer.carpulse.domain.usecase.driver.SendTripReviewUseCase
import hr.fer.carpulse.domain.usecase.mqtt.ConnectToBrokerUseCase
import hr.fer.carpulse.domain.usecase.mqtt.DisconnectFromBrokerUseCase
import hr.fer.carpulse.domain.usecase.preferences.ReadLocalStorageStateUseCase
import hr.fer.carpulse.domain.usecase.trip.review.SaveTripReviewUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class TripReviewScreenViewModel(
    private val sendTripReviewUseCase: SendTripReviewUseCase,
    private val getDriverDataUseCase: GetDriverDataUseCase,
    private val readLocalStorageStateUseCase: ReadLocalStorageStateUseCase,
    private val saveTripReviewUseCase: SaveTripReviewUseCase,
    private val connectToBrokerUseCase: ConnectToBrokerUseCase,
    private val disconnectFromBrokerUseCase: DisconnectFromBrokerUseCase
) : ViewModel() {

    private val _tripReview = MutableStateFlow(TripReview())
    val tripReview = _tripReview.asStateFlow()

    private var storeDataLocally: Boolean = false

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val driverData = getDriverDataUseCase().first()
            _tripReview.value = _tripReview.value.copy(
                email = driverData.email
            )

            storeDataLocally = readLocalStorageStateUseCase().first()

//            if (!storeDataLocally) {
//                connectToBrokerUseCase()
//            }
        }

        val time = Calendar.getInstance().time
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm")
        val current = formatter.format(time)
        _tripReview.value = _tripReview.value.copy(
            time = current
        )
    }

    fun updateStartStopSystem(used: Boolean) {
        _tripReview.value = _tripReview.value.copy(
            startStopSystem = if (used) StartStopSystem.Used.value else StartStopSystem.NotUsed.value
        )
    }

    fun updateSuddenAcceleration(suddenAcceleration: String) {
        val suddenAccelerationNumber: Int
        try {
            suddenAccelerationNumber = if (suddenAcceleration == "") {
                -1
            } else {
                if (suddenAcceleration.toInt() >= 0) {
                    suddenAcceleration.toInt()
                } else {
                    -1
                }
            }

            _tripReview.value = _tripReview.value.copy(
                suddenAcceleration = suddenAccelerationNumber
            )
        } catch (exc: NumberFormatException) {
            // TODO: show error
            Log.d("debug_log", exc.toString())
        }
    }

    fun updateSuddenDeceleration(suddenDeceleration: String) {
        val suddenDecelerationNumber: Int
        try {
            suddenDecelerationNumber = if (suddenDeceleration == "") {
                -1
            } else {
                if (suddenDeceleration.toInt() >= 0) {
                    suddenDeceleration.toInt()
                } else {
                    -1
                }
            }

            _tripReview.value = _tripReview.value.copy(
                suddenDecelaration = suddenDecelerationNumber
            )
        } catch (exc: NumberFormatException) {
            // TODO: show error
            Log.d("debug_log", exc.toString())
        }
    }

    fun updateEfficiencyKnowledge(efficiencyKnowledge: Boolean) {
        _tripReview.value = _tripReview.value.copy(
            efficiencyKnowledge = efficiencyKnowledge
        )
    }

    fun updateEfficiencyEstimation(efficiencyEstimation: String) {
        val efficiencyEstimationNumber: Double
        try {
            efficiencyEstimationNumber = if (efficiencyEstimation == "") {
                -1.0
            } else {
                if (efficiencyEstimation.toDouble() >= 0.0) {
                    efficiencyEstimation.toDouble()
                } else {
                    -1.0
                }
            }

            _tripReview.value = _tripReview.value.copy(
                efficiencyEstimation = efficiencyEstimationNumber
            )
        } catch (exc: NumberFormatException) {
            // TODO: show error
            Log.d("debug_log", exc.toString())
        }
    }

    fun updateComment(comment: String) {
        _tripReview.value = _tripReview.value.copy(
            comment = comment
        )
    }

    fun sendTripReview(tripUUID: String?) {
        _tripReview.value = _tripReview.value.copy(
            tripId = tripUUID ?: ""
        )

        viewModelScope.launch(Dispatchers.IO) {
            if (storeDataLocally) {
                Log.d("debug_log", "Storing trip review locally...")
                saveTripReviewUseCase(_tripReview.value)
            } else {
                Log.d("debug_log", "Sending trip review...")
                sendTripReviewUseCase(_tripReview.value)
            }
        }
    }

    override fun onCleared() {
        if (!storeDataLocally) {
            disconnectFromBrokerUseCase()
        }
        super.onCleared()
    }
}