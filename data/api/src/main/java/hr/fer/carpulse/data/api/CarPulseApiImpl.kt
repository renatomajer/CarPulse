package hr.fer.carpulse.data.api

import android.util.Log
import hr.fer.carpulse.data.api.model.DriverStatisticsDto
import hr.fer.carpulse.data.api.model.TripCoordinatesDto
import hr.fer.carpulse.data.api.model.TripDetailsDto
import hr.fer.carpulse.data.api.model.TripDistanceDto
import hr.fer.carpulse.domain.common.contextual.data.LocationData
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.post

class CarPulseApiImpl(
    private val httpClient: HttpClient
) : CarPulseApi {

    override suspend fun getTripDistance(tripUUID: String): TripDistanceDto {
        return try {
            val url = "$API_BASE_URL/trips/$tripUUID/data/distance"
            val tripDistanceDto = httpClient.get<TripDistanceDto>(url)
            tripDistanceDto
        } catch (exc: Exception) {
            Log.d("debug_log", exc.message ?: "Exception occurred wile contacting CarPulse server.")
            TripDistanceDto(tripUUID, null)
        }
    }

    override suspend fun calculateTripDistance(
        tripUUID: String,
        locationData: List<LocationData>
    ): TripDistanceDto {
        return try {
            val url = "$API_BASE_URL/trips/$tripUUID/calculate/distance"
            val tripDistanceDto = httpClient.post<TripDistanceDto>(url) {
                body = locationData
            }
            tripDistanceDto
        } catch (exc: Exception) {
            Log.d("debug_log", exc.message ?: "Exception occurred wile contacting CarPulse server.")
            TripDistanceDto(tripUUID, null)
        }
    }

    override suspend fun getTripCoordinates(tripUUID: String): TripCoordinatesDto? {
        return try {
            val url = "$API_BASE_URL/trips/$tripUUID/data/coordinates"
            val tripCoordinatesDto = httpClient.get<TripCoordinatesDto>(url)
            tripCoordinatesDto
        } catch (exc: Exception) {
            Log.d("debug_log", exc.message ?: "Exception occurred wile contacting CarPulse server.")
            null
        }
    }

    override suspend fun getDriverStatistics(driverId: String): DriverStatisticsDto? {
        return try {
            val url = "$API_BASE_URL/drivers/$driverId/statistics"
            val driverStatisticsDto = httpClient.get<DriverStatisticsDto>(url)
            driverStatisticsDto
        } catch (exc: Exception) {
            Log.d("debug_log", exc.message ?: "Exception occurred wile contacting CarPulse server.")
            null
        }
    }

    override suspend fun getTripDetailsDto(tripUUID: String): TripDetailsDto? {
        return try {
            val url = "$API_BASE_URL/trips/$tripUUID/data/details"
            val tripDetailsDto = httpClient.get<TripDetailsDto>(url)
            tripDetailsDto
        } catch (exc: Exception) {
            Log.d("debug_log", exc.message ?: "Exception occurred wile contacting CarPulse server.")
            null
        }
    }

    companion object {
        private const val API_BASE_URL = BuildConfig.CAR_PULSE_API_BASE_URL
    }
}