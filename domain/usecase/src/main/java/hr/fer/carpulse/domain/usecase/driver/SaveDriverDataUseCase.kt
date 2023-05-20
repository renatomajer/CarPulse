package hr.fer.carpulse.domain.usecase.driver

import hr.fer.carpulse.domain.common.driver.DriverData
import hr.fer.carpulse.domain.repointerfaces.DriverDataRepository

class SaveDriverDataUseCase(
    private val driverDataRepository: DriverDataRepository
) {
    suspend operator fun invoke(driverData: DriverData) {
        return driverDataRepository.insertDriverData(driverData)
    }
}