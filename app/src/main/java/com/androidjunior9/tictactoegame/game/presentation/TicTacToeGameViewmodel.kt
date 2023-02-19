package com.androidjunior9.tictactoegame.game.presentation

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androidjunior9.tictactoegame.game.data.GameState
import com.androidjunior9.tictactoegame.game.data.MakeTurn
import com.androidjunior9.tictactoegame.game.data.RealtimeMessagingClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.net.ConnectException
import javax.inject.Inject

@HiltViewModel
class TicTacToeGameViewmodel @Inject constructor(
    private val client: RealtimeMessagingClient,
    savedStateHandle: SavedStateHandle
):ViewModel() {
    private var roomName by mutableStateOf("")
    var username by mutableStateOf("")
    init{
        val name = savedStateHandle.get<String>("roomname")
        if (!name.isNullOrEmpty()){
            roomName = name
        }
        val user = savedStateHandle.get<String>("username")
        if (!user.isNullOrEmpty()){
            username = user
        }
        Log.d("Error","$username $roomName")


    }
    private val _isconnecting = MutableStateFlow(false)
    val isconnecting = _isconnecting.asStateFlow()
    private val _showconnectionError = MutableStateFlow(false)
    val showconnectionError = _showconnectionError.asStateFlow()
    val state = client
        .getGameStateStream(
            room = roomName,
            username = username
        ).onStart { _isconnecting.value = true }
        .onEach { _isconnecting.value = false }
        .catch{ t-> _showconnectionError.value = t is ConnectException}
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), GameState())






    fun finishTurn(x:Int,y:Int){
        if(state.value.board[y][x] != 0 || state.value.winner != 0){
            return
        }

        viewModelScope.launch {
            client.sendAction(MakeTurn(x,y))
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.launch {
            client.close()
        }
    }




}