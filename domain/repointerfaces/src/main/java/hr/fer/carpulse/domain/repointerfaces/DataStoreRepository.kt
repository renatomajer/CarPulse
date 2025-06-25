package hr.fer.carpulse.domain.repointerfaces

import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {

    suspend fun saveOnBoardingState(completed: Boolean)

    fun isOnboardingCompleted(): Flow<Boolean>

    suspend fun saveLocalStorageState(storeLocally: Boolean)

    fun readLocalStorageState(): Flow<Boolean>

    suspend fun storeUserName(name: String)

    fun retrieveUserName(): Flow<String>

    suspend fun storeCarImageIndex(index: Int)

    fun retrieveCarImageIndex(): Flow<Int>

    suspend fun storeAvatarImageIndex(index: Int)

    fun retrieveAvatarImageIndex(): Flow<Int>
}