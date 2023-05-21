package hr.fer.carpulse.domain.common.driver

import kotlinx.serialization.Serializable

@Serializable
data class DriverData(
    val email: String = "",
    val age: Int = 0,
    val gender: String = "",
    val fuelType: String = FuelType.Diesel.type,
    val vehicleType: String = "",
    val vehicleProductionYear: Int = 0,
    val vehicleMotorPower: Int = 0,
    val startStopSystem: Boolean = false,
    val drivingAge: Int = 0,
    val drivingInPrivateVehicle: Int = 0,
    val driverInPrivateVehicle: Int = 0,
    val fuelConsumptionOptimisation: Int = 0,
    val drivingCrowdedRoads: Int = 0,
    val comfort: String = "",
    val security: String = "",
    val sportsStyle: String = "",
    val fuelEfficiency: String = "",
    val neutralTraffic: String = ""
)