package com.example.edadeda

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.edadeda.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


const val REC_KEY = "RECEPTES"

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
        epModel.up.observe(this){
            if(it){
                setUpActionBar()
                epModel.up.value = false
            }
        }
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
    private fun setUpActionBar(){

        val ab = supportActionBar
        ab?.show()
//        Thread{
//            val dIcon = if(auth.currentUser?.photoUrl != null){
//                app.getImageByUrl(auth.currentUser?.photoUrl)
//            } else{
//                getDrawable(R.drawable.ic_launcher_foreground)
//            }
//            runOnUiThread{
//                ab?.setDisplayHomeAsUpEnabled(true)
//                ab?.setHomeAsUpIndicator(dIcon)
//                ab?.title = auth.currentUser?.displayName.toString()
//            }
//        }.start()
        ab?.setDisplayHomeAsUpEnabled(true)
        ab?.setHomeAsUpIndicator(getDrawable(R.drawable.ic_launcher_foreground))
        ab?.title = auth.currentUser?.displayName.toString()
        ab?.hide()
        ab?.show()
    }
}






