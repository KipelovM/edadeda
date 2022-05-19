package com.example.edadeda

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.edadeda.databinding.FragmentAuthBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class Auth : Fragment() {
    private lateinit var launcher: ActivityResultLauncher<Intent>
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: FragmentAuthBinding
    private val dataModel: DataModel by activityViewModels()
    private val db = Firebase.firestore
    private val users = ArrayList<User>()
    private lateinit var singInClient:GoogleSignInClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
            try {
                val account = task.getResult(ApiException::class.java)
                if (account != null){
                    firebaseGSI(account.idToken!!)
                }

            }catch (e: ApiException){
                Log.d("bebra", "failed singIn")
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        db.collection(USR_KEY).get()
            .addOnSuccessListener { result ->
                users.clear()
                for (document in result) {
                    val usrMap = document.data.toMap()
                    val usr = User(usrMap["id"] as String,usrMap["name"] as String,usrMap["photo"] as String,usrMap["mail"] as String)
                    users.add(usr)
                    Log.d("bebra", "${document.id} => ${document.data}")



                }
                Toast.makeText(context?.applicationContext, "added!", Toast.LENGTH_LONG).show()
            }
        binding = FragmentAuthBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkSI()
        binding.SGin.setOnClickListener {
            singInWithGoogle()
        }
        binding.sIn.setOnClickListener{
            registration()
        }

    }

    private fun getClient(): GoogleSignInClient{
        val gso = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        return GoogleSignIn.getClient(requireActivity(),gso)
    }
    private fun singInWithGoogle(){
        singInClient = getClient()
        launcher.launch(singInClient.signInIntent)
    }
    private fun firebaseGSI(idToken: String){
        val credential = GoogleAuthProvider.getCredential(idToken,null)
        auth.signInWithCredential(credential).addOnCompleteListener {
            if(it.isSuccessful){
                Toast.makeText(context?.applicationContext, "SingIn Successes", Toast.LENGTH_SHORT).show()
                if(auth.currentUser?.uid.toString() !in users.map{ usr-> usr.id }){
                    db.collection(USR_KEY).document(auth.currentUser!!.uid).set(User(auth.currentUser!!.uid,auth.currentUser!!.displayName.toString(),auth.currentUser!!.photoUrl.toString(),auth.currentUser!!.email.toString()))
                }
                checkSI()
            }
            else{
                Toast.makeText(context?.applicationContext, "SingIn Fail", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun checkSI(){
        if(auth.currentUser != null){
            this@Auth.requireActivity().supportFragmentManager
                .beginTransaction()
                .replace(R.id.mainFrm,AllReceptes())
                .addToBackStack(null)
                .commit()
        }
    }
    private fun registration(){
        val email = binding.editTextTextEmailAddress.text.toString()
        val password = binding.editTextTextPassword.text.toString()
        if(email.isNotEmpty() && password.isNotEmpty()){

            auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this@Auth.requireActivity()){
                if(it.isSuccessful){
                    Toast.makeText(context?.applicationContext, "SingUp successful", Toast.LENGTH_SHORT).show()

                }
                else{
                    Toast.makeText(context?.applicationContext, it.exception?.message, Toast.LENGTH_SHORT).show()
                }
            }
        }else{
            Toast.makeText(context?.applicationContext, "Email and password cannot be empty", Toast.LENGTH_SHORT).show()
        }
    }
    companion object {
        @JvmStatic
        fun newInstance() = Auth()
    }
}