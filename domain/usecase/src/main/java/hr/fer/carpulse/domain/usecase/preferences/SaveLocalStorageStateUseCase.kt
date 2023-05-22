package hr.fer.carpulse.domain.usecase.preferences

import hr.fer.carpulse.domain.repointerfaces.DataStoreRepository

class SaveLocalStorageStateUseCase(
    private val dataStoreRepository: DataStoreRepository
) {
    suspend operator fun invoke(storeLocally: Boolean) {
        dataStoreRepository.saveLocalStorageState(storeLocally = storeLocally)
    }
}