package com.example.allvideodownloader.share_view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.allvideodownloader.downloads.DownloadItem

class HomeViewModel : ViewModel() {
    private val _downloadItems = MutableLiveData<List<DownloadItem>>()
    val downloadItems: LiveData<List<DownloadItem>> = _downloadItems

    fun addDownloadItem(downloadItem: DownloadItem) {
        val currentList = _downloadItems.value ?: emptyList()
        _downloadItems.value = currentList + downloadItem
    }

fun updateDownloadItem(downloadId: Long, progress: Int, status: String) {
    val currentList = _downloadItems.value?.toMutableList() ?: mutableListOf()
    val itemIndex = currentList.indexOfFirst { it.id == downloadId }
    if (itemIndex != null && itemIndex >= 0) {
        val item = currentList[itemIndex]
        val updatedItem = item.copy(progress = progress, status = status)
        currentList[itemIndex] = updatedItem
        _downloadItems.postValue(currentList)
    }
}

}
