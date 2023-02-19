package com.androidjunior9.tictactoegame.game.data

import android.util.Log
import com.androidjunior9.tictactoegame.Constants
import io.ktor.client.*
import io.ktor.client.features.websocket.*
import io.ktor.client.request.*
import io.ktor.http.cio.websocket.*
import kotlinx.coroutines.flow.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class KtorRealtimeMessagingClient(
    private val client:HttpClient
): RealtimeMessagingClient {
    private var session:WebSocketSession? = null

    override fun getGameStateStream(room:String,username:String): Flow<GameState> {
        return flow {
            Log.d("Errorr", room)
            session = client.webSocketSession {
                url("ws://${Constants.IP_ADDRESS}/play?username=$username&roomname=$room")
            }
            Log.d("Errorrrr",session?.incoming.toString())
            val gamestates = session!!
                .incoming
                .consumeAsFlow()
                .filterIsInstance<Frame.Text>()
                .mapNotNull { Json.decodeFromString<GameState>(it.readText()) }

            emitAll(gamestates)
        }
    }

    override suspend fun sendAction(action: MakeTurn) {
        session?.outgoing?.send(Frame.Text("make_turn#${Json.encodeToString(action)}"))
    }

    override suspend fun close() {
        session?.close()
        session = null
    }


}