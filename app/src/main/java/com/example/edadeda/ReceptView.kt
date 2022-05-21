package com.example.edadeda

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.edadeda.databinding.FragmentReceptViewBinding


class ReceptView : Fragment() {
    private lateinit var binding: FragmentReceptViewBinding
    private val rvModel: RVModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentReceptViewBinding.inflate(inflater)
        rvModel.curRecept.observe(this.viewLifecycleOwner) { rec ->
            binding.apply {
                tvRecName.text = rec.name
                tvUserName.text = rec.userName
                textView4.text = rec.description
                imageView2.setImageResource(R.drawable.ic_launcher_foreground)
            }
        }
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = ReceptView()
    }
}