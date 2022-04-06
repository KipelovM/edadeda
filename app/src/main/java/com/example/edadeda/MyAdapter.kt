package com.example.edadeda

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.edadeda.databinding.ItemBinding

class MyAdapter(val posts: List<Recept>) : RecyclerView.Adapter<MyAdapter.ViewRoll>() {

    class ViewRoll(val binding: ItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewRoll {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemBinding.inflate(inflater, parent, false)
        return ViewRoll(binding)
    }

    override fun onBindViewHolder(holder: ViewRoll, position: Int) {
        val post = posts[position]
        holder.binding.textView.text = post.name
        holder.binding.textView2.text = post.user.name
        holder.binding.textView3.text = post.description
    }

    override fun getItemCount(): Int {
        return posts.size
    }
}