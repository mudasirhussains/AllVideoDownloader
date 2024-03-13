package com.example.allvideodownloader.progress

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.allvideodownloader.about.AboutFragment
import com.example.allvideodownloader.databinding.FragmentProgressBinding
import com.example.allvideodownloader.downloads.DownloadAdapter
import com.example.allvideodownloader.downloads.DownloadItem
import com.example.allvideodownloader.share_view_model.HomeViewModel
import com.example.allvideodownloader.utils.visible

class ProgressFragment : Fragment() {
    private lateinit var binding : FragmentProgressBinding
    private lateinit var downloadAdapter: DownloadAdapter
    lateinit var viewModel: HomeViewModel
    private val downloadItems: MutableList<DownloadItem> = mutableListOf()
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
        viewModel = ViewModelProvider(requireActivity())[HomeViewModel::class.java]
        setupRecyclerView()

        viewModel.downloadItems.observe(viewLifecycleOwner, Observer { updatedItems ->
            updatedItems.forEach { updatedItem ->
                val index = downloadAdapter.downloadItems.indexOfFirst { it.id == updatedItem.id }

                if (index != -1) {
                    val currentItem = downloadAdapter.downloadItems[index]

                    if (currentItem.progress != updatedItem.progress) {
                        downloadAdapter.updateProgress(updatedItem.id, updatedItem.progress)
                    }

                    if (currentItem.status != updatedItem.status) {
                        downloadAdapter.updateStatus(updatedItem.id, updatedItem.status)
                    }
                } else {
                    downloadAdapter.downloadItems.add(updatedItem)
                    downloadAdapter.notifyItemInserted(downloadAdapter.downloadItems.size - 1)
                }
            }

            downloadAdapter.downloadItems.retainAll(updatedItems)
        })


        return binding.root
    }

    private fun setupRecyclerView() {
        binding.recyclerProgress.layoutManager = LinearLayoutManager(requireContext())
        downloadAdapter = DownloadAdapter(downloadItems)
        binding.recyclerProgress.adapter = downloadAdapter
    }
}