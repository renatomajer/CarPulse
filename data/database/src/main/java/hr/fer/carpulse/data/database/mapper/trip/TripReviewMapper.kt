package hr.fer.carpulse.data.database.mapper.trip

import hr.fer.carpulse.data.database.mapper.EntityMapper
import hr.fer.carpulse.data.database.trip.review.TripReviewEntity
import hr.fer.carpulse.domain.common.trip.TripReview

class TripReviewMapper : EntityMapper<TripReview, TripReviewEntity> {
    override fun toEntity(data: TripReview): TripReviewEntity {
        return TripReviewEntity(
            time = data.time,
            email = data.email,
            tripUUID = data.tripId,
            startStopSystem = data.startStopSystem,
            suddenAcceleration = data.suddenAcceleration,
            suddenDecelaration = data.suddenDecelaration,
            efficiencyKnowledge = data.efficiencyKnowledge,
            efficiencyEstimation = data.efficiencyEstimation,
            comment = data.comment
        )
    }

    override fun fromEntity(entity: TripReviewEntity): TripReview {
        return TripReview(
            time = entity.time,
            email = entity.email,
            tripId = entity.tripUUID,
            startStopSystem = entity.startStopSystem,
            suddenAcceleration = entity.suddenAcceleration,
            suddenDecelaration = entity.suddenDecelaration,
            efficiencyKnowledge = entity.efficiencyKnowledge,
            efficiencyEstimation = entity.efficiencyEstimation,
            comment = entity.comment
        )
    }
}