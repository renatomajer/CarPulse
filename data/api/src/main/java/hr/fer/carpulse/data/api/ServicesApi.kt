package hr.fer.carpulse.data.api

import hr.fer.carpulse.domain.common.contextual.data.LocationData
import kotlinx.coroutines.flow.StateFlow

interface ServicesApi {
    fun updateLocation()

    fun stopLocationUpdate()

    fun getLocationFlow(): StateFlow<LocationData>
}