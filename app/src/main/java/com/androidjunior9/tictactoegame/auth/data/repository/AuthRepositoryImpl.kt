package com.androidjunior9.tictactoegame.auth.data.repository

import android.content.SharedPreferences
import android.util.Log
import com.androidjunior9.tictactoegame.auth.data.remote.AuthApi
import com.androidjunior9.tictactoegame.auth.domain.model.AuthRequest
import com.androidjunior9.tictactoegame.auth.domain.model.AuthResult
import com.androidjunior9.tictactoegame.auth.domain.repository.AuthRepository
import retrofit2.HttpException

class AuthRepositoryImpl(
    private val api: AuthApi,
    private val prefs: SharedPreferences
): AuthRepository {

    override suspend fun signUp(username: String, password: String): AuthResult<Unit> {
        return try {
            api.signUp(
                request = AuthRequest(
                    username = username,
                    password = password
                )
            )
            signIn(username, password)
        } catch(e: HttpException) {
            if(e.code() == 401) {
                AuthResult.Unauthorized()
            } else if(e.code() == 409){
                AuthResult.Conflict()
            }
            else if(e.code() == 400){
                AuthResult.BadRequest()
            }
            else {
                AuthResult.UnknownError()
            }
        } catch (e: Exception) {
            Log.d("Error","$e")
            AuthResult.UnknownError()
        }
    }

    override suspend fun signIn(username: String, password: String): AuthResult<Unit> {
        return try {
            val response = api.signIn(
                request = AuthRequest(
                    username = username,
                    password = password
                )
            )
            Log.d("TagError",response.token)
            prefs.edit()
                .putString("jwt", response.token)
                .apply()
            prefs.edit()
                .putString("username", username)
                .apply()
            AuthResult.Authorized()
        } catch(e: HttpException) {
            if(e.code() == 401) {
                AuthResult.Unauthorized()
            } else if(e.code() == 409){
                Log.d("TagError","Hello")
                AuthResult.Conflict()

            }
            else if(e.code() == 400){
                AuthResult.BadRequest()
            }
            else {
                Log.d("Error","$e")
                AuthResult.UnknownError()
            }
        } catch (e: Exception) {
            Log.d("Error","$e")
            AuthResult.UnknownError()
        }
    }

    override suspend fun authenticate(): AuthResult<Unit> {
        return try {
            val token = prefs.getString("jwt", null) ?: return AuthResult.Unauthorized()
            api.authenticate("Bearer $token")

            AuthResult.Authorized()
        } catch(e: HttpException) {
            if(e.code() == 401) {
                AuthResult.Unauthorized()
            }else if(e.code() == 409){
                AuthResult.Conflict()
            }
            else if(e.code() == 400){
                AuthResult.BadRequest()
            }
            else {
                Log.d("Error","$e")
                AuthResult.UnknownError()

            }

        } catch (e: Exception) {
            Log.d("Error","$e")
            AuthResult.UnknownError()

        }


    }




}