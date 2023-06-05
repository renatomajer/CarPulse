package hr.fer.carpulse.data.api.mqtt.publish

import hr.fer.carpulse.domain.common.trip.MobileDeviceInfo
import hr.fer.carpulse.domain.common.trip.TripStartInfo
import hr.fer.carpulse.domain.common.trip.VehicleInfo
import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class TripStartInfoPublishData(
    val vehicleInfo: VehicleInfo,
    val mobileDeviceInfo: MobileDeviceInfo,
    val tripId: String,
    val tripStartTimestamp: TripStartTimestamp
) {
    companion object {
        fun createPublishData(tripStartInfo: TripStartInfo): List<TripStartInfoPublishData> {
            return listOf(
                TripStartInfoPublishData(
                    vehicleInfo = tripStartInfo.vehicleInfo,
                    mobileDeviceInfo = tripStartInfo.mobileDeviceInfo,
                    tripId = tripStartInfo.tripId,
                    tripStartTimestamp = TripStartTimestamp(tripStartInfo.tripStartTimestamp)
                )
            )
        }
    }
}


@kotlinx.serialization.Serializable
data class TripStartTimestamp(
    @SerialName("\$numberLong")
    val numberLong: String
)
