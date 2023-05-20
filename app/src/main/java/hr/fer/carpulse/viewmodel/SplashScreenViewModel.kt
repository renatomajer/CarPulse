package hr.fer.carpulse.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hr.fer.carpulse.domain.repointerfaces.DataStoreRepository
import hr.fer.carpulse.navigation.Screens
import kotlinx.coroutines.launch

class SplashScreenViewModel(
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {

    private val _isLoading: MutableState<Boolean> = mutableStateOf(true)
    val isLoading: State<Boolean> = _isLoading

    private val _startDestination: MutableState<String> =
        mutableStateOf(Screens.UserDataScreen.route)
    val startDestination: State<String> = _startDestination

    init {
        viewModelScope.launch {
            dataStoreRepository.readOnBoardingState().collect { completed ->
                if (completed) {
                    _startDestination.value = Screens.HomeScreen.route
                } else {
                    _startDestination.value = Screens.UserDataScreen.route
                }
            }

            _isLoading.value = false
        }
    }
}