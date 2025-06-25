package hr.fer.carpulse.domain.usecase.driver

import hr.fer.carpulse.domain.common.driver.DriverStatistics
import hr.fer.carpulse.domain.repointerfaces.CarPulseRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext

class GetDriverStatisticsUseCase(
    private val getDriverDataUseCase: GetDriverDataUseCase,
    private val carPulseRepository: CarPulseRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend operator fun invoke(): DriverStatistics? {
        return withContext(dispatcher) {
            val driverId = getDriverDataUseCase().first().email

            carPulseRepository.getDriverStatistics(driverId = driverId)
        }
    }
}