package com.androidjunior9.tictactoegame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.androidjunior9.tictactoegame.navigation.Navigation
import com.androidjunior9.tictactoegame.ui.theme.TicTacToeGameTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TicTacToeGameTheme {
                val navController = rememberNavController()
                Navigation(navController = navController)
            }
        }
    }
}

