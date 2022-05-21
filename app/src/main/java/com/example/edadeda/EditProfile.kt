package com.example.edadeda

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.edadeda.databinding.FragmentEditProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class EditProfile : Fragment() {

    lateinit var binding: FragmentEditProfileBinding
    lateinit var auth: FirebaseAuth
    val epModel = EPModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 1 && data != null && data.data != null) if(resultCode == Activity.RESULT_OK){
            Log.d("bebra","img url:${data.data}")
            epModel.avtrUri.value = data.data
            binding.ivRegAvtr.setImageURI(data.data)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        auth = Firebase.auth
        binding = FragmentEditProfileBinding.inflate(inflater)
        binding.apply {
            ivRegAvtr.setImageURI(auth.currentUser?.photoUrl)
            epModel.avtrUri.value = auth.currentUser?.photoUrl
            etName.hint = auth.currentUser?.displayName
            btnChgImg.setOnClickListener {
                val intent = Intent()
                intent.type = "image/*"
                intent.action = Intent.ACTION_GET_CONTENT
                startActivityForResult(intent, 1)
            }
            btnEdit.setOnClickListener {
                var name = auth.currentUser?.displayName
                var uri = auth.currentUser?.photoUrl
                uri = epModel.avtrUri.value
                name = binding.etName.text.toString()
                auth.currentUser?.updateProfile(
                    UserProfileChangeRequest.Builder().setDisplayName(name).build()
                )
                auth.currentUser?.updateProfile(
                    UserProfileChangeRequest.Builder().setPhotoUri(uri).build()
                )
                Toast.makeText(context?.applicationContext, "Edited!", Toast.LENGTH_SHORT).show()
            }
        }
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = EditProfile()
    }
}