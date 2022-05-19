package com.example.edadeda

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class DataModel: ViewModel() {
    val curRecept: MutableLiveData<Recept> by lazy {
        MutableLiveData<Recept>()
    }
}