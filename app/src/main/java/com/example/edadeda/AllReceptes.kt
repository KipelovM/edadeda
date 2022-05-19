package com.example.edadeda

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.edadeda.databinding.FragmentAllReceptesBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

private lateinit var rw1: RecyclerView
private val db = Firebase.firestore
private val myAdapter = MyAdapter()
private var receptes = ArrayList<Recept>()


class AllReceptes : Fragment() {
    private lateinit var binding: FragmentAllReceptesBinding
    private val dataModel: DataModel by activityViewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAllReceptesBinding.inflate(inflater)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rw1 = binding.recyclerView
        db.collection(REC_KEY).get()
            .addOnSuccessListener { result ->
                receptes.clear()
                for (document in result) {
                    db.collection(REC_KEY).document(document.id).update(mapOf("id" to document.id))
                    val recMap = document.data.toMap()
                    val rec = Recept(document.id,recMap["name"] as String,recMap["userId"] as String,recMap["description"] as String)
                    receptes.add(rec)
                    myAdapter.addRecept(rec)
                    Log.d(ContentValues.TAG, "${document.id} => ${document.data}")



                }
                Toast.makeText(context?.applicationContext, "added! ${receptes.size}", Toast.LENGTH_LONG).show()
            }
            .addOnFailureListener { exception ->
                Log.d(ContentValues.TAG, "Error getting documents: ", exception)
            }
        val bAdd = binding.bAdd

        bAdd.setOnClickListener {
            dataModel.curFragment.value = ReceptView()
//            val rec = Recept("1","NAMEexmp","1","DescriptionEXMP")
//            db.collection(REC_KEY)
//                .add(rec)
//                .addOnSuccessListener { documentReference ->
//                    Log.d("bebra", "DocumentSnapshot added with ID: ${documentReference.id}")
//                    Toast.makeText(context?.applicationContext, "successes", Toast.LENGTH_LONG).show()
//                }
//                .addOnFailureListener { e ->
//                    Log.w(ContentValues.TAG, "Error adding document", e)
//                    Toast.makeText(context?.applicationContext, "fail", Toast.LENGTH_LONG).show()
//                }
        }

        rw1.layoutManager = LinearLayoutManager(this.context)
        rw1.adapter = myAdapter
//        myAdapter.addRecept(receptes)

    }

    companion object {
        fun newInstance() = AllReceptes()
    }
}