package hr.fer.carpulse.data.api.di

import hr.fer.carpulse.data.api.AssistantApi
import hr.fer.carpulse.data.api.AssistantApiImpl
import hr.fer.carpulse.data.api.CarPulseApi
import hr.fer.carpulse.data.api.CarPulseApiImpl
import hr.fer.carpulse.data.api.DataApi
import hr.fer.carpulse.data.api.DataApiImpl
import hr.fer.carpulse.data.api.ServicesApi
import hr.fer.carpulse.data.api.ServicesApiImpl
import hr.fer.carpulse.data.api.TrafficApi
import hr.fer.carpulse.data.api.TrafficApiImpl
import hr.fer.carpulse.data.api.WeatherApi
import hr.fer.carpulse.data.api.WeatherApiImpl
import hr.fer.carpulse.data.api.config.ktorHttpClient
import hr.fer.carpulse.data.api.mqtt.MQTTClient
import io.ktor.client.HttpClient
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val apiModule = module {
    single<HttpClient> {
        ktorHttpClient
    }

    single<WeatherApi> {
        WeatherApiImpl(client = get())
    }

    single<ServicesApi> {
        ServicesApiImpl(context = androidApplication())
    }

    single {
        MQTTClient(context = androidApplication())
    }

    single<DataApi> {
        DataApiImpl(mqttClient = get())
    }

    single<TrafficApi> {
        TrafficApiImpl(httpClient = get())
    }

    single<AssistantApi> {
        AssistantApiImpl(httpClient = get())
    }

    single<CarPulseApi> {
        CarPulseApiImpl(httpClient = get())
    }
}