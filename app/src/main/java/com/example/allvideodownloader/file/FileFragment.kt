package com.example.allvideodownloader.file

import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.allvideodownloader.databinding.FragmentFileBinding
import com.example.allvideodownloader.youtube_dl.DownloadingExampleActivity
import com.yausername.youtubedl_android.YoutubeDL
import com.yausername.youtubedl_android.YoutubeDLRequest
import java.io.File


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
        binding.btnDownload.setOnClickListener {
            //testing purpose, navigate to youtube dl library
            startActivity(Intent(requireContext(), DownloadingExampleActivity::class.java))
        }

        return binding.root
    }
}