package com.example.edadeda

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AuthModel:ViewModel() {
    val isAuth: MutableLiveData<Boolean> = MutableLiveData(false)
}