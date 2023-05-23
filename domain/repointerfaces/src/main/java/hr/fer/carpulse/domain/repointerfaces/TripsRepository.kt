package hr.fer.carpulse.domain.repointerfaces

import hr.fer.carpulse.domain.common.obd.OBDReading
import hr.fer.carpulse.domain.common.trip.TripStartInfo
import hr.fer.carpulse.domain.common.trip.TripSummary
import kotlinx.coroutines.flow.Flow

interface TripsRepository {
    fun sendTripStartInfo(tripStartInfo: TripStartInfo)

    fun getTripSummaries(): Flow<List<TripSummary>>

    suspend fun insertTripSummary(tripSummary: TripSummary)

    fun getAllOBDReadings(): Flow<List<OBDReading>>

    fun getOBDReadings(tripUUID: String): Flow<List<OBDReading>>

    fun getAllUnsentUUIDs(): Flow<List<String>>

    fun updateSummarySentStatus(tripUUID: String, sent: Boolean)

    suspend fun insertOBDReading(obdReading: OBDReading, tripUUID: String)

    fun sendOBDReading(obdReading: OBDReading, tripUUID: String)
}