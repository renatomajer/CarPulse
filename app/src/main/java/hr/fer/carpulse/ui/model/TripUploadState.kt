package hr.fer.carpulse.ui.model

sealed class TripUploadState {
    data object NotUploaded: TripUploadState()
    data object IsUploading: TripUploadState()
    data object Uploaded: TripUploadState()
}