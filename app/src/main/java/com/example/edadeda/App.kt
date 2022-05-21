package com.example.edadeda

import android.app.Application
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import com.squareup.picasso.Picasso

class App : Application() {
    fun getImageByUrl(url: String?): BitmapDrawable {
        val bMap = Picasso.get().load(url).get()
        return BitmapDrawable(resources, bMap)
    }
    fun getImageByUrl(url: Uri?): BitmapDrawable {
        val bMap = Picasso.get().load(url).get()
        return BitmapDrawable(resources, bMap)
    }


}