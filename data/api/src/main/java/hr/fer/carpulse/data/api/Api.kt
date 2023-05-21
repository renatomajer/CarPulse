package hr.fer.carpulse.data.api

import hr.fer.carpulse.domain.common.driver.DriverData
import hr.fer.carpulse.domain.common.trip.TripReview

interface Api {
    fun sendDriverData(driverData: DriverData)
    fun sendTripReview(tripReview: TripReview)
}