package hr.fer.carpulse.domain.common.trip

import kotlinx.serialization.Serializable

@Serializable
data class VehicleInfo(
    val pids01_20: String = "",
    val fuelType: String = "",
    val pids21_40: String = "",
    val pids41_60: String = "",
    val vehicle: String = ""
) {
}