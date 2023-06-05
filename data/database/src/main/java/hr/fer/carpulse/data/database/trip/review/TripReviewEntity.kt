package hr.fer.carpulse.data.database.trip.review

import androidx.room.Entity

@Entity(tableName = "trip_review", primaryKeys = ["tripUUID"])
data class TripReviewEntity(
    val time: String,
    val email: String,
    val tripUUID: String,
    val startStopSystem: String,
    val suddenAcceleration: Int,
    val suddenDecelaration: Int,
    val efficiencyKnowledge: Boolean,
    val efficiencyEstimation: Double,
    val comment: String
) {
}