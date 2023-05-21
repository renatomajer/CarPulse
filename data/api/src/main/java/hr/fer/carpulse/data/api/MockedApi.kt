package hr.fer.carpulse.data.api

import android.util.Log
import hr.fer.carpulse.domain.common.driver.DriverData
import hr.fer.carpulse.domain.common.trip.TripReview
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class MockedApi : Api {

    private val format = Json { encodeDefaults = true }

    override fun sendDriverData(driverData: DriverData) {
        val jsonString = format.encodeToString(driverData)
        Log.d("debug_log", jsonString)
    }

    override fun sendTripReview(tripReview: TripReview) {
        val jsonString = format.encodeToString(tripReview)
        Log.d("debug_log", jsonString)
    }
}
