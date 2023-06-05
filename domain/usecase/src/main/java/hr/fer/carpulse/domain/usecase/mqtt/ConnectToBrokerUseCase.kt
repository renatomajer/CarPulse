package hr.fer.carpulse.domain.usecase.mqtt

import hr.fer.carpulse.domain.repointerfaces.TripsRepository

class ConnectToBrokerUseCase(
    private val tripsRepository: TripsRepository
) {
    operator fun invoke() {
        tripsRepository.connectToMqttBroker()
    }
}