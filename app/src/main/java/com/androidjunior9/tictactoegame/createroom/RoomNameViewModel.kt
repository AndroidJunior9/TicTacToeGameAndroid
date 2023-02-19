package com.androidjunior9.tictactoegame.createroom

import android.content.SharedPreferences
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RoomNameViewModel @Inject constructor(
    private val pref:SharedPreferences
):ViewModel() {
    val username = mutableStateOf("")
    init{
        viewModelScope.launch{
            username.value = pref.getString("username",null)?:""
            Log.d("Errorrr",username.value)
        }
    }
}