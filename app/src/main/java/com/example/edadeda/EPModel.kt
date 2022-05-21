package com.example.edadeda

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class EPModel:ViewModel() {
    val avtrUri: MutableLiveData<Uri> by lazy {
        MutableLiveData<Uri>()
    }
}