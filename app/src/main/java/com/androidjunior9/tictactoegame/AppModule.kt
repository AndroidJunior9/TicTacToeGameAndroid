package com.androidjunior9.tictactoegame

import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.androidjunior9.tictactoegame.auth.data.remote.AuthApi
import com.androidjunior9.tictactoegame.auth.data.repository.AuthRepositoryImpl
import com.androidjunior9.tictactoegame.auth.domain.repository.AuthRepository
import com.androidjunior9.tictactoegame.game.data.KtorRealtimeMessagingClient
import com.androidjunior9.tictactoegame.game.data.RealtimeMessagingClient

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.logging.*
import io.ktor.client.features.websocket.*
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providesHttpClient():HttpClient{
        return HttpClient(CIO){
            install(Logging)
            install(WebSockets)
        }
    }

    @Singleton
    @Provides
    fun providesRealtimeMessagingClient(httpClient: HttpClient): RealtimeMessagingClient {
        return KtorRealtimeMessagingClient(httpClient)
    }

    @Provides
    @Singleton
    fun provideAuthApi(): AuthApi {
        return Retrofit.Builder()
            .baseUrl("http://${Constants.IP_ADDRESS}")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create()
    }



    @Provides
    @Singleton
    fun provideSharedPref(app: Application): SharedPreferences {
        return app.getSharedPreferences("prefs", MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(api: AuthApi, prefs: SharedPreferences): AuthRepository {
        return AuthRepositoryImpl(api, prefs)
    }


}