package com.example.allvideodownloader.home

import android.app.DownloadManager
import android.content.Context
import android.content.IntentFilter
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.allvideodownloader.databinding.FragmentHomeBinding
import com.example.allvideodownloader.downloads.DownloadItem
import com.example.allvideodownloader.downloads.DownloadReceiver
import com.example.allvideodownloader.share_view_model.HomeViewModel


class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    lateinit var viewModel: HomeViewModel
    private lateinit var downloadReceiver: DownloadReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity())[HomeViewModel::class.java]
        downloadReceiver = DownloadReceiver(viewModel)
        val intentFilter = IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
        requireContext().registerReceiver(downloadReceiver, intentFilter)
        clickListeners()

        return binding.root
    }

    private fun clickListeners() {
        binding.btnSearch.setOnClickListener {
            if (binding.etSearchBar.text.toString().isEmpty().not()) {
                downloadVideo(binding.etSearchBar.text.toString())
            } else {
                binding.etSearchBar.error = "Empty"
            }
        }
    }

    private fun downloadVideo(videoUrl: String) {
        val downloadManager =
            requireActivity().getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val downloadUri = Uri.parse(videoUrl)
        val uniqueTitle = "Video ${System.currentTimeMillis()}"
        val appName =
            requireActivity().applicationInfo.loadLabel(requireActivity().packageManager).toString()
        val request = DownloadManager.Request(downloadUri).apply {
            setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
            setTitle(uniqueTitle)
            setDescription("Downloading...")
            setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "$uniqueTitle.mp4")
        }
        val downloadId = downloadManager.enqueue(request)
        viewModel.addDownloadItem(DownloadItem(downloadId, uniqueTitle, 0, "Downloading"))
        checkDownloadProgress(downloadId, downloadManager)
    }

    companion object {
        fun newInstance(): HomeFragment {
            val args = Bundle()

            val fragment = HomeFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        requireContext().unregisterReceiver(downloadReceiver)
    }

    private fun checkDownloadProgress(downloadId: Long, downloadManager: DownloadManager) {
        val handler = Handler(Looper.getMainLooper())
        val runnable = object : Runnable {
            override fun run() {
                val query = DownloadManager.Query().setFilterById(downloadId)
                val cursor = downloadManager.query(query)

                if (cursor.moveToFirst()) {
                    val status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))
                    val downloadedBytes =
                        cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR))
                    val totalBytes =
                        cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES))

                    when (status) {
                        DownloadManager.STATUS_RUNNING -> {
                            if (totalBytes > 0) {
                                val progress = (downloadedBytes * 100L / totalBytes).toInt()
                                viewModel.updateDownloadItem(downloadId, progress, "Downloading")
                            }
                            handler.postDelayed(this, 1000)
                        }

                        DownloadManager.STATUS_SUCCESSFUL -> {
                            viewModel.updateDownloadItem(downloadId, 100, "Completed")
                        }

                        DownloadManager.STATUS_FAILED -> {
                            viewModel.updateDownloadItem(downloadId, 0, "Failed")
                        }
                    }
                }
                cursor.close()
            }
        }
        handler.post(runnable)
    }


    override fun onResume() {
        super.onResume()
        downloadReceiver = DownloadReceiver(viewModel)
        val intentFilter = IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
        requireContext().registerReceiver(downloadReceiver, intentFilter)
    }

    override fun onPause() {
        super.onPause()
        requireContext().unregisterReceiver(downloadReceiver)
    }


//    private fun downloadImageToGallery(context: Context, bitmap: Bitmap) {
//        val displayName =
//            "Image_${System.currentTimeMillis()}.png"     // Using MediaStore API for Android 10 and above
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {

//            val contentValues = ContentValues().apply {
//                put(
//                    MediaStore.Images.Media.DISPLAY_NAME,
//                    displayName
//                ) put (MediaStore.Images.Media.MIME_TYPE, "image/png")
//                put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES+"/"+Constants.APPLICATION_NAME)
//            }
//            val contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
//            val resolver = context.contentResolver
//            val uri = resolver.insert(contentUri, contentValues) uri ?. let { imageUri ->
//                resolver.openOutputStream(imageUri)
//                    ?.use { outputStream ->                 // Save the bitmap to the output stream
//                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
//                    }             // Optional: Show a toast message indicating that the image has been saved
//                binding.tvDownloadDescription.visible()
//                binding.btnDone.text = getString(R.string.done)
//            }
//        } else {
//            // For versions below Android 10, using traditional approach
//            val root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
//                .toString()
//            val myDir =
//                File("$root/${Constants.APPLICATION_NAME}")         // Check if the directory exists, if not, create it
//            if (!myDir.exists()) {
//                myDir.mkdirs()
//            }
//            val file = File(myDir, displayName)
////         Save the bitmap to the file
//            var out: OutputStream? = null
//            try {
//                out = file.outputStream()
//                bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
//                // Notify the system that a new file has been created
//                MediaScannerConnection.scanFile(
//                    context,
//                    arrayOf(file.toString()),
//                    arrayOf("image/png")
//                ) { _, _ ->
////          Showing a message indicating that the image has been saved
//                    binding.tvDownloadDescription.visible()
//                    binding.btnDone.text = getString(R.string.done)
//                }
//            } catch (e: Exception) {
//                e.printStackTrace()
//            } finally {
//                out?.close()
//            }
//        }
//
//
}