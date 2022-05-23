package com.example.edadeda

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.edadeda.databinding.FragmentChangeReceptBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class ChangeRecept : Fragment() {
    lateinit var binding: FragmentChangeReceptBinding
    val chrModel = ChRModel()
    val db = Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChangeReceptBinding.inflate(inflater)
            val rec = curRec
            Log.d("bebra","${rec.name}")
            binding.apply {
                etRecName2.setText(rec?.name)
                etDscript2.setText(rec?.description)
                etIngr2.setText(rec?.ingr)
                btnChange2.setOnClickListener {
                    binding.apply {
                        val name = etRecName2.text.toString()
                        val des = etDscript2.text.toString()
                        val ingr = etIngr2.text.toString()
                        db.collection(REC_KEY).document(curRec.id).update("name",name)
                        db.collection(REC_KEY).document(curRec.id).update("description",des)
                        db.collection(REC_KEY).document(curRec.id).update("ingr",ingr)
                        this@ChangeRecept.activity?.supportFragmentManager?.beginTransaction()
                            ?.replace(R.id.mainFrm,AllReceptes())
                            ?.addToBackStack(null)
                            ?.commit()
                    }

                }
            }

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) = ChangeRecept()
    }
}