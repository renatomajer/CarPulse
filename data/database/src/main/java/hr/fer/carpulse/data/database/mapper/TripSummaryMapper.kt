package hr.fer.carpulse.data.database.mapper

import hr.fer.carpulse.data.database.trip.TripSummaryEntity
import hr.fer.carpulse.domain.common.trip.TripSummary

class TripSummaryMapper : EntityMapper<TripSummary, TripSummaryEntity> {

    override fun toEntity(data: TripSummary): TripSummaryEntity {
        return TripSummaryEntity(
            tripUUID = data.tripUUID,
            startTimestamp = data.startTimestamp,
            endTimestamp = data.endTimestamp,
            maxRPM = data.maxRPM,
            maxSpeed = data.maxSpeed,
            sent = data.sent
        )
    }

    override fun fromEntity(entity: TripSummaryEntity): TripSummary {
        return TripSummary(
            tripUUID = entity.tripUUID,
            startTimestamp = entity.startTimestamp,
            endTimestamp = entity.endTimestamp,
            maxRPM = entity.maxRPM,
            maxSpeed = entity.maxSpeed,
            sent = entity.sent
        )
    }
}