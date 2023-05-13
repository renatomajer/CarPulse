package hr.fer.carpulse.domain.common.obd


data class OBDReading(
    val rpm: String = NO_DATA,
    val speed: String = NO_DATA,
    val relativeThrottlePosition: String = NO_DATA,
    val absoluteThrottlePositionB: String = NO_DATA,
    val throttlePosition: String = NO_DATA,
    val acceleratorPedalPositionE: String = NO_DATA,
    val engineLoad: String = NO_DATA,
    val acceleratorPedalPositionD: String = NO_DATA,
    val timestamp: Long
) {
    companion object {
        const val NO_DATA = "No Data"
    }
}
