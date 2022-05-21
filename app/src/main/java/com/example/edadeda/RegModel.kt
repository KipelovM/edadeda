package com.example.edadeda

import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class RegModel: ViewModel() {
    val fsRef = Firebase.storage.reference
    val avtrUri: MutableLiveData<Uri?> by lazy {
        MutableLiveData<Uri?>()
    }
    fun uploadImage(uri:Uri,path:String){
        val mRef = fsRef.child(path)
        mRef.putFile(uri)
        fsRef.child(path).downloadUrl.addOnCompleteListener {
            if(it.isSuccessful){
                avtrUri.value = it.result
            }
            else{
                Log.d("bebra","download error: ${it.exception?.message}")
            }
        }

    }
}