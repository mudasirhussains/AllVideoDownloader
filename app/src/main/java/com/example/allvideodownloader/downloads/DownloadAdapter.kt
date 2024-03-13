package com.example.allvideodownloader.downloads

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.allvideodownloader.R

class DownloadAdapter(val downloadItems: MutableList<DownloadItem>) :
    RecyclerView.Adapter<DownloadAdapter.DownloadViewHolder>() {

    class DownloadViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleTextView: TextView = view.findViewById(R.id.titleTextView)
        val progressBar: ProgressBar = view.findViewById(R.id.progressBar)
        val statusTextView: TextView = view.findViewById(R.id.statusTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DownloadViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.download_item, parent, false)
        return DownloadViewHolder(view)
    }

    override fun onBindViewHolder(holder: DownloadViewHolder, position: Int) {
        val item = downloadItems[position]
        holder.titleTextView.text = item.title
        //holder.progressBar.progress = item.progress
        holder.statusTextView.text = item.status


        if (item.status == "Downloading") {
            // Show an indeterminate progress bar to indicate an ongoing download
            holder.progressBar.isIndeterminate = true
        } else {
            // When download is completed, you can either hide the progress bar or show it as full
            holder.progressBar.isIndeterminate = false
            holder.progressBar.progress = 100 // or set visibility to GONE
        }
    }

    override fun getItemCount(): Int = downloadItems.size

    fun updateProgress(downloadId: Long, progress: Int) {
        val index = downloadItems.indexOfFirst { it.id == downloadId }
        if (index != -1) {
            downloadItems[index].progress = progress
            notifyItemChanged(index)
        }
    }

    fun updateStatus(downloadId: Long, status: String) {
        val index = downloadItems.indexOfFirst { it.id == downloadId }
        if (index != -1) {
            downloadItems[index].status = status
            notifyItemChanged(index)
        }
    }
}
