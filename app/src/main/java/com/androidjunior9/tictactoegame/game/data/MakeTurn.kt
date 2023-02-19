package com.androidjunior9.tictactoegame.game.data

import kotlinx.serialization.Serializable

@Serializable
data class MakeTurn(val x:Int,val y:Int)
