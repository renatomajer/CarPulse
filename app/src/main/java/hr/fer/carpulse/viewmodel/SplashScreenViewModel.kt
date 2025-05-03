package hr.fer.carpulse.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hr.fer.carpulse.domain.repointerfaces.DataStoreRepository
import hr.fer.carpulse.navigation.Screens
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class SplashScreenViewModel(
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {

    private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    private val _startDestination: MutableStateFlow<String?> = MutableStateFlow(null)
    val startDestination = _startDestination.asStateFlow()

    init {
        viewModelScope.launch {
            val isOnboardingCompleted = dataStoreRepository.readOnBoardingState().first()

            if (isOnboardingCompleted) {
                _startDestination.value = Screens.HomeScreen.route
            } else {
                _startDestination.value = Screens.UserDataScreen.route
            }

            _isLoading.value = false
        }
    }
}