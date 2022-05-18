package com.example.edadeda

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.edadeda.databinding.ItemBinding

class MyAdapter: RecyclerView.Adapter<MyAdapter.MyHolder>() {

    private var receptes = ArrayList<Recept>()



    class MyHolder(item: View) : RecyclerView.ViewHolder(item){
        private val binding = ItemBinding.bind(item)
        fun bind(rec: Recept) = with(binding){
            textView.text = rec.name
            textView2.text = rec.userId
            textView3.text = rec.description
            imageView.setImageResource(R.drawable.ic_launcher_foreground)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val view =  LayoutInflater.from(parent.context).inflate(R.layout.item,parent,false)
        return MyHolder(view)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.bind(receptes[position])
    }

    override fun getItemCount(): Int {
        return receptes.size
    }
    fun addRecept(rec: Recept){
        receptes.add(rec)
        Log.d("bebra", "recept added${receptes.size}")
        notifyDataSetChanged()
    }

}