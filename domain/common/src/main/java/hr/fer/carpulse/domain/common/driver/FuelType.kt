package hr.fer.carpulse.domain.common.driver

sealed class FuelType(val type: String) {
    object Diesel : FuelType("Diesel")
    object Petrol : FuelType("Petrol (Benzine)")
    object LPG : FuelType("LPG")
    object HybridDiesel : FuelType("Hybrid Diesel")
    object HybridPetrol : FuelType("Hybrid Petrol")
    object Electric : FuelType("Electric")
}

fun getFuelTypeValues(): Array<String> {
    return arrayOf(
        FuelType.Diesel.type,
        FuelType.Petrol.type,
        FuelType.LPG.type,
        FuelType.HybridDiesel.type,
        FuelType.HybridPetrol.type,
        FuelType.Electric.type
    )
}