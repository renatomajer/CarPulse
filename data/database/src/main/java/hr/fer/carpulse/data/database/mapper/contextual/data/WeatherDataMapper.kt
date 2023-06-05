package hr.fer.carpulse.data.database.mapper.contextual.data

import hr.fer.carpulse.data.database.trip.contextual.data.WeatherDataEntity
import hr.fer.carpulse.domain.common.contextual.data.*

class WeatherDataMapper {
    fun toEntity(data: WeatherData, tripUUID: String): WeatherDataEntity {
        return WeatherDataEntity(
            tripUUID = tripUUID,
            visibility = data.visibility,
            timezone = data.timezone,
            grnd_level = data.main.grnd_level, //Main
            temp = data.main.temp,
            temp_min = data.main.temp_min,
            humidity = data.main.humidity,
            pressure = data.main.pressure,
            sea_level = data.main.sea_level,
            feels_like = data.main.feels_like,
            temp_max = data.main.temp_max,
            all = data.clouds.all, //Clouds
            country = data.sys.country, // Sys
            sunrise = data.sys.sunrise,
            sunset = data.sys.sunset,
            sysId = data.sys.id,
            message = data.sys.message,
            type = data.sys.type,
            dt = data.dt,
            lon = data.coord.lon, // Coord
            lat = data.coord.lat,
            name = data.name,
            weather = data.weather,
            cod = data.cod,
            id = data.id,
            base = data.base,
            deg = data.wind.deg, // Wind
            speed = data.wind.speed,
            gust = data.wind.gust
        )
    }

    fun fromEntity(entity: WeatherDataEntity): WeatherData {
        val main = Main(
            grnd_level = entity.grnd_level,
            temp = entity.temp,
            temp_min = entity.temp_min,
            humidity = entity.humidity,
            pressure = entity.pressure,
            sea_level = entity.sea_level,
            feels_like = entity.feels_like,
            temp_max = entity.temp_max,
        )

        val clouds = Clouds(all = entity.all)

        val sys = Sys(
            country = entity.country,
            sunrise = entity.sunrise,
            sunset = entity.sunset,
            id = entity.sysId,
            message = entity.message,
            type = entity.type,
        )

        val coord = Coord(
            lon = entity.lon,
            lat = entity.lat,
        )

        val wind = Wind(
            deg = entity.deg,
            speed = entity.speed,
            gust = entity.gust
        )

        return WeatherData(
            visibility = entity.visibility,
            timezone = entity.timezone,
            main = main,
            clouds = clouds,
            sys = sys,
            dt = entity.dt,
            coord = coord,
            name = entity.name,
            weather = entity.weather,
            cod = entity.cod,
            id = entity.id,
            base = entity.base,
            wind = wind
        )
    }
}