package hr.fer.carpulse.domain.common.driver

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DriverData(
    @SerialName("Email")
    val email: String = "",
    @SerialName("Age")
    val age: Int = 0,
    @SerialName("Gender")
    val gender: String = "",
    @SerialName("FuelType")
    val fuelType: String = FuelType.Diesel.type,
    @SerialName("VehicleType")
    val vehicleType: String = "",
    @SerialName("VehicleProductionYear")
    val vehicleProductionYear: Int = 0,
    @SerialName("VehicleMotorPower")
    val vehicleMotorPower: Int = 0,
    @SerialName("StartStopSystem")
    val startStopSystem: Boolean = false,
    @SerialName("DrivingAge")
    val drivingAge: Int = 0,
    @SerialName("DrivingInPrivateVehicle")
    val drivingInPrivateVehicle: Int = 0,
    @SerialName("DriverInPrivateVehicle")
    val driverInPrivateVehicle: Int = 0,
    @SerialName("FuelConsumptionOptimisation")
    val fuelConsumptionOptimisation: Int = 0,
    @SerialName("DrivingCrowdedRoads")
    val drivingCrowdedRoads: Int = 0,
    @SerialName("Comfort")
    val comfort: String = "",
    @SerialName("Security")
    val security: String = "",
    @SerialName("SportStyle")
    val sportsStyle: String = "",
    @SerialName("FuelEfficiency")
    val fuelEfficiency: String = "",
    @SerialName("NeutralTraffic")
    val neutralTraffic: String = ""
)