package com.androidjunior9.tictactoegame.auth.data.remote

import com.androidjunior9.tictactoegame.auth.domain.model.AuthRequest
import com.androidjunior9.tictactoegame.auth.domain.model.TokenResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthApi {

    @POST("signup")
    suspend fun signUp(
        @Body request: AuthRequest
    )

    @POST("signin")
    suspend fun signIn(
        @Body request: AuthRequest
    ): TokenResponse

    @GET("authenticate")
    suspend fun authenticate(
        @Header("Authorization") token: String
    )

}