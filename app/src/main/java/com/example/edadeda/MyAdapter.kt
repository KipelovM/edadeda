package com.example.edadeda

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.edadeda.databinding.ReceptItemBinding

class MyAdapter: RecyclerView.Adapter<MyAdapter.MyHolder>() {

    private var receptes = ArrayList<Recept>()



    class MyHolder(item: View) : RecyclerView.ViewHolder(item){
        private val binding = ReceptItemBinding.bind(item)
        fun bind(rec: Recept) = with(binding){
            textView.text = rec.name
            textView2.text = rec.userId
            textView3.text = rec.description
            imageView.setImageResource(R.drawable.ic_launcher_foreground)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val view =  LayoutInflater.from(parent.context).inflate(R.layout.recept_item,parent,false)
        return MyHolder(view)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.bind(receptes[position])
    }

    override fun getItemCount(): Int {
        return receptes.size
    }
    fun addRecept(rec: Recept){

        if(rec.id !in receptes.map{ it.id }){
            receptes.add(rec)
        }
        notifyDataSetChanged()
        Log.d("bebra", "recept added ${receptes.size}")
    }


}