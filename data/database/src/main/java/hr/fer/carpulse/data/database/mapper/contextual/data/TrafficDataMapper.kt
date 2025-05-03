package hr.fer.carpulse.data.database.mapper.contextual.data

import hr.fer.carpulse.data.database.trip.contextual.data.TrafficDataEntity
import hr.fer.carpulse.domain.common.contextual.data.Coordinates
import hr.fer.carpulse.domain.common.contextual.data.FlowSegmentData
import hr.fer.carpulse.domain.common.contextual.data.TrafficData

class TrafficDataMapper {

    fun toEntity(data: TrafficData, tripUUID: String): TrafficDataEntity =
        TrafficDataEntity(
            tripUUID = tripUUID,
            timestamp = data.timestamp,
            frc = data.flowSegmentData.frc,
            currentSpeed = data.flowSegmentData.currentSpeed,
            freeFlowSpeed = data.flowSegmentData.freeFlowSpeed,
            currentTravelTime = data.flowSegmentData.currentTravelTime,
            freeFlowTravelTime = data.flowSegmentData.freeFlowTravelTime,
            confidence = data.flowSegmentData.confidence,
            roadClosure = data.flowSegmentData.roadClosure,
            coordinates = data.flowSegmentData.coordinates.coordinate
        )

    fun fromEntity(entity: TrafficDataEntity): TrafficData {

        val flowSegmentData = FlowSegmentData(
            frc = entity.frc,
            currentSpeed = entity.currentSpeed,
            freeFlowSpeed = entity.freeFlowSpeed,
            currentTravelTime = entity.currentTravelTime,
            freeFlowTravelTime = entity.freeFlowTravelTime,
            confidence = entity.confidence,
            roadClosure = entity.roadClosure,
            coordinates = Coordinates(entity.coordinates)
        )

        return TrafficData(
            timestamp = entity.timestamp,
            flowSegmentData = flowSegmentData
        )
    }
}