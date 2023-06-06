package hr.fer.carpulse.data.api.di

import hr.fer.carpulse.data.api.*
import hr.fer.carpulse.data.api.config.ktorHttpClient
import hr.fer.carpulse.data.api.mqtt.MQTTClient
import io.ktor.client.*
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
}