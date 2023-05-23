package hr.fer.carpulse.data.database.driver

import androidx.room.Entity

@Entity(tableName = "driver_data", primaryKeys = ["email"])
data class DriverDataEntity(
    val email: String,
    val age: Int,
    val gender: String,
    val fuelType: String,
    val vehicleType: String,
    val vehicleProductionYear: Int,
    val vehicleMotorPower: Int,
    val startStopSystem: Boolean,
    val drivingAge: Int,
    val drivingInPrivateVehicle: Int,
    val driverInPrivateVehicle: Int,
    val fuelConsumptionOptimisation: Int,
    val drivingCrowdedRoads: Int,
    val comfort: String,
    val security: String,
    val sportsStyle: String,
    val fuelEfficiency: String,
    val neutralTraffic: String
)
