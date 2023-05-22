package hr.fer.carpulse.data.repository

import hr.fer.carpulse.data.api.Api
import hr.fer.carpulse.domain.common.trip.TripStartInfo
import hr.fer.carpulse.domain.repointerfaces.TripsRepository

class TripsRepositoryImpl(
    private val api: Api
) : TripsRepository {

    override fun sendTripStartInfo(tripStartInfo: TripStartInfo) {
        api.sendTripStartInfo(tripStartInfo)
    }
}