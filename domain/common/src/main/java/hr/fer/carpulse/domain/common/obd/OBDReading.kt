package hr.fer.carpulse.domain.common.obd

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient


@Serializable
data class OBDReading(
    @SerialName("ENGINE_RPM")
    val rpm: String = NO_DATA,
    @SerialName("SPEED")
    val speed: String = NO_DATA,
    @SerialName("Relative Throttle Position")
    val relativeThrottlePosition: String = NO_DATA,
    @SerialName("Absolute Throttle Position B")
    val absoluteThrottlePositionB: String = NO_DATA,
    @SerialName("THROTTLE_POS")
    val throttlePosition: String = NO_DATA,
    @SerialName("Accelerator Pedal Position E")
    val acceleratorPedalPositionE: String = NO_DATA,
    @SerialName("ENGINE_LOAD")
    val engineLoad: String = NO_DATA,
    @SerialName("Accelerator Pedal Position D")
    val acceleratorPedalPositionD: String = NO_DATA,
//    @Transient
//    val timestamp: Long = 0L
    @Transient
    val timestamp: Long = System.currentTimeMillis()
) {
    companion object {
        const val NO_DATA = "No Data"
        const val NA = "NA"

        fun createPublishData(data: OBDReading): OBDReading {
            return OBDReading(
                rpm = if(data.rpm == NO_DATA) "0" else data.rpm.removeSuffix("RPM"),
                speed = if(data.speed == NO_DATA) "0" else data.speed.removeSuffix("km/h"),
                relativeThrottlePosition = data.relativeThrottlePosition,
                absoluteThrottlePositionB = data.absoluteThrottlePositionB,
                throttlePosition = data.throttlePosition,
                acceleratorPedalPositionE = data.acceleratorPedalPositionE,
                engineLoad = data.engineLoad,
                acceleratorPedalPositionD = data.acceleratorPedalPositionD,
                timestamp = data.timestamp
            )
        }
    }
}
