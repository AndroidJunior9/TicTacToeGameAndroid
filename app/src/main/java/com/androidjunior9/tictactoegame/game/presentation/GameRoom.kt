package com.androidjunior9.tictactoegame.game.presentation

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel


@Composable
fun GameRoom(
    viewModel: TicTacToeGameViewmodel = hiltViewModel(),
){
    val state by viewModel.state.collectAsState()
    val isConnecting by viewModel.isconnecting.collectAsState()
    val showConnectionError by viewModel.showconnectionError.collectAsState()
    Log.d("State",state.players.toString())
    if(showConnectionError){
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ){
            Text(
                text = "Couldn't connect to the server",
                color = MaterialTheme.colors.error
            )
        }
        return
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Column(
            modifier = Modifier
                .padding(top = 32.dp)
                .align(Alignment.TopCenter)
        ) {
            if (!state.players.contains(1)) {
                Text(
                    text = "Waiting for player X",
                    fontSize = 32.sp,
                    color = Color.Black
                )
            } else if (!state.players.contains(2)) {
                Text(
                    text = "Waiting for player O",
                    fontSize = 32.sp,
                    color = Color.Black
                )
            }
        }
        if (state.players.size == 2 && state.winner==0 && !state.isBoardFull) {
            Text(
                text = if(state.playerAtTurn==1) "X is Next" else "O is Next",
                fontSize = 32.sp,
                modifier = Modifier.align(Alignment.TopCenter)
            )
        }

        TicTacToeField(
            state = state,
            onTapInField = viewModel::finishTurn,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .padding(16.dp)
        )
        if(state.isBoardFull || state.winner != 0){
            Text(
                text = when(state.winner){
                    1 -> "Player X Won"
                    2 -> "Player O Won"
                    else -> "Draw"
                },
                modifier = Modifier
                    .padding(
                        bottom = 32.dp
                    )
                    .align(Alignment.BottomCenter),
                fontSize = 32.sp
            )
        }
        if (isConnecting){
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ){
                CircularProgressIndicator()
            }
        }
    }

}