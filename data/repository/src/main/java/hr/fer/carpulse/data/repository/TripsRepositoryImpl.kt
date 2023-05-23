package hr.fer.carpulse.data.repository

import hr.fer.carpulse.data.api.Api
import hr.fer.carpulse.data.database.mapper.OBDReadingMapper
import hr.fer.carpulse.data.database.mapper.TripSummaryMapper
import hr.fer.carpulse.data.database.trip.ITripSummaryDao
import hr.fer.carpulse.data.database.trip.obd.IOBDReadingsDao
import hr.fer.carpulse.domain.common.obd.OBDReading
import hr.fer.carpulse.domain.common.obd.Reading
import hr.fer.carpulse.domain.common.trip.TripStartInfo
import hr.fer.carpulse.domain.common.trip.TripSummary
import hr.fer.carpulse.domain.repointerfaces.TripsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TripsRepositoryImpl(
    private val tripSummaryDao: ITripSummaryDao,
    private val obdReadingsDao: IOBDReadingsDao,
    private val tripSummaryMapper: TripSummaryMapper,
    private val obdReadingMapper: OBDReadingMapper,
    private val api: Api
) : TripsRepository {

    override fun sendTripStartInfo(tripStartInfo: TripStartInfo) {
        api.sendTripStartInfo(tripStartInfo)
    }

    override fun getTripSummaries(): Flow<List<TripSummary>> {
        return tripSummaryDao.getAll().map { tripSummaryEntities ->
            tripSummaryMapper.fromEntities(tripSummaryEntities)
        }
    }

    override suspend fun insertTripSummary(tripSummary: TripSummary) {
        val entity = tripSummaryMapper.toEntity(tripSummary)
        tripSummaryDao.insert(entity)
    }

    override fun getAllOBDReadings(): Flow<List<OBDReading>> {
        return obdReadingsDao.getAll().map { obdReadingEntities ->
            obdReadingEntities.map { entity ->
                obdReadingMapper.fromEntity(entity)
            }
        }
    }

    override fun getOBDReadings(tripUUID: String): Flow<List<OBDReading>> {
        return obdReadingsDao.get(tripUUID = tripUUID).map { obdReadingEntities ->
            obdReadingEntities.map { entity ->
                obdReadingMapper.fromEntity(entity)
            }
        }
    }

    override fun getAllUnsentUUIDs(): Flow<List<String>> {
        return tripSummaryDao.getAllUnsentUUIDs()
    }

    override fun updateSummarySentStatus(tripUUID: String, sent: Boolean) {
        tripSummaryDao.updateSummarySentStatus(tripUUID, sent)
    }

    override suspend fun insertOBDReading(obdReading: OBDReading, tripUUID: String) {
        val entity = obdReadingMapper.toEntity(obdReading, tripUUID)
        obdReadingsDao.insert(entity)
    }

    override fun sendOBDReading(obdReading: OBDReading, tripUUID: String) {
        val reading = Reading(
            tripId = tripUUID,
            obdData = obdReading,
            timestamp = obdReading.timestamp
        )

        api.sendReading(reading)

        // delete the reading once it is sent to the server
        val entity = obdReadingMapper.toEntity(obdReading, tripUUID)
        obdReadingsDao.delete(entity)
    }
}