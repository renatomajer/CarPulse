package hr.fer.carpulse.domain.usecase.driver

import hr.fer.carpulse.domain.common.driver.DriverData
import hr.fer.carpulse.domain.repointerfaces.DriverDataRepository
import kotlinx.coroutines.flow.Flow

class GetDriverDataUseCase(
    private val driverDataRepository: DriverDataRepository
) {
    operator fun invoke(): Flow<DriverData> {
        return driverDataRepository.getDriverData()
    }
}