package hr.fer.carpulse.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import hr.fer.carpulse.domain.repointerfaces.DataStoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

// TODO usecases for this repo
class DataStoreRepositoryImpl(
    private val dataStore: DataStore<Preferences>
) : DataStoreRepository {

    private object PreferencesKey {
        val onBoardingKey = booleanPreferencesKey(name = "on_boarding_complete")
        val localStorageKey = booleanPreferencesKey(name = "store_locally")
        val userNameKey = stringPreferencesKey(name = "user_name")
        val carImageIndex = intPreferencesKey(name = "car_image_index")
        val avatarImageIndex = intPreferencesKey(name = "avatar_image_index")
    }

    override suspend fun saveOnBoardingState(completed: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.onBoardingKey] = completed
        }
    }

    override fun isOnboardingCompleted(): Flow<Boolean> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                val onBoardingState = preferences[PreferencesKey.onBoardingKey] ?: false
                onBoardingState
            }
    }

    override suspend fun saveLocalStorageState(storeLocally: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.localStorageKey] = storeLocally
        }
    }

    // if true, the data should be saved locally
    override fun readLocalStorageState(): Flow<Boolean> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                val localStorageState = preferences[PreferencesKey.localStorageKey] ?: false
                localStorageState
            }
    }

    override suspend fun storeUserName(name: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.userNameKey] = name
        }
    }

    override fun retrieveUserName(): Flow<String> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }.map { preferences ->
                preferences[PreferencesKey.userNameKey] ?: ""
            }
    }

    override suspend fun storeCarImageIndex(index: Int) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.carImageIndex] = index
        }
    }

    override fun retrieveCarImageIndex(): Flow<Int> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }.map { preferences ->
                preferences[PreferencesKey.carImageIndex] ?: 0
            }
    }

    override suspend fun storeAvatarImageIndex(index: Int) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.avatarImageIndex] = index
        }
    }

    override fun retrieveAvatarImageIndex(): Flow<Int> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }.map { preferences ->
                preferences[PreferencesKey.avatarImageIndex] ?: 0
            }
    }
}