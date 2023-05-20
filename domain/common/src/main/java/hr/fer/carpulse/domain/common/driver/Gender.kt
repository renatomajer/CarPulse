package hr.fer.carpulse.domain.common.driver

sealed class Gender(val value: String) {
    object Male : Gender("Male")
    object Female : Gender("Female")
}
