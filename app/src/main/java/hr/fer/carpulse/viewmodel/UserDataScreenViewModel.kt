package hr.fer.carpulse.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hr.fer.carpulse.domain.common.driver.DriverData
import hr.fer.carpulse.domain.common.driver.Gender
import hr.fer.carpulse.domain.repointerfaces.DataStoreRepository
import hr.fer.carpulse.domain.usecase.driver.GetDriverDataUseCase
import hr.fer.carpulse.domain.usecase.driver.SaveDriverDataUseCase
import hr.fer.carpulse.domain.usecase.driver.SendDriverDataUseCase
import hr.fer.carpulse.domain.usecase.mqtt.ConnectToBrokerUseCase
import hr.fer.carpulse.domain.usecase.mqtt.DisconnectFromBrokerUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class UserDataScreenViewModel(
    private val dataStoreRepository: DataStoreRepository,
    private val saveDriverDataUseCase: SaveDriverDataUseCase,
    private val getDriverDataUseCase: GetDriverDataUseCase,
    private val sendDriverDataUseCase: SendDriverDataUseCase,
    private val connectToBrokerUseCase: ConnectToBrokerUseCase,
    private val disconnectFromBrokerUseCase: DisconnectFromBrokerUseCase
) : ViewModel() {

    private val _driverData = MutableStateFlow(DriverData())
    val driverData: StateFlow<DriverData> = _driverData.asStateFlow()

    init {
        connectToBrokerUseCase()
    }

    fun saveOnBoardingState(completed: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.saveOnBoardingState(completed = completed)
        }
    }

    fun updateEmail(email: String) {
        _driverData.value = _driverData.value.copy(
            email = email
        )
    }

    fun updateAge(age: String) {
        val ageNumber: Int
        try {
            ageNumber = if (age == "") {
                0
            } else {
                age.toInt()
            }

            _driverData.value = _driverData.value.copy(
                age = ageNumber
            )
        } catch (exc: NumberFormatException) {
            // TODO: show error
            Log.d("debug_log", "Age is just a number...")
            Log.d("debug_log", exc.toString())
        }
    }

    fun updateGender(gender: Gender) {
        _driverData.value = _driverData.value.copy(
            gender = gender.value
        )
    }

    fun updateFuelType(fuelType: String) {
        _driverData.value = _driverData.value.copy(
            fuelType = fuelType
        )
    }

    fun updateVehicleType(vehicleType: String) {
        _driverData.value = _driverData.value.copy(
            vehicleType = vehicleType
        )
    }

    fun updateVehicleProductionYear(productionYear: String) {
        val productionYearNumber: Int
        try {
            productionYearNumber = if (productionYear == "") {
                0
            } else {
                productionYear.toInt()
            }

            _driverData.value = _driverData.value.copy(
                vehicleProductionYear = productionYearNumber
            )
        } catch (exc: NumberFormatException) {
            // TODO: show error
            Log.d("debug_log", exc.toString())
        }
    }

    fun updateVehicleMotorPower(motorPower: String) {
        val motorPowerNumber: Int
        try {
            motorPowerNumber = if (motorPower == "") {
                0
            } else {
                motorPower.toInt()
            }

            _driverData.value = _driverData.value.copy(
                vehicleMotorPower = motorPowerNumber
            )
        } catch (exc: NumberFormatException) {
            // TODO: show error
            Log.d("debug_log", exc.toString())
        }
    }

    fun updateStartStopSystem(value: Boolean) {
        _driverData.value = _driverData.value.copy(
            startStopSystem = value
        )
    }

    fun updateDrivingAge(drivingAge: String) {
        val drivingAgeNumber: Int
        try {
            drivingAgeNumber = if (drivingAge == "") {
                0
            } else {
                drivingAge.toInt()
            }

            _driverData.value = _driverData.value.copy(
                drivingAge = drivingAgeNumber
            )
        } catch (exc: NumberFormatException) {
            // TODO: show error
            Log.d("debug_log", exc.toString())
        }
    }

    fun updateDrivingInPrivateVehicle(drivingInPrivateVehicle: String) {
        val drivingInPrivateVehicleNumber: Int
        try {
            drivingInPrivateVehicleNumber = if (drivingInPrivateVehicle == "") {
                0
            } else {
                drivingInPrivateVehicle.toInt()
            }

            _driverData.value = _driverData.value.copy(
                drivingInPrivateVehicle = drivingInPrivateVehicleNumber
            )
        } catch (exc: NumberFormatException) {
            // TODO: show error
            Log.d("debug_log", exc.toString())
        }
    }

    fun updateDriverInPrivateVehicle(driverInPrivateVehicle: String) {
        val driverInPrivateVehicleNumber: Int
        try {
            driverInPrivateVehicleNumber = if (driverInPrivateVehicle == "") {
                0
            } else {
                driverInPrivateVehicle.toInt()
            }

            _driverData.value = _driverData.value.copy(
                driverInPrivateVehicle = driverInPrivateVehicleNumber
            )
        } catch (exc: NumberFormatException) {
            // TODO: show error
            Log.d("debug_log", exc.toString())
        }
    }

    fun updateFuelConsumptionOptimisation(fuelConsumptionOptimisation: String) {
        val fuelConsumptionOptimisationNumber: Int
        try {
            fuelConsumptionOptimisationNumber = if (fuelConsumptionOptimisation == "") {
                0
            } else {
                fuelConsumptionOptimisation.toInt()
            }

            _driverData.value = _driverData.value.copy(
                fuelConsumptionOptimisation = fuelConsumptionOptimisationNumber
            )
        } catch (exc: NumberFormatException) {
            // TODO: show error
            Log.d("debug_log", exc.toString())
        }
    }

    fun updateDrivingCrowdedRoads(drivingCrowdedRoads: String) {
        val drivingCrowdedRoadsNumber: Int
        try {
            drivingCrowdedRoadsNumber = if (drivingCrowdedRoads == "") {
                0
            } else {
                drivingCrowdedRoads.toInt()
            }

            _driverData.value = _driverData.value.copy(
                drivingCrowdedRoads = drivingCrowdedRoadsNumber
            )
        } catch (exc: NumberFormatException) {
            // TODO: show error
            Log.d("debug_log", exc.toString())
        }
    }

    fun updateComfort(comfort: String) {
        _driverData.value = _driverData.value.copy(
            comfort = comfort
        )
    }

    fun updateSecurity(security: String) {
        _driverData.value = _driverData.value.copy(
            security = security
        )
    }

    fun updateSportsStyle(sportsStyle: String) {
        _driverData.value = _driverData.value.copy(
            sportsStyle = sportsStyle
        )
    }

    fun updateFuelEfficiency(fuelEfficiency: String) {
        _driverData.value = _driverData.value.copy(
            fuelEfficiency = fuelEfficiency
        )
    }

    fun updateNeutralTraffic(neutralTraffic: String) {
        _driverData.value = _driverData.value.copy(
            neutralTraffic = neutralTraffic
        )
    }

    fun saveDriverData() {
        Log.d("debug_log", driverData.value.toString())
        viewModelScope.launch(Dispatchers.IO) {
            saveDriverDataUseCase(driverData.value)
        }
    }

    fun getDriverData() {
        viewModelScope.launch {
            val dd = getDriverDataUseCase().first()
            _driverData.value = dd
            Log.d("debug_log", "Data stored: " + dd.toString())
        }
    }

    fun sendDriverData() {
        sendDriverDataUseCase(_driverData.value)
    }

    override fun onCleared() {
        disconnectFromBrokerUseCase()
        super.onCleared()
    }
}