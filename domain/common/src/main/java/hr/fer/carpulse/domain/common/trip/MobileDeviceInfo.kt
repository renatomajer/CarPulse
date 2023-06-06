package hr.fer.carpulse.domain.common.trip

import kotlinx.serialization.Serializable

@Serializable
data class MobileDeviceInfo(
    val appVersion: String = "",
    val deviceName: String = "",
    val operator: String = "",
    val fingerprint: String = "",
    val androidId: String = ""
)
