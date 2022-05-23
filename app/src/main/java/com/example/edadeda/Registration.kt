package com.example.edadeda

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.edadeda.databinding.FragmentRegistrationBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage


class Registration : Fragment() {
    private lateinit var binding: FragmentRegistrationBinding
    private val regModel: RegModel by activityViewModels()
    lateinit var auth: FirebaseAuth
    val fsRef = Firebase.storage.reference
    var urii:Uri? = null
    var isUri = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 1 && data != null && data.data != null) if(resultCode == RESULT_OK){
            Log.d("bebra","img url:${data.data}")
            urii = data.data
            isUri = true
            binding.ivRegAvtr.setImageURI(data.data)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = FragmentRegistrationBinding.inflate(inflater)
        binding.apply {
            btnChgImg.setOnClickListener {
                val intent = Intent()
                intent.type = "image/*"
                intent.action = Intent.ACTION_GET_CONTENT
                startActivityForResult(intent, 1)
            }
            btnReg12.setOnClickListener {
                binding.btnReg12.isClickable = false
                var name: String
                var email: String
                var password: String
                val uri = regModel.avtrUri.value
                binding.apply {
                    name = etName.text.toString()
                    email = etEmail.text.toString()
                    password = etPassword.text.toString()
                }
                if (name != "" && email != "" && password != "" && isUri) {
                    binding.progressBar22.visibility = View.VISIBLE
                    auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                        if (it.isSuccessful) {
                            Log.d("bebra","user Created")
                            fsRef.child(auth.currentUser?.uid.toString()).putFile(urii!!).addOnCompleteListener { fireStore ->
                                if(fireStore.isSuccessful){
                                    Log.d("bebra","file Uploaded( typo:${fireStore.result.metadata?.contentType} size:${fireStore.result.metadata?.sizeBytes}")
                                    auth.currentUser?.updateProfile(
                                        UserProfileChangeRequest.Builder().setDisplayName(name).build()
                                    )?.addOnCompleteListener {
                                        Log.d("bebra","name Sat")
                                        this@Registration.requireActivity().supportFragmentManager
                                            .beginTransaction()
                                            .replace(R.id.mainFrm, Auth())
                                            .addToBackStack(null)
                                            .commit()
                                    }
                                }
                                else{
                                    Log.d("bebra",it.exception?.message.toString())
                                }
                            }



                        } else {
                            binding.btnReg12.isClickable = true
                            Toast.makeText(
                                context?.applicationContext,
                                it.exception?.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }


            return binding.root
        }


    companion object {
        @JvmStatic
        fun newInstance() = Registration()
    }
}