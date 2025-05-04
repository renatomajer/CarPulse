package hr.fer.carpulse.domain.repointerfaces

import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {

    suspend fun saveOnBoardingState(completed: Boolean)

    fun isOnboardingCompleted(): Flow<Boolean>

    suspend fun saveLocalStorageState(storeLocally: Boolean)

    fun readLocalStorageState(): Flow<Boolean>

    suspend fun storeUserName(name: String)

    fun retrieveUserName(): Flow<String>

    suspend fun storeAvatarColorIndex(index: Int)

    fun retrieveAvatarColorIndex(): Flow<Int>
}