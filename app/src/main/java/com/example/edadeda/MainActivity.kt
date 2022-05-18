package com.example.edadeda

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.edadeda.databinding.ActivityMainBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

const val REC_KEY = "RECEPTES"

class MainActivity : AppCompatActivity() {


    private lateinit var rw1: RecyclerView
    private val db = Firebase.firestore
    private val users = mutableListOf<User>()
    private val myAdapter = MyAdapter()
    private var receptes = ArrayList<Recept>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        rw1 = binding.recyclerView
        db.collection(REC_KEY).get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val recMap = document.data.toMap()
                    val rec = Recept(document.id,recMap["name"] as String,recMap["userId"] as String,recMap["description"] as String)
                    receptes.add(rec)
                    myAdapter.addRecept(rec)
                    Toast.makeText(applicationContext, "added!${rec.name} ${receptes.size}", Toast.LENGTH_LONG).show()
                    Log.d(TAG, "${document.id} => ${document.data}")



                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }
        val bAdd = binding.bAdd

        bAdd.setOnClickListener {
            val rec = Recept("1","NAMEexmp","1","DescriptionEXMP")
            db.collection(REC_KEY)
                .add(rec)
                .addOnSuccessListener { documentReference ->
                    Log.d("bebra", "DocumentSnapshot added with ID: ${documentReference.id}")
                    Toast.makeText(applicationContext, "successes", Toast.LENGTH_LONG).show()
                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "Error adding document", e)
                }
        }

        rw1.layoutManager = LinearLayoutManager(this)
        rw1.adapter = myAdapter
//        myAdapter.addRecept(receptes)
    }

}





