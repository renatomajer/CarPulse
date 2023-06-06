package hr.fer.carpulse.data.api

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import hr.fer.carpulse.domain.common.contextual.data.LocationData
import kotlinx.coroutines.flow.*

class ServicesApiImpl(
    private val context: Context
) : ServicesApi {

    private val locationDataFlow = MutableStateFlow(LocationData())

    private val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    // TODO: handle permissions in MainActivity
    @SuppressLint("MissingPermission")
    override fun updateLocation() {

        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
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

    override fun getLocationFlow(): StateFlow<LocationData> {
        return locationDataFlow.asStateFlow()
    }
}