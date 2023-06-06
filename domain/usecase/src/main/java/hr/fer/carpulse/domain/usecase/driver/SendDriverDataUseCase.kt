package hr.fer.carpulse.domain.usecase.driver

import hr.fer.carpulse.domain.common.driver.DriverData
import hr.fer.carpulse.domain.repointerfaces.DriverDataRepository

class SendDriverDataUseCase(
    private val driverDataRepository: DriverDataRepository
) {
    operator fun invoke(driverData: DriverData) {
        driverDataRepository.sendDriverData(driverData)
    }
}