package hr.fer.carpulse.domain.usecase.preferences

import hr.fer.carpulse.domain.repointerfaces.DataStoreRepository
import kotlinx.coroutines.flow.Flow

class ReadLocalStorageStateUseCase(
    private val dataStoreRepository: DataStoreRepository
) {
    operator fun invoke(): Flow<Boolean> {
        return dataStoreRepository.readLocalStorageState()
    }
}