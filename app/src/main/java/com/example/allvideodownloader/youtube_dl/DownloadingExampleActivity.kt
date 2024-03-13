package com.example.allvideodownloader.youtube_dl


import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.allvideodownloader.R
import com.yausername.youtubedl_android.BuildConfig
import com.yausername.youtubedl_android.YoutubeDL.getInstance
import com.yausername.youtubedl_android.YoutubeDLRequest
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.io.File


class DownloadingExampleActivity : AppCompatActivity(), View.OnClickListener {
    private var btnStartDownload: Button? = null
    private var btnStopDownload: Button? = null
    private var etUrl: EditText? = null
    private var useConfigFile: Switch? = null
    private var progressBar: ProgressBar? = null
    private var tvDownloadStatus: TextView? = null
    private var tvCommandOutput: TextView? = null
    private var pbLoading: ProgressBar? = null
    private var downloading = false
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private val processId = "MyDlProcess"
    private val callback: (Float, Long?, String?) -> Unit =
        { progress: Float, o2: Long?, line: String? ->
            runOnUiThread {
                progressBar!!.progress = progress.toInt()
                tvDownloadStatus!!.text = line
            }
            Unit
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_downloading_example)
        initViews()
        initListeners()
    }

    private fun initViews() {
        btnStartDownload = findViewById<Button>(R.id.btn_start_download)
        btnStopDownload = findViewById<Button>(R.id.btn_stop_download)
        etUrl = findViewById<EditText>(R.id.et_url)
        useConfigFile = findViewById<Switch>(R.id.use_config_file)
        progressBar = findViewById<ProgressBar>(R.id.progress_bar)
        tvDownloadStatus = findViewById<TextView>(R.id.tv_status)
        pbLoading = findViewById<ProgressBar>(R.id.pb_status)
        tvCommandOutput = findViewById<TextView>(R.id.tv_command_output)
    }

    private fun initListeners() {
        btnStartDownload!!.setOnClickListener(this)
        btnStopDownload!!.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_start_download -> startDownload()
            R.id.btn_stop_download -> try {
                getInstance().destroyProcessById(processId)
            } catch (e: Exception) {
                Log.e(TAG, e.toString())
            }
        }
    }

    private fun startDownload() {
        if (downloading) {
            Toast.makeText(
                this@DownloadingExampleActivity,
                "cannot start download. a download is already in progress",
                Toast.LENGTH_LONG
            ).show()
            return
        }
        if (!isStoragePermissionGranted) {
            Toast.makeText(
                this@DownloadingExampleActivity,
                "grant storage permission and retry",
                Toast.LENGTH_LONG
            ).show()
            return
        }
        val url = etUrl!!.text.toString().trim { it <= ' ' }
        if (TextUtils.isEmpty(url)) {
            etUrl!!.error = getString(R.string.url_error)
            return
        }
        val request = YoutubeDLRequest(url)
        val youtubeDLDir = downloadLocation
        val config = File(youtubeDLDir, "config.txt")
        if (useConfigFile!!.isChecked && config.exists()) {
            request.addOption("--config-location", config.absolutePath)
        } else {
            request.addOption("--no-mtime")
            request.addOption("--downloader", "libaria2c.so")
            request.addOption("--external-downloader-args", "aria2c:\"--summary-interval=1\"")
            request.addOption("-f", "bestvideo[ext=mp4]+bestaudio[ext=m4a]/best[ext=mp4]/best")
            request.addOption("-o", youtubeDLDir.absolutePath + "/%(title)s.%(ext)s")
        }
        showStart()
        downloading = true
        val disposable: Disposable = Observable.fromCallable {
            getInstance().execute(
                request,
                processId,
                callback
            )
        }
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ youtubeDLResponse ->
                pbLoading!!.visibility = View.GONE
                progressBar!!.progress = 100
                tvDownloadStatus!!.text = getString(R.string.download_complete)
                tvCommandOutput?.text = (youtubeDLResponse.out.toString())
                Toast.makeText(
                    this@DownloadingExampleActivity,
                    "download successful",
                    Toast.LENGTH_LONG
                ).show()
                downloading = false
            }) { e ->
                if (BuildConfig.DEBUG) Log.e(
                    TAG,
                    "failed to download",
                    e
                )
                pbLoading!!.visibility = View.GONE
                tvDownloadStatus!!.text = getString(R.string.download_failed)
                tvCommandOutput?.text = e.message.toString()
                Toast.makeText(
                    this@DownloadingExampleActivity,
                    "download failed",
                    Toast.LENGTH_LONG
                )
                    .show()
                downloading = false
            }
        compositeDisposable.add(disposable)
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
        super.onDestroy()
    }

    private val downloadLocation: File
        private get() {
            val downloadsDir =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            val youtubeDLDir = File(downloadsDir, "youtubedl-android")
            if (!youtubeDLDir.exists()) youtubeDLDir.mkdir()
            return youtubeDLDir
        }

    private fun showStart() {
        tvDownloadStatus!!.text = getString(R.string.download_start)
        progressBar!!.progress = 0
        pbLoading!!.visibility = View.VISIBLE
    }

    private val isStoragePermissionGranted: Boolean
        get() =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                true
            }else{
                if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED
                ) {
                    true
                } else {
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                        1
                    )
                    false
                }
            }


    companion object {
        private val TAG = DownloadingExampleActivity::class.java.simpleName
    }
}