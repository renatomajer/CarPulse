package hr.fer.carpulse.ui.model

data class TripSummaryUIModel(
    val tripUUID: String,
    val startDate: String,
    val startTime: String,
    val distance: Double,
    val duration: Int,
    val uploadState: TripUploadState
)
