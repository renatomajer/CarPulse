package hr.fer.carpulse.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hr.fer.carpulse.domain.common.driver.DriverStatistics
import hr.fer.carpulse.domain.repointerfaces.DataStoreRepository
import hr.fer.carpulse.domain.usecase.driver.GetDriverStatisticsUseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class OverallStatisticsViewModel(
    private val dataStoreRepository: DataStoreRepository,
    private val getDriverStatisticsUseCase: GetDriverStatisticsUseCase
) : ViewModel() {

    var driverStatistics by mutableStateOf<DriverStatistics?>(null)
        private set

    var isLoading by mutableStateOf(true)
        private set

    var carImageIndex by mutableStateOf(0)
        private set

    init {
        viewModelScope.launch {
            carImageIndex = dataStoreRepository.retrieveCarImageIndex().first()
            driverStatistics = getDriverStatisticsUseCase()
            isLoading = false
        }
    }

    fun getAssistantAnalysis() {
        // TODO: implement assistant call and play response
    }
}