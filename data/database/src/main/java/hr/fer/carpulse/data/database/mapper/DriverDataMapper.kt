package hr.fer.carpulse.data.database.mapper

import hr.fer.carpulse.data.database.driver.DriverDataEntity
import hr.fer.carpulse.domain.common.driver.DriverData

class DriverDataMapper : EntityMapper<DriverData, DriverDataEntity> {

    override fun toEntity(data: DriverData): DriverDataEntity {
        return DriverDataEntity(
            email = data.email,
            age = data.age,
            gender = data.gender,
            fuelType = data.fuelType,
            vehicleType = data.vehicleType,
            vehicleProductionYear = data.vehicleProductionYear,
            vehicleMotorPower = data.vehicleMotorPower,
            startStopSystem = data.startStopSystem,
            drivingAge = data.drivingAge,
            drivingInPrivateVehicle = data.drivingInPrivateVehicle,
            driverInPrivateVehicle = data.driverInPrivateVehicle,
            fuelConsumptionOptimisation = data.fuelConsumptionOptimisation,
            drivingCrowdedRoads = data.drivingCrowdedRoads,
            comfort = data.comfort,
            security = data.security,
            sportsStyle = data.sportsStyle,
            fuelEfficiency = data.fuelEfficiency,
            neutralTraffic = data.neutralTraffic
        )
    }

    override fun fromEntity(entity: DriverDataEntity): DriverData {
        return DriverData(
            email = entity.email,
            age = entity.age,
            gender = entity.gender,
            fuelType = entity.fuelType,
            vehicleType = entity.vehicleType,
            vehicleProductionYear = entity.vehicleProductionYear,
            vehicleMotorPower = entity.vehicleMotorPower,
            startStopSystem = entity.startStopSystem,
            drivingAge = entity.drivingAge,
            drivingInPrivateVehicle = entity.drivingInPrivateVehicle,
            driverInPrivateVehicle = entity.driverInPrivateVehicle,
            fuelConsumptionOptimisation = entity.fuelConsumptionOptimisation,
            drivingCrowdedRoads = entity.drivingCrowdedRoads,
            comfort = entity.comfort,
            security = entity.security,
            sportsStyle = entity.sportsStyle,
            fuelEfficiency = entity.fuelEfficiency,
            neutralTraffic = entity.neutralTraffic
        )
    }
}