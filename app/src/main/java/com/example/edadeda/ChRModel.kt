package com.example.edadeda

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ChRModel: ViewModel() {
    val curRecept: MutableLiveData<Recept> by lazy {
        MutableLiveData<Recept>()
    }
}