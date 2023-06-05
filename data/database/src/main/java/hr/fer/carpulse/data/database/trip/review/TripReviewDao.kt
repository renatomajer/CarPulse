package hr.fer.carpulse.data.database.trip.review

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
abstract class TripReviewDao : ITripReviewDao {

    @Query("SELECT * FROM trip_review WHERE tripUUID = :tripUUID")
    abstract override fun get(tripUUID: String): Flow<TripReviewEntity>

    @Insert
    abstract override suspend fun insert(tripReviewEntity: TripReviewEntity)

    @Delete
    abstract override fun delete(tripReviewEntity: TripReviewEntity)
}