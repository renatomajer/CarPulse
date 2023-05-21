package hr.fer.carpulse.domain.common.trip

sealed class StartStopSystem(val value: String) {
    object Used: StartStopSystem("used")
    object NotUsed: StartStopSystem("not used")
}
