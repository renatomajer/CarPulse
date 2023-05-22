package hr.fer.carpulse.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hr.fer.carpulse.domain.usecase.preferences.ReadLocalStorageStateUseCase
import hr.fer.carpulse.domain.usecase.preferences.SaveLocalStorageStateUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SettingsScreenViewModel(
    private val saveLocalStorageStateUseCase: SaveLocalStorageStateUseCase,
    private val readLocalStorageStateUseCase: ReadLocalStorageStateUseCase
) : ViewModel() {

    private val _storeLocally: MutableState<Boolean> =
        mutableStateOf(false)
    val storeLocally: State<Boolean> = _storeLocally

    init {
        viewModelScope.launch {
            readLocalStorageStateUseCase().collect { storeLocally ->
                _storeLocally.value = storeLocally
            }
        }
    }

    fun saveLocalStorageState(storeLocally: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            saveLocalStorageStateUseCase(storeLocally)
        }
    }
}