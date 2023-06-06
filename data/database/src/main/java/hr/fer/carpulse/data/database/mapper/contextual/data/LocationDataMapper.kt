package hr.fer.carpulse.data.database.mapper.contextual.data

import hr.fer.carpulse.data.database.trip.contextual.data.LocationDataEntity
import hr.fer.carpulse.domain.common.contextual.data.LocationData

class LocationDataMapper {
    fun toEntity(data: LocationData, tripUUID: String): LocationDataEntity {
        return LocationDataEntity(
            tripUUID = tripUUID,
            longitude = data.longitude,
            latitude = data.latitude,
            altitude = data.altitude,
            timestamp = data.timestamp
        )
    }

    fun fromEntity(entity: LocationDataEntity): LocationData {
        return LocationData(
            longitude = entity.longitude,
            latitude = entity.latitude,
            altitude = entity.altitude,
            timestamp = entity.timestamp
        )
    }
}