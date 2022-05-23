package com.example.edadeda


import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.example.edadeda.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

var curRec = Recept("","","","","","")
const val REC_KEY = "RECEPTES"
const val USER_KEY = "USERS"

class MainActivity : AppCompatActivity() {
    private val authModel: AuthModel by viewModels()
    private lateinit var auth: FirebaseAuth
    private val db = Firebase.firestore
    private lateinit var user: User
    val mainModel = MainModel()
    val epModel = EPModel()
    lateinit var app: App

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        return super.onCreateOptionsMenu(menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.miEP){
            openFrag(EditProfile())
        }
        if(item.itemId == R.id.sout){
            authModel.isAuth.value = false
            auth.signOut()
            openFrag(Auth())

        }
        return super.onOptionsItemSelected(item)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = applicationContext as App
        mainModel.ab?.value = supportActionBar
        val binding = ActivityMainBinding.inflate(layoutInflater)
        auth = Firebase.auth
        setContentView(binding.root)
        openFrag(Auth())
        val fragManag = supportFragmentManager
        authModel.isAuth.observe(this) {
            if(it){
                setUpActionBar()
            }
            else{
                supportActionBar?.hide()
            }
        }

    }
    private fun openFrag(f: Fragment){
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.mainFrm,f)
            .addToBackStack(null)
            .commit()
    }
    fun setUpActionBar(){
        val ab = supportActionBar
        var iDraw:Drawable
        Firebase.storage.reference.child(auth.currentUser?.uid.toString()).downloadUrl.addOnCompleteListener {
            if(it.isSuccessful){
                Glide.with(this).asDrawable().load(it.result).apply(RequestOptions().override(120, 120)).circleCrop()
                    .into(object : CustomTarget<Drawable?>() {
                        override fun onResourceReady(
                            resource: Drawable,
                            transition: com.bumptech.glide.request.transition.Transition<in Drawable?>?
                        ) {
                            supportActionBar!!.setHomeAsUpIndicator(resource)
                        }

                        override fun onLoadCleared(@Nullable placeholder: Drawable?) {}
                    })
            }
        }
        ab?.show()

        ab?.setDisplayHomeAsUpEnabled(true)
        ab?.title = auth.currentUser?.displayName.toString()
    }
}






