package com.example.edadeda

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.edadeda.databinding.ReceptItemBinding
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class MyAdapter(): RecyclerView.Adapter<MyAdapter.MyHolder>() {

    private var receptes = ArrayList<Recept>()
    interface OnClickListner{
        fun onItemClick(position: Int,)
    }
    private lateinit var myListner: OnClickListner
    class MyHolder(item: View,listner: OnClickListner) : RecyclerView.ViewHolder(item){
        private val binding = ReceptItemBinding.bind(item)
        init{
            item.setOnClickListener{
                listner.onItemClick(adapterPosition,)
            }
        }
        fun bind(rec: Recept){
            db.collection(USER_KEY).document(rec.userId).get().addOnCompleteListener {
                if(it.isSuccessful){
                    binding.textView2.text  = "by: ${it.result["name"]}"
                }
            }
            binding.apply {
                textView.text = rec.name

                textView3.text = "Ingredients: \n${rec.ingr}"
            }
            Firebase.storage.reference.child(rec.userId).downloadUrl.addOnCompleteListener {
                Glide.with(binding.root).load(it.result.toString()).apply(RequestOptions().override(150, 150)).circleCrop().into(binding.imageView)
                binding.progressBar.visibility = View.GONE
            }


        }

    }
    fun setOnItemClickListner(listner: OnClickListner){
        myListner = listner
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val view =  LayoutInflater.from(parent.context).inflate(R.layout.recept_item,parent,false)
        return MyHolder(view,myListner)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.bind(receptes[position])
    }

    override fun getItemCount(): Int {
        return receptes.size
    }
    fun recClear(){
        receptes.clear()
    }
    fun addRecept(rec: Recept){
        receptes.add(rec)
        notifyDataSetChanged()
        Log.d("bebra", "recept added ${receptes.size}")
    }
    fun getRecept(position: Int): Recept {
        return receptes[position]
    }

}