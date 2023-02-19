package com.androidjunior9.tictactoegame.auth.domain.repository

import com.androidjunior9.tictactoegame.auth.domain.model.AuthResult


interface AuthRepository {
    suspend fun signUp(username: String, password: String): AuthResult<Unit>
    suspend fun signIn(username: String, password: String): AuthResult<Unit>
    suspend fun authenticate(): AuthResult<Unit>

}