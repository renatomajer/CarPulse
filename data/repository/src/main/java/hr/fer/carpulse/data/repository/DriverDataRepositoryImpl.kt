package hr.fer.carpulse.data.repository

import hr.fer.carpulse.data.api.DataApi
import hr.fer.carpulse.data.database.driver.IDriverDataDao
import hr.fer.carpulse.data.database.mapper.DriverDataMapper
import hr.fer.carpulse.data.database.mapper.trip.TripReviewMapper
import hr.fer.carpulse.data.database.trip.review.ITripReviewDao
import hr.fer.carpulse.domain.common.driver.DriverData
import hr.fer.carpulse.domain.common.trip.TripReview
import hr.fer.carpulse.domain.repointerfaces.DriverDataRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DriverDataRepositoryImpl(
    private val driverDataDao: IDriverDataDao,
    private val driverDataMapper: DriverDataMapper,
    private val tripReviewDao: ITripReviewDao,
    private val tripReviewMapper: TripReviewMapper,
    private val dataApi: DataApi
) : DriverDataRepository {

    override fun getDriverData(): Flow<DriverData> {
        return driverDataDao.get().map { driverDataEntity ->
            driverDataMapper.fromEntity(driverDataEntity)
        }
    }

    override suspend fun insertDriverData(driverData: DriverData) {
        val entity = driverDataMapper.toEntity(driverData)
        driverDataDao.insert(entity)
    }

    override fun sendDriverData(driverData: DriverData) {
        dataApi.sendDriverData(driverData)
    }

    override fun sendTripReview(tripReview: TripReview) {
        dataApi.sendTripReview(tripReview)
    }

    override fun getTripReview(tripUUID: String): Flow<TripReview> {
        return tripReviewDao.get(tripUUID).map { tripReviewEntity ->
            tripReviewMapper.fromEntity(tripReviewEntity)
        }
    }

    override suspend fun insertTripReview(tripReview: TripReview) {
        val entity = tripReviewMapper.toEntity(tripReview)
        tripReviewDao.insert(entity)
    }

    override fun deleteTripReview(tripReview: TripReview) {
        val entity = tripReviewMapper.toEntity(tripReview)
        tripReviewDao.delete(entity)
    }
}