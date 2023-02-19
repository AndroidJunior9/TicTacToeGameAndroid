package com.androidjunior9.tictactoegame.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.androidjunior9.tictactoegame.createroom.RoomNameTextfield
import com.androidjunior9.tictactoegame.Routes
import com.androidjunior9.tictactoegame.auth.presentation.AuthScreen
import com.androidjunior9.tictactoegame.game.presentation.GameRoom

@Composable
fun Navigation(
    navController: NavHostController
){
    NavHost(navController = navController, startDestination = Routes.AUTH_SCREEN){
        composable(route = Routes.AUTH_SCREEN){
            AuthScreen(navController = navController)
        }
        composable(route = Routes.ROOM_NAME){
            RoomNameTextfield(navController = navController)
        }
        composable(
            route = Routes.GAME_ROOM+"?roomname={roomname}&username={username}",
            arguments = listOf(
                navArgument(
                    name = "roomname",
                ){
                    type = NavType.StringType
                    defaultValue = ""
                },navArgument(
                        name = "username",
            ){
                type = NavType.StringType
                defaultValue = ""
            }
            )
        ){
            GameRoom()
        }
    }
}