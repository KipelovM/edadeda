package com.example.edadeda

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.edadeda.databinding.FragmentReceptViewBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage


class ReceptView : Fragment() {
    private lateinit var binding: FragmentReceptViewBinding
    private val rvModel: RVModel by activityViewModels()
    val auth = Firebase.auth
    val chrModel = ChRModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentReceptViewBinding.inflate(inflater)
        rvModel.curRecept.observe(this.viewLifecycleOwner) { rec ->
            if(auth.currentUser?.uid.toString() == rec.userId){
                binding.apply {
                    btnDel.visibility = View.VISIBLE
                    btnDel.setOnClickListener {
                        db.collection(REC_KEY).document(rec.id).delete().addOnCompleteListener {
                            if(it.isSuccessful){
                                this@ReceptView.requireActivity().supportFragmentManager
                                    .beginTransaction()
                                    .replace(R.id.mainFrm,AllReceptes())
                                    .addToBackStack(null)
                                    .commit()
                            }
                        }
                    }
                    btnChange.visibility = View.VISIBLE
                    btnChange.setOnClickListener {
                        curRec = rec
                        this@ReceptView.requireActivity().supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.mainFrm,ChangeRecept())
                            .addToBackStack(null)
                            .commit()
                    }
                }

            }
            db.collection(USER_KEY).document(rec.userId).get().addOnCompleteListener {
                if(it.isSuccessful){
                    binding.tvUserName.text  = "by: ${it.result["name"]}"
                }
            }
            Firebase.storage.reference.child(rec.userId).downloadUrl.addOnCompleteListener {
                Glide.with(binding.root).load(it.result.toString()).apply(RequestOptions().override(150, 150)).circleCrop().into(binding.imageView2)
                binding.pbRV.visibility = View.GONE
            }
            binding.apply {
                tvRecName.text = rec.name
                tvRec.text = rec.description
                tvIng.text = "Ingredients: \n${rec.ingr}"
            }
        }
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = ReceptView()
    }
}