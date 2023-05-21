package hr.fer.carpulse.data.api

import android.util.Log
import hr.fer.carpulse.domain.common.driver.DriverData
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class MockedApi : Api {
    override fun sendDriverData(driverData: DriverData) {
        val format = Json { encodeDefaults = true }
        val jsonString = format.encodeToString(driverData)
        Log.d("debug_log", jsonString)
    }
}
