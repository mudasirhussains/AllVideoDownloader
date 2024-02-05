package com.example.allvideodownloader.progress

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.allvideodownloader.about.AboutFragment
import com.example.allvideodownloader.databinding.FragmentProgressBinding
import com.example.allvideodownloader.utils.visible

class ProgressFragment : Fragment() {
    private lateinit var binding : FragmentProgressBinding
    companion object {
        fun newInstance(): ProgressFragment {
            val args = Bundle()

            val fragment = ProgressFragment()
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
        binding = FragmentProgressBinding.inflate(inflater, container, false)
        binding.groupEmptyView.visible()
        return binding.root
    }
}