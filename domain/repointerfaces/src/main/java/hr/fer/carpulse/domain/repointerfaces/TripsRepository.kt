package hr.fer.carpulse.domain.repointerfaces

import hr.fer.carpulse.domain.common.trip.TripStartInfo

interface TripsRepository {
    fun sendTripStartInfo(tripStartInfo: TripStartInfo)
}