package com.example.edadeda

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class RVModel: ViewModel() {
    val curRecept: MutableLiveData<Recept> by lazy {
        MutableLiveData<Recept>()
    }
}