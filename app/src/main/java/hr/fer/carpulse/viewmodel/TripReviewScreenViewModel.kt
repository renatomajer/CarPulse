package hr.fer.carpulse.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hr.fer.carpulse.domain.common.trip.StartStopSystem
import hr.fer.carpulse.domain.common.trip.TripReview
import hr.fer.carpulse.domain.usecase.driver.GetDriverDataUseCase
import hr.fer.carpulse.domain.usecase.driver.SendTripReviewUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class TripReviewScreenViewModel(
    private val sendTripReviewUseCase: SendTripReviewUseCase,
    private val getDriverDataUseCase: GetDriverDataUseCase
) : ViewModel() {

    private val _tripReview = MutableStateFlow(TripReview())
    val tripReview = _tripReview.asStateFlow()

    init {
        viewModelScope.launch {
            getDriverDataUseCase().collect { driverData ->
                _tripReview.value = _tripReview.value.copy(
                    email = driverData.email
                )
            }
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
        val efficiencyEstimationNumber: Int
        try {
            efficiencyEstimationNumber = if (efficiencyEstimation == "") {
                -1
            } else {
                if (efficiencyEstimation.toInt() >= 0) {
                    efficiencyEstimation.toInt()
                } else {
                    -1
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
        sendTripReviewUseCase(_tripReview.value)
    }
}