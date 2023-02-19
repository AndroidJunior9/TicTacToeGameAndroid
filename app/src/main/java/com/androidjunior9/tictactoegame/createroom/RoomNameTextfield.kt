package com.androidjunior9.tictactoegame.createroom

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.androidjunior9.tictactoegame.Routes


@Composable
fun RoomNameTextfield(
    navController: NavController,
    viewModel: RoomNameViewModel = hiltViewModel()
){
    var roomName by remember {
        mutableStateOf("")
    }
    val context = LocalContext.current
    Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = Color.Blue,
                    shape = RoundedCornerShape(25.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                TextField(
                    value = roomName,
                    onValueChange = {
                        roomName = it
                    },
                    textStyle = TextStyle(
                        fontSize = 20.sp,
                        color = Color.Black
                    )
                )

                Button(onClick = {
                    if (roomName.isNotEmpty()) {
                        Log.d("Erroru",viewModel.username.value)
                        navController.navigate(
                            Routes.GAME_ROOM +"?roomname=${roomName}&username=${viewModel.username.value}"
                        )
                    } else Toast.makeText(context, "Roomname cannot be empty", Toast.LENGTH_LONG).show()
                }) {
                    Text(
                        text = "Join/Create",
                        style = TextStyle(
                            fontSize = 16.sp,
                            color = Color.White
                        )
                    )
                }
            }
        }
    }
