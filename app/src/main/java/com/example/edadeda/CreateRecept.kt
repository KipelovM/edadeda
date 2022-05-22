package com.example.edadeda

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.edadeda.databinding.FragmentCreateReceptBinding

class CreateRecept : Fragment() {
    val crModel = CRModel()
    lateinit var binding: FragmentCreateReceptBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateReceptBinding.inflate(inflater)
        binding.btnCreate.setOnClickListener{
            var des:String
            var name:String
            var recIngr:String
            binding.apply {
                des = etDscript.text.toString()
                name = etRecName.text.toString()
                recIngr = etIngr.text.toString()
            }
            if(des != "" && name != ""){
                crModel.createRecept(name,des,recIngr)
                Toast.makeText(context?.applicationContext, "Recept Created", Toast.LENGTH_SHORT).show()
                this@CreateRecept.requireActivity().supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.mainFrm,AllReceptes())
                    .addToBackStack(null)
                    .commit()
            }else{
                Toast.makeText(context?.applicationContext, "fields cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = CreateRecept()

    }
}