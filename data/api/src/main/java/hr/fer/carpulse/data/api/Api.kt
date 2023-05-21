package hr.fer.carpulse.data.api

import hr.fer.carpulse.domain.common.driver.DriverData

interface Api {
    fun sendDriverData(driverData: DriverData)
}