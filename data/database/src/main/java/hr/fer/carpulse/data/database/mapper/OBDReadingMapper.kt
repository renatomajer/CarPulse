package hr.fer.carpulse.data.database.mapper

import hr.fer.carpulse.data.database.trip.obd.OBDReadingEntity
import hr.fer.carpulse.domain.common.obd.OBDReading

class OBDReadingMapper {
    fun toEntity(data: OBDReading, tripUUID: String): OBDReadingEntity {
        return OBDReadingEntity(
            rpm = data.rpm,
            speed = data.speed,
            relativeThrottlePosition = data.relativeThrottlePosition,
            absoluteThrottlePositionB = data.absoluteThrottlePositionB,
            throttlePosition = data.throttlePosition,
            acceleratorPedalPositionE = data.acceleratorPedalPositionE,
            engineLoad = data.engineLoad,
            acceleratorPedalPositionD = data.acceleratorPedalPositionD,
            timestamp = data.timestamp,
            tripUUID = tripUUID
        )
    }

    fun fromEntity(entity: OBDReadingEntity): OBDReading {
        return OBDReading(
            rpm = entity.rpm,
            speed = entity.speed,
            relativeThrottlePosition = entity.relativeThrottlePosition,
            absoluteThrottlePositionB = entity.absoluteThrottlePositionB,
            throttlePosition = entity.throttlePosition,
            acceleratorPedalPositionE = entity.acceleratorPedalPositionE,
            engineLoad = entity.engineLoad,
            acceleratorPedalPositionD = entity.acceleratorPedalPositionD,
            timestamp = entity.timestamp
        )
    }
}