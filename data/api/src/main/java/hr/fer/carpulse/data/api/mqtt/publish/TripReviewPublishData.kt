package hr.fer.carpulse.data.api.mqtt.publish

import hr.fer.carpulse.domain.common.trip.TripReview
import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class TripReviewPublishData(
    val time: Time,
    val email: String,
    val tripId: String,
    val startStopSystem: String,
    val suddenAcceleration: Int,
    val suddenDecelaration: Int,
    val efficiencyKnowledge: Boolean,
    val efficiencyEstimation: Double,
    val comment: String
) {
    companion object {
        fun createPublishData(tripReview: TripReview): List<TripReviewPublishData> {
            return listOf(
                TripReviewPublishData(
                    time = Time(tripReview.time),
                    email = tripReview.email,
                    tripId = tripReview.tripId,
                    startStopSystem = tripReview.startStopSystem,
                    suddenAcceleration = tripReview.suddenAcceleration,
                    suddenDecelaration = tripReview.suddenDecelaration,
                    efficiencyKnowledge = tripReview.efficiencyKnowledge,
                    efficiencyEstimation = tripReview.efficiencyEstimation,
                    comment = tripReview.comment
                )
            )
        }
    }
}

@kotlinx.serialization.Serializable
data class Time(
    @SerialName("\$date")
    val date: String
)

