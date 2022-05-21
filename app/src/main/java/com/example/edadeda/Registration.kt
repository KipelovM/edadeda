package com.example.edadeda

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
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


class Registration : Fragment() {
    private lateinit var binding: FragmentRegistrationBinding
    private val regModel: RegModel by activityViewModels()
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 1 && data != null && data.data != null) if(resultCode == RESULT_OK){
            Log.d("bebra","img url:${data.data}")
            regModel.avtrUri.value = data.data
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
            btnReg.setOnClickListener {
                var name: String
                var email: String
                var password: String
                val uri = regModel.avtrUri.value
                binding.apply {
                    name = etName.text.toString()
                    email = etEmail.text.toString()
                    password = etPassword.text.toString()
                }
                if (name != "" && email != "" && password != "" && uri != null) {
                    auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                        if (it.isSuccessful) {
                            binding.ivRegAvtr.isDrawingCacheEnabled = true
                            binding.ivRegAvtr.buildDrawingCache()
                            regModel.uploadImage(uri,binding.ivRegAvtr.drawable as BitmapDrawable,auth.currentUser?.uid.toString())

                            auth.currentUser?.updateProfile(
                                UserProfileChangeRequest.Builder().setPhotoUri(regModel.avtrUri.value).build()
                            )
                            auth.currentUser?.updateProfile(
                                UserProfileChangeRequest.Builder().setDisplayName(name).build()
                            )
                            auth.currentUser?.updateProfile(
                                UserProfileChangeRequest.Builder().setPhotoUri(uri).build()
                            )
                            this@Registration.requireActivity().supportFragmentManager
                                .beginTransaction()
                                .replace(R.id.mainFrm, Auth())
                                .addToBackStack(null)
                                .commit()
                        } else {
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