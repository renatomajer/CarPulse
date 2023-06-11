package hr.fer.carpulse.data.api

import android.annotation.SuppressLint
import android.content.Context
import android.os.Looper
import android.util.Log
import com.google.android.gms.location.*
import hr.fer.carpulse.domain.common.contextual.data.LocationData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ServicesApiImpl(
    private val context: Context
) : ServicesApi {

    private val locationDataFlow = MutableStateFlow(LocationData())

    private val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    private lateinit var locationRequest: LocationRequest

    // TODO: handle permissions in MainActivity
    @SuppressLint("MissingPermission")
    override fun updateLocation() {

        locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 300L).build()

        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )
    }

    override fun stopLocationUpdate() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    override fun getLocationFlow(): StateFlow<LocationData> {
        return locationDataFlow.asStateFlow()
    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            for (location in locationResult.locations) {
                val lat = location.latitude
                val lon = location.longitude
                val alt = location.altitude
                Log.d("debug_log", "got new location in api -> lat: $lat, lon: $lon, alt: $alt")

                val data = LocationData(
                    longitude = lon,
                    latitude = lat,
                    altitude = alt,
                    timestamp = System.currentTimeMillis()
                )

                locationDataFlow.update { data }
            }
        }

    }
}