package com.example.edadeda

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
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
    private val dataModel: DataModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        openFrag( AllReceptes())

    }
    private fun openFrag(f: Fragment){
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.mainFrm,f)
            .commit()
    }
}





