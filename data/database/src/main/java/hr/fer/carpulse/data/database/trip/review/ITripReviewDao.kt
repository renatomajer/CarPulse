package hr.fer.carpulse.data.database.trip.review

import kotlinx.coroutines.flow.Flow

interface ITripReviewDao {
    fun get(tripUUID: String): Flow<TripReviewEntity>

    suspend fun insert(tripReviewEntity: TripReviewEntity)

    fun delete(tripReviewEntity: TripReviewEntity)
}