package hr.fer.carpulse.data.repository

import hr.fer.carpulse.data.api.Api
import hr.fer.carpulse.data.database.IDriverDataDao
import hr.fer.carpulse.data.database.mapper.DriverDataMapper
import hr.fer.carpulse.domain.common.driver.DriverData
import hr.fer.carpulse.domain.common.trip.TripReview
import hr.fer.carpulse.domain.repointerfaces.DriverDataRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DriverDataRepositoryImpl(
    private val driverDataDao: IDriverDataDao,
    private val mapper: DriverDataMapper,
    private val api: Api
) : DriverDataRepository {

    override fun getDriverData(): Flow<DriverData> {
        return driverDataDao.get().map { driverDataEntity ->
            mapper.fromEntity(driverDataEntity)
        }
    }

    override suspend fun insertDriverData(driverData: DriverData) {
        val entity = mapper.toEntity(driverData)
        driverDataDao.insert(entity)
    }

    override fun sendDriverData(driverData: DriverData) {
        api.sendDriverData(driverData)
    }

    override fun sendTripReview(tripReview: TripReview) {
        api.sendTripReview(tripReview)
    }
}