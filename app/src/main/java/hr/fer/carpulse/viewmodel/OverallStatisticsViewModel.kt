package hr.fer.carpulse.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hr.fer.carpulse.domain.common.driver.DriverStatistics
import hr.fer.carpulse.domain.usecase.driver.GetDriverStatisticsUseCase
import kotlinx.coroutines.launch

class OverallStatisticsViewModel(
    private val getDriverStatisticsUseCase: GetDriverStatisticsUseCase
) : ViewModel() {

    var driverStatistics by mutableStateOf<DriverStatistics?>(null)
        private set

    var isLoading by mutableStateOf(true)
        private set

    init {
        viewModelScope.launch {
            driverStatistics = getDriverStatisticsUseCase()
            isLoading = false
        }
    }

    fun getAssistantAnalysis() {
        // TODO: implement assistant call and play response
    }
}