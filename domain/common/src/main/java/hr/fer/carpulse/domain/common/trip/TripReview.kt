package hr.fer.carpulse.domain.common.trip

import kotlinx.serialization.Serializable

@Serializable
data class TripReview(
    val time: String = "",
    val email: String = "",
    val tripId: String = "",
    val startStopSystem: String = "",
    val suddenAcceleration: Int = -1,
    val suddenDecelaration: Int = -1,
    val efficiencyKnowledge: Boolean = false,
    val efficiencyEstimation: Int = -1,
    val comment: String = ""
)
