package com.example.edadeda

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainModel:ViewModel(){
    val ab: MutableLiveData<androidx.appcompat.app.ActionBar>? = null
    val auth = Firebase.auth
    fun updateActionBar(){
        ab?.value?.title = auth.currentUser?.displayName.toString()
        ab?.value?.hide()
    }
}