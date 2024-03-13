package com.example.allvideodownloader.downloads

data class DownloadItem(
    val id: Long,
    val title: String,
    var progress: Int,
    var status: String // e.g., "Downloading", "Completed", "Failed"
)
