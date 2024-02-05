package com.example.allvideodownloader.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.allvideodownloader.R
import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import androidx.core.content.ContextCompat.getSystemService
import com.example.allvideodownloader.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    lateinit var binding : FragmentHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        clickListeners()
        return binding.root
    }

    private fun clickListeners() {
        binding.btnSearch.setOnClickListener {
            if (binding.etSearchBar.text.toString().isEmpty().not()){
                downloadVideo(binding.etSearchBar.text.toString())
            }else{
                binding.etSearchBar.error = "Empty"
            }
        }
    }

    fun downloadVideo(videoUrl: String) {
        val downloadManager = requireActivity().getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val downloadUri = Uri.parse(videoUrl)
        val request = DownloadManager.Request(downloadUri).apply {
            setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
            setTitle("Download Video") // Title of the download notification
            setDescription("Downloading...") // Description of the download notification
            setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            // For Android 10 and above, use the app-specific directory without requesting external storage permission
            setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "downloaded_video.mp4")
        }

        val downloadId = downloadManager.enqueue(request)
        // You can use the downloadId to query status or cancel the download
    }

    companion object {
        fun newInstance(): HomeFragment {
            val args = Bundle()

            val fragment = HomeFragment()
            fragment.arguments = args
            return fragment
        }
    }

}