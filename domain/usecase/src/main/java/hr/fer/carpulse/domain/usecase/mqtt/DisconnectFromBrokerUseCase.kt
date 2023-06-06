package hr.fer.carpulse.domain.usecase.mqtt

import hr.fer.carpulse.domain.repointerfaces.TripsRepository

class DisconnectFromBrokerUseCase(
    private val tripsRepository: TripsRepository
) {
    operator fun invoke() {
        tripsRepository.disconnectFromMqttBroker()
    }
}