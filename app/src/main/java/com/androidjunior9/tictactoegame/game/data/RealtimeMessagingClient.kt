package com.androidjunior9.tictactoegame.game.data


import kotlinx.coroutines.flow.Flow

interface RealtimeMessagingClient {
    fun getGameStateStream(room:String,username:String): Flow<GameState>

    suspend fun sendAction(action: MakeTurn)

    suspend fun close()
}