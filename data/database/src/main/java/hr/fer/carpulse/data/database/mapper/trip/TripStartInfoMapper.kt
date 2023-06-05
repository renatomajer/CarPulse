package hr.fer.carpulse.data.database.mapper.trip

import hr.fer.carpulse.data.database.mapper.EntityMapper
import hr.fer.carpulse.data.database.trip.startInfo.TripStartInfoEntity
import hr.fer.carpulse.domain.common.trip.MobileDeviceInfo
import hr.fer.carpulse.domain.common.trip.TripStartInfo
import hr.fer.carpulse.domain.common.trip.VehicleInfo

class TripStartInfoMapper : EntityMapper<TripStartInfo, TripStartInfoEntity> {
    override fun toEntity(data: TripStartInfo): TripStartInfoEntity {
        return TripStartInfoEntity(
            pids01_20 = data.vehicleInfo.pids01_20,
            fuelType = data.vehicleInfo.fuelType,
            pids21_40 = data.vehicleInfo.pids21_40,
            pids41_60 = data.vehicleInfo.pids41_60,
            vehicle = data.vehicleInfo.vehicle,
            appVersion = data.mobileDeviceInfo.appVersion,
            deviceName = data.mobileDeviceInfo.deviceName,
            operator = data.mobileDeviceInfo.operator,
            fingerprint = data.mobileDeviceInfo.fingerprint,
            androidId = data.mobileDeviceInfo.androidId,
            tripUUID = data.tripId,
            tripStartTimestamp = data.tripStartTimestamp
        )
    }

    override fun fromEntity(entity: TripStartInfoEntity): TripStartInfo {

        val vehicleInfo = VehicleInfo(
            pids01_20 = entity.pids01_20,
            fuelType = entity.fuelType,
            pids21_40 = entity.pids21_40,
            pids41_60 = entity.pids41_60,
            vehicle = entity.vehicle,
        )

        val mobileDeviceInfo = MobileDeviceInfo(
            appVersion = entity.appVersion,
            deviceName = entity.deviceName,
            operator = entity.operator,
            fingerprint = entity.fingerprint,
            androidId = entity.androidId,
        )

        return TripStartInfo(
            vehicleInfo = vehicleInfo,
            mobileDeviceInfo = mobileDeviceInfo,
            tripId = entity.tripUUID,
            tripStartTimestamp = entity.tripStartTimestamp
        )
    }
}