package hr.fer.carpulse.data.api

import android.util.Log
import hr.fer.carpulse.domain.common.driver.DriverData
import hr.fer.carpulse.domain.common.obd.Reading
import hr.fer.carpulse.domain.common.trip.TripReview
import hr.fer.carpulse.domain.common.trip.TripStartInfo
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class MockedApi() : Api {

    private val format = Json { encodeDefaults = true }

    override fun sendDriverData(driverData: DriverData) {
        val jsonString = format.encodeToString(driverData)
        Log.d("debug_log", jsonString)
    }

    override fun sendTripReview(tripReview: TripReview) {
        val jsonString = format.encodeToString(tripReview)
        Log.d("debug_log", jsonString)
    }

    override fun sendTripStartInfo(tripStartInfo: TripStartInfo) {
        val jsonString = format.encodeToString(tripStartInfo)
        Log.d("debug_log", jsonString)
    }

    override fun sendReading(reading: Reading) {
        val jsonString = format.encodeToString(reading)
        Log.d("debug_log", jsonString)
    }


}
