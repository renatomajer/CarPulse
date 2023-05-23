package hr.fer.carpulse.data.database.driver

import kotlinx.coroutines.flow.Flow

interface IDriverDataDao {

    fun get(): Flow<DriverDataEntity>

    fun insert(driverData: DriverDataEntity)

}