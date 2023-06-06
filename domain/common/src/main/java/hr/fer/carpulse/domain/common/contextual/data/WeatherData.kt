package hr.fer.carpulse.domain.common.contextual.data

@kotlinx.serialization.Serializable
data class WeatherData(
    val visibility: Int = -1,
    val timezone: Int = -1,
    val main: Main = Main(),
    val clouds: Clouds = Clouds(),
    val sys: Sys = Sys(),
    val dt: Long = 0L,
    val coord: Coord = Coord(),
    val name: String = "",
    val weather: Array<Weather> = emptyArray(),
    val cod: Int = -1,
    val id: Int = -1,
    val base: String = "",
    val wind: Wind = Wind()

) {
}

@kotlinx.serialization.Serializable
data class Main(
    val grnd_level: Int = -1,
    val temp: Double = 0.0,
    val temp_min: Double = 0.0,
    val humidity: Int = 0,
    val pressure: Int = 0,
    val sea_level: Int = 0,
    val feels_like: Double = 0.0,
    val temp_max: Double = 0.0
)

@kotlinx.serialization.Serializable
data class Clouds(
    val all: Int = 0
)

@kotlinx.serialization.Serializable
data class Sys(
    val country: String = "",
    val sunrise: Long = 0L,
    val sunset: Long = 0L,
    val id: Long = 0L,
    val message: Int = 0,
    val type: Int = 0
)

@kotlinx.serialization.Serializable
data class Coord(
    val lon: Double = 0.0,
    val lat: Double = 0.0,
)

@kotlinx.serialization.Serializable
data class Weather(
    val id: Int = -1,
    val main: String = "",
    val description: String = "",
    val icon: String = ""
)

@kotlinx.serialization.Serializable
data class Wind(
    val deg: Int = -1,
    val speed: Double = 0.0,
    val gust: Double = 0.0
)