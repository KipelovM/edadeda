package com.example.edadeda

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.edadeda.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var rw1: RecyclerView
    private val users = mutableListOf<User>()
    private val myAdapter = MyAdapter()
    val receptes = ArrayList<Recept>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        rw1 = binding.recyclerView
        val bAdd = binding.bAdd
        var index = 0
        var x = 0

        val names = mutableListOf<String>().also {
            it.add("Peter")
            it.add("Mark")
            it.add("Masha")
            it.add("Bob")
            it.add("Makar")
        }
        val photos = mutableListOf<String>().also {
            it.add("https://d279m997dpfwgl.cloudfront.net/wp/2020/02/krivak-1-1000x750.jpg")
            it.add("https://d279m997dpfwgl.cloudfront.net/wp/2020/02/krivak-1-1000x750.jpg")
            it.add("https://d279m997dpfwgl.cloudfront.net/wp/2020/02/krivak-1-1000x750.jpg")
            it.add("https://d279m997dpfwgl.cloudfront.net/wp/2020/02/krivak-1-1000x750.jpg")
            it.add("https://d279m997dpfwgl.cloudfront.net/wp/2020/02/krivak-1-1000x750.jpg")
        }
        val email = mutableListOf<String>().also {
            it.add("Peter@gmail.com")
            it.add("Mark@gmail.com")
            it.add("Masha@gmail.com")
            it.add("Bob@gmail.com")
            it.add("Makar@gmail.com")
        }

        while (x < 5){
            users.add(User((x+1).toLong(), names[x], photos[x], email[x]))
            x++
        }


        var y = 0
        while (y < 5){
            receptes.add(Recept((y+1).toLong(), "Name example $y", users[y], "Description example N$y Vodka-based martinis fill cocktail menus, and there is no shortage of recipes to mix up. Yet, there is only one true vodka martini, and it is unbelievably simple. All you need for this drink is vodka, dry vermouth, and bitters (plus a garnish like a lemon peel, if you'd like), making it nothing more than the classic gin martini with a vodka base. It is crisp, clean, and the best way to show off the top-shelf vodkas in your bar."))
            y++
        }
        rw1.layoutManager = LinearLayoutManager(this)
        rw1.adapter = myAdapter
        bAdd.setOnClickListener(){
            if(index > 4)index = 0
            myAdapter.addRecept(receptes[index])
            index++
        }


}}