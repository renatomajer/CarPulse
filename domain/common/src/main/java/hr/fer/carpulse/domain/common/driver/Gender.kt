package hr.fer.carpulse.domain.common.driver

sealed class Gender(val value: String) {
    object Male : Gender("m")
    object Female : Gender("f")
}
