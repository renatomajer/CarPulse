package hr.fer.carpulse.domain.repointerfaces

import hr.fer.carpulse.domain.common.driver.DriverData
import kotlinx.coroutines.flow.Flow

interface DriverDataRepository {

    fun getDriverData(): Flow<DriverData>

    suspend fun insertDriverData(driverData: DriverData)
}