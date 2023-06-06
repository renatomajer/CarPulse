package hr.fer.carpulse.data.repository

import hr.fer.carpulse.data.api.DataApi
import hr.fer.carpulse.data.api.ServicesApi
import hr.fer.carpulse.data.api.WeatherApi
import hr.fer.carpulse.data.database.mapper.OBDReadingMapper
import hr.fer.carpulse.data.database.mapper.contextual.data.LocationDataMapper
import hr.fer.carpulse.data.database.mapper.contextual.data.WeatherDataMapper
import hr.fer.carpulse.data.database.mapper.trip.TripStartInfoMapper
import hr.fer.carpulse.data.database.mapper.trip.TripSummaryMapper
import hr.fer.carpulse.data.database.trip.ITripSummaryDao
import hr.fer.carpulse.data.database.trip.contextual.data.ILocationDataDao
import hr.fer.carpulse.data.database.trip.contextual.data.IWeatherDataDao
import hr.fer.carpulse.data.database.trip.obd.IOBDReadingsDao
import hr.fer.carpulse.data.database.trip.startInfo.ITripStartInfoDao
import hr.fer.carpulse.domain.common.contextual.data.LocationData
import hr.fer.carpulse.domain.common.contextual.data.WeatherData
import hr.fer.carpulse.domain.common.obd.OBDReading
import hr.fer.carpulse.domain.common.trip.TripStartInfo
import hr.fer.carpulse.domain.common.trip.TripSummary
import hr.fer.carpulse.domain.repointerfaces.TripsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

class TripsRepositoryImpl(
    private val tripSummaryDao: ITripSummaryDao,
    private val tripStartInfoDao: ITripStartInfoDao,
    private val obdReadingsDao: IOBDReadingsDao,
    private val locationDataDao: ILocationDataDao,
    private val weatherDataDao: IWeatherDataDao,
    private val tripSummaryMapper: TripSummaryMapper,
    private val tripStartInfoMapper: TripStartInfoMapper,
    private val obdReadingMapper: OBDReadingMapper,
    private val locationDataMapper: LocationDataMapper,
    private val weatherDataMapper: WeatherDataMapper,
    private val dataApi: DataApi,
    private val weatherApi: WeatherApi,
    private val servicesApi: ServicesApi
) : TripsRepository {

    override fun connectToMqttBroker() {
        dataApi.connectToMqttBroker()
    }

    override fun disconnectFromMqttBroker() {
        dataApi.disconnectFromMqttBroker()
    }

    override fun sendTripStartInfo(tripStartInfo: TripStartInfo) {
        dataApi.sendTripStartInfo(tripStartInfo)
    }

    override suspend fun insertTripStartInfo(tripStartInfo: TripStartInfo) {
        val entity = tripStartInfoMapper.toEntity(tripStartInfo)
        tripStartInfoDao.insert(entity)
    }

    override fun getTripStartInfo(tripUUID: String): Flow<TripStartInfo> {
        return tripStartInfoDao.get(tripUUID).map { tripStartInfoEntity ->
            tripStartInfoMapper.fromEntity(tripStartInfoEntity)
        }
    }

    override fun deleteTripStartInfo(tripStartInfo: TripStartInfo) {
        val entity = tripStartInfoMapper.toEntity(tripStartInfo)
        tripStartInfoDao.delete(entity)
    }

    override fun getTripSummaries(): Flow<List<TripSummary>> {
        return tripSummaryDao.getAll().map { tripSummaryEntities ->
            tripSummaryMapper.fromEntities(tripSummaryEntities)
        }
    }

    override suspend fun insertTripSummary(tripSummary: TripSummary) {
        val entity = tripSummaryMapper.toEntity(tripSummary)
        tripSummaryDao.insert(entity)
    }

    override fun getAllOBDReadings(): Flow<List<OBDReading>> {
        return obdReadingsDao.getAll().map { obdReadingEntities ->
            obdReadingEntities.map { entity ->
                obdReadingMapper.fromEntity(entity)
            }
        }
    }

    override fun getOBDReadings(tripUUID: String): Flow<List<OBDReading>> {
        return obdReadingsDao.get(tripUUID = tripUUID).map { obdReadingEntities ->
            obdReadingEntities.map { entity ->
                obdReadingMapper.fromEntity(entity)
            }
        }
    }

    override fun getAllUnsentUUIDs(): Flow<List<String>> {
        return tripSummaryDao.getAllUnsentUUIDs()
    }

    override fun updateSummarySentStatus(tripUUID: String, sent: Boolean) {
        tripSummaryDao.updateSummarySentStatus(tripUUID, sent)
    }

    override suspend fun insertOBDReading(obdReading: OBDReading, tripUUID: String) {
        val entity = obdReadingMapper.toEntity(obdReading, tripUUID)
        obdReadingsDao.insert(entity)
    }

    override fun getWeatherData(latitude: Double, longitude: Double): Flow<WeatherData> {
        return flow {
            val weatherData = weatherApi.getWeatherInfo(latitude = latitude, longitude = longitude)
            emit(weatherData)
        }.flowOn(
            Dispatchers.IO
        )
    }

    override fun updateLocationData() {
        servicesApi.updateLocation()
    }

    override fun getLocationData(): StateFlow<LocationData> {
        return servicesApi.getLocationFlow()
    }

    override suspend fun insertLocationData(locationData: LocationData, tripUUID: String) {
        val entity = locationDataMapper.toEntity(locationData, tripUUID)
        locationDataDao.insert(entity)
    }

    override fun getSavedLocationData(tripUUID: String): Flow<List<LocationData>> {
        return locationDataDao.get(tripUUID).map { entities ->
            entities.map { entity ->
                locationDataMapper.fromEntity(entity)
            }
        }
    }

    override suspend fun insertWeatherData(weatherData: WeatherData, tripUUID: String) {
        val entity = weatherDataMapper.toEntity(weatherData, tripUUID)

        weatherDataDao.insert(entity)
    }

    override fun getSavedWeatherData(tripUUID: String): Flow<WeatherData> {
        return weatherDataDao.get(tripUUID).map { entity ->
            weatherDataMapper.fromEntity(entity)
        }
    }

    override fun sendTripReadingData(
        locationData: LocationData,
        weatherData: WeatherData,
        tripUUID: String,
        obdReading: OBDReading
    ) {
        dataApi.sendTripReadingData(
            locationData = locationData,
            weatherData = weatherData,
            tripUUID = tripUUID,
            obdReading = obdReading
        )

        //TODO refactor this so that weather data is deleted after all the data stored locally is sent

        // delete weather data once it is sent to the server
        val weatherDataEntity = weatherDataMapper.toEntity(weatherData, tripUUID)
        weatherDataDao.delete(weatherDataEntity)

        // delete the data once it is sent to the server
        val locationDataEntity = locationDataMapper.toEntity(locationData, tripUUID)
        locationDataDao.delete(locationDataEntity)

        // delete the reading once it is sent to the server
        val obdReadingEntity = obdReadingMapper.toEntity(obdReading, tripUUID)
        obdReadingsDao.delete(obdReadingEntity)
    }
}