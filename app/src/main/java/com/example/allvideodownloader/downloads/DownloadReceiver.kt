package com.example.allvideodownloader.downloads

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.allvideodownloader.share_view_model.HomeViewModel

class DownloadReceiver(private val viewModel: HomeViewModel) : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val downloadId = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1) ?: return

        val downloadManager = context?.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager?
        val query = DownloadManager.Query().setFilterById(downloadId)
        val cursor = downloadManager?.query(query)

        if (cursor?.moveToFirst() == true) {
            val statusIndex = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)
            val status = cursor.getInt(statusIndex)

            when (status) {
                DownloadManager.STATUS_SUCCESSFUL -> {
                    viewModel.updateDownloadItem(downloadId, 100, "Completed")
                }
                DownloadManager.STATUS_FAILED -> {
                    viewModel.updateDownloadItem(downloadId, 0, "Failed")
                }
            }
        }
        cursor?.close()
    }
}
