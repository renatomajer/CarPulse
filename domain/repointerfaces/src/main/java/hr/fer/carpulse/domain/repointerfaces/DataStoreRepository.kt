package hr.fer.carpulse.domain.repointerfaces

import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {

    suspend fun saveOnBoardingState(completed: Boolean)

    fun readOnBoardingState(): Flow<Boolean>

    suspend fun saveLocalStorageState(storeLocally: Boolean)

    fun readLocalStorageState(): Flow<Boolean>
}