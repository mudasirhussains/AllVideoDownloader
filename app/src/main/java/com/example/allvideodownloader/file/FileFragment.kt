package com.example.allvideodownloader.file

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.allvideodownloader.about.AboutFragment
import com.example.allvideodownloader.databinding.FragmentFileBinding

class FileFragment : Fragment() {
    private lateinit var binding: FragmentFileBinding
    companion object {
        fun newInstance(): FileFragment {
            val args = Bundle()

            val fragment = FileFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFileBinding.inflate(inflater, container, false)
        return binding.root
    }
}