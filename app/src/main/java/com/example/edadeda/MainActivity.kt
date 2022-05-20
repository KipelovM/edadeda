package com.example.edadeda

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentOnAttachListener
import com.example.edadeda.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso


const val REC_KEY = "RECEPTES"
const val USR_KEY = "USERS"

class MainActivity : AppCompatActivity() {

    private val dataModel: DataModel by viewModels()
    private lateinit var auth: FirebaseAuth
    private val db = Firebase.firestore
    private lateinit var user: User

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        return super.onCreateOptionsMenu(menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.sout){
            auth.signOut()
            openFrag(Auth())
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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

        db.collection(USR_KEY).document(auth.currentUser?.uid.toString()).get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    Toast.makeText(this, document["name"].toString(), Toast.LENGTH_SHORT).show()
                } else {

                }
            }


        val ab = supportActionBar
        Thread{
            val bMap = Picasso.get().load(auth.currentUser?.photoUrl).get()
            val dIcon = BitmapDrawable(resources,bMap)
            runOnUiThread{
                ab?.setDisplayHomeAsUpEnabled(true)
                ab?.setHomeAsUpIndicator(dIcon)
                ab?.title = auth.currentUser?.displayName.toString()
            }
        }.start()
    }
}






