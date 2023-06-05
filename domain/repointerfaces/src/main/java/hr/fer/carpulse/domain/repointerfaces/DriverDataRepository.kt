package hr.fer.carpulse.domain.repointerfaces

import hr.fer.carpulse.domain.common.driver.DriverData
import hr.fer.carpulse.domain.common.trip.TripReview
import kotlinx.coroutines.flow.Flow

interface DriverDataRepository {

    fun getDriverData(): Flow<DriverData>

    suspend fun insertDriverData(driverData: DriverData)

    fun sendDriverData(driverData: DriverData)

    fun sendTripReview(tripReview: TripReview)

    fun getTripReview(tripUUID: String): Flow<TripReview>

    suspend fun insertTripReview(tripReview: TripReview)

    fun deleteTripReview(tripReview: TripReview)
}