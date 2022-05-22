package com.example.edadeda

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class CRModel:ViewModel() {
    val db = Firebase.firestore
    val auth = Firebase.auth
    fun createRecept(recName:String,recDscript:String){
        val rec = Recept("1",recName,auth.currentUser?.uid.toString(),auth.currentUser?.displayName.toString(),recDscript)
        db.collection(REC_KEY)
            .add(rec)
            .addOnSuccessListener { documentReference ->
                documentReference.update("id",documentReference.id)
                Log.d("bebra", "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(ContentValues.TAG, "Error adding document", e)
            }
    }
}