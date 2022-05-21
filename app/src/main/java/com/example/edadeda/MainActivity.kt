package com.example.edadeda

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentOnAttachListener
import com.example.edadeda.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


const val REC_KEY = "RECEPTES"
const val USR_KEY = "USERS"

class MainActivity : AppCompatActivity() {

    private val RVModel: RVModel by viewModels()
    private lateinit var auth: FirebaseAuth
    private val db = Firebase.firestore
    private lateinit var user: User
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
            auth.signOut()
            openFrag(Auth())

        }
        return super.onOptionsItemSelected(item)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = applicationContext as App
        val binding = ActivityMainBinding.inflate(layoutInflater)
        auth = Firebase.auth
        setContentView(binding.root)
        openFrag(Auth())
        val fragManag = supportFragmentManager
        fragManag.addFragmentOnAttachListener(FragmentOnAttachListener{fragmentManager, fragment ->
            setUpActionBar()
        })

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
        Thread{
            val dIcon = if(auth.currentUser?.photoUrl != null){
                app.getImageByUrl(auth.currentUser?.photoUrl)
            } else{
                getDrawable(R.drawable.ic_launcher_foreground)
            }
            runOnUiThread{
                ab?.setDisplayHomeAsUpEnabled(true)
                ab?.setHomeAsUpIndicator(dIcon)
                ab?.title = auth.currentUser?.displayName.toString()
            }
        }.start()
    }
}






