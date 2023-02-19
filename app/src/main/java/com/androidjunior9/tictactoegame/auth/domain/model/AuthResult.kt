package com.androidjunior9.tictactoegame.auth.domain.model

sealed class AuthResult<T>(val data:T? = null) {
    class Authorized<T>(data:T? = null): AuthResult<T>(data)
    class Unauthorized<T>: AuthResult<T>()
    class UnknownError<T>: AuthResult<T>()
    class Conflict<T>: AuthResult<T>()
    class BadRequest<T>: AuthResult<T>()
}