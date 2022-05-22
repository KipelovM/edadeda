package com.example.edadeda

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class EPModel:ViewModel() {
    val fsRef = Firebase.storage.reference
    fun uploadImage(uri:Uri,path:String){
        fsRef.child(path).putFile(uri).addOnCompleteListener {
            if(it.isSuccessful){
                Log.d("bebra","file Uploaded( typo:${it.result.metadata?.contentType} size:${it.result.metadata?.sizeBytes}")
            }
            else{
                Log.d("bebra",it.exception?.message.toString())
            }
        }
    }
}