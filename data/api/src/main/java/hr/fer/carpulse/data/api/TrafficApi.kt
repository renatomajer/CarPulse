package hr.fer.carpulse.data.api

import hr.fer.carpulse.domain.common.contextual.data.TrafficData

interface TrafficApi {
    suspend fun getTrafficFlowData(
        zoom: Int = 10,
        latitude: Double,
        longitude: Double
    ): TrafficData?
}