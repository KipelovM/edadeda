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
import com.google.firebase.storage.ktx.storage

class EditProfile : Fragment() {

    lateinit var binding: FragmentEditProfileBinding
    lateinit var auth: FirebaseAuth
    val frStore = Firebase.storage.reference
    val epModel = EPModel()
    val mainModel = MainModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 1 && data != null && data.data != null) if(resultCode == Activity.RESULT_OK){
            Log.d("bebra","img uri:${data.data}")
            epModel.uploadImage(data.data!!,auth.currentUser!!.uid)
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
//            ivRegAvtr.setImageURI(auth.currentUser?.photoUrl)
            etName.hint = auth.currentUser?.displayName
            btnChgImg.setOnClickListener {
                val intent = Intent()
                intent.type = "image/*"
                intent.action = Intent.ACTION_GET_CONTENT
                startActivityForResult(intent, 1)
            }
            btnEdit.setOnClickListener {
                val activity = requireActivity() as MainActivity
                var name = auth.currentUser?.displayName
                var url = auth.currentUser?.photoUrl
                frStore.child(auth.currentUser?.uid.toString()).downloadUrl.addOnCompleteListener{
                    if(it.isSuccessful){
                        url = it.result
                        Log.d("bebra","got url: ${it.result}")
                    }

                }
                name = binding.etName.text.toString()
                auth.currentUser?.updateProfile(
                    UserProfileChangeRequest.Builder().setDisplayName(name).build()
                )
                auth.currentUser?.updateProfile(
                    UserProfileChangeRequest.Builder().setPhotoUri(url).build()
                )?.addOnCompleteListener {
                    activity.setUpActionBar()
                    this@EditProfile.activity?.supportFragmentManager?.beginTransaction()
                        ?.replace(R.id.mainFrm,AllReceptes())
                        ?.addToBackStack(null)
                        ?.commit()
                }

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