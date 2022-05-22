package com.example.edadeda

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.edadeda.databinding.FragmentAllReceptesBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

private lateinit var rw1: RecyclerView
private val db = Firebase.firestore
private lateinit var myAdapter:MyAdapter
private var receptes = ArrayList<Recept>()


class AllReceptes : Fragment() {
    private lateinit var binding: FragmentAllReceptesBinding
    private val RVModel: RVModel by activityViewModels()
    override fun onDestroy() {
        super.onDestroy()

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db.collection(REC_KEY).get()
            .addOnSuccessListener { result ->
                receptes.clear()
                myAdapter.recClear()
                for (document in result) {
                    db.collection(REC_KEY).document(document.id).update(mapOf("id" to document.id))
                    val recMap = document.data.toMap()
                    if(recMap["name"] != null && recMap["userId"] != null &&  recMap["userName"] != null && recMap["description"] != null){
                        val rec = Recept(document.id,recMap["name"] as String,recMap["userId"] as String,recMap["userName"] as String,recMap["description"] as String)
                        receptes.add(rec)
                        myAdapter.addRecept(rec)
                    }

                    Log.d(ContentValues.TAG, "${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(ContentValues.TAG, "Error getting documents: ", exception)
            }
        myAdapter = MyAdapter()
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
        val  ab = this@AllReceptes.requireActivity().actionBar
        rw1 = binding.recyclerView

        val bAdd = binding.bAdd

        bAdd.setOnClickListener {
            this@AllReceptes.requireActivity().supportFragmentManager
                .beginTransaction()
                .replace(R.id.mainFrm,CreateRecept())
                .addToBackStack(null)
                .commit()
        }

        rw1.layoutManager = LinearLayoutManager(this.context)
        rw1.adapter = myAdapter
        myAdapter.setOnItemClickListner(object : MyAdapter.OnClickListner{
            override fun onItemClick(position: Int) {
                RVModel.curRecept.value = myAdapter.getRecept(position)
                this@AllReceptes.requireActivity().supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.mainFrm,ReceptView())
                    .addToBackStack(null)
                    .commit()
                Log.d("bebra", "clicked $position")
            }

        })
//        myAdapter.addRecept(receptes)

    }

    companion object {
        @JvmStatic
        fun newInstance() = AllReceptes()
    }
}