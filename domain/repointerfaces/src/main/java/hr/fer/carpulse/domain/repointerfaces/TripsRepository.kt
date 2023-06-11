package hr.fer.carpulse.domain.repointerfaces

import hr.fer.carpulse.domain.common.contextual.data.LocationData
import hr.fer.carpulse.domain.common.contextual.data.WeatherData
import hr.fer.carpulse.domain.common.obd.OBDReading
import hr.fer.carpulse.domain.common.trip.TripStartInfo
import hr.fer.carpulse.domain.common.trip.TripSummary
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface TripsRepository {
    fun sendTripStartInfo(tripStartInfo: TripStartInfo)

    suspend fun insertTripStartInfo(tripStartInfo: TripStartInfo)

    fun getTripStartInfo(tripUUID: String): Flow<TripStartInfo>

    fun deleteTripStartInfo(tripStartInfo: TripStartInfo)

    fun getTripSummaries(): Flow<List<TripSummary>>

    suspend fun insertTripSummary(tripSummary: TripSummary)

    fun getAllOBDReadings(): Flow<List<OBDReading>>

    fun getOBDReadings(tripUUID: String): Flow<List<OBDReading>>

    fun getAllUnsentUUIDs(): Flow<List<String>>

    fun updateSummarySentStatus(tripUUID: String, sent: Boolean)

    suspend fun insertOBDReading(obdReading: OBDReading, tripUUID: String)

    fun updateLocationData()

    fun stopLocationUpdate()

    fun getLocationData(): StateFlow<LocationData>

    suspend fun insertLocationData(locationData: LocationData, tripUUID: String)

    fun getSavedLocationData(tripUUID: String): Flow<List<LocationData>>

    fun getWeatherData(latitude: Double, longitude: Double): Flow<WeatherData>

    suspend fun insertWeatherData(weatherData: WeatherData, tripUUID: String)

    fun getSavedWeatherData(tripUUID: String): Flow<WeatherData>

    fun connectToMqttBroker()

    fun disconnectFromMqttBroker()

    fun sendTripReadingData(
        locationData: LocationData,
        weatherData: WeatherData,
        tripUUID: String,
        obdReading: OBDReading
    )
}