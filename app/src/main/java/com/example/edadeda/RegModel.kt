package com.example.edadeda

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.ByteArrayOutputStream

class RegModel: ViewModel() {
    val fsRef = Firebase.storage.reference
    val avtrUri: MutableLiveData<Uri?> by lazy {
        MutableLiveData<Uri?>()
    }
    fun uploadImage(uri:Uri,bdraw:BitmapDrawable,path:String){
        val bitmap = (bdraw).bitmap
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()
        var uploadTask = fsRef.putBytes(data)
        uploadTask.addOnFailureListener {
            Log.d("bebra",it.message.toString())
        }.addOnSuccessListener { taskSnapshot ->
            // taskSnapshot.metadata contains file metadata such as size, content-type, etc.
            val meta = taskSnapshot.metadata
            Log.d("bebra","img Uploaded( size:${meta?.sizeBytes} type: ${meta?.contentType}")
        }
    }
}