package hr.fer.carpulse.domain.common.trip

import kotlinx.serialization.Serializable

@Serializable
data class TripStartInfo(
    val vehicleInfo: VehicleInfo = VehicleInfo(),
    val mobileDeviceInfo: MobileDeviceInfo = MobileDeviceInfo(),
    val tripId: String = "",
    val tripStartTimestamp: String = ""
)
