package com.example.allvideodownloader

import android.os.Bundle
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.allvideodownloader.about.AboutFragment
import com.example.allvideodownloader.base.BaseActivity
import com.example.allvideodownloader.databinding.ActivityMainBinding
import com.example.allvideodownloader.file.FileFragment
import com.example.allvideodownloader.home.HomeFragment
import com.example.allvideodownloader.progress.ProgressFragment
import com.example.allvideodownloader.utils.setTextViewDrawableColor

class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var currentFragment: Fragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setBinding()
        initFragments()
        clickListener()
    }

    private fun setBinding() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun clickListener() {
        binding.tvHome.setOnClickListener {
            currentFragment = HomeFragment.newInstance()
            changeFragmentWithoutReCreation(
                currentFragment,
                HomeFragment::class.java.canonicalName!!
            )
            changeBottomTabs(binding.tvHome)
        }

        binding.tvProgress.setOnClickListener {
            currentFragment = ProgressFragment.newInstance()
            changeFragmentWithoutReCreation(
                currentFragment,
                ProgressFragment::class.java.canonicalName!!
            )
            changeBottomTabs(binding.tvProgress)
        }

        binding.tvFile.setOnClickListener {
            currentFragment = FileFragment.newInstance()
            changeFragmentWithoutReCreation(
                currentFragment,
                FileFragment::class.java.canonicalName!!
            )
            changeBottomTabs(binding.tvFile)
        }

        binding.tvAbout.setOnClickListener {
            currentFragment = AboutFragment.newInstance()
            changeFragmentWithoutReCreation(
                currentFragment,
                AboutFragment::class.java.canonicalName!!
            )
            changeBottomTabs(binding.tvAbout)
        }
    }

    private fun initFragments() {
        currentFragment = HomeFragment.newInstance()
        changeFragmentWithoutReCreation(
            currentFragment,
            HomeFragment::class.java.canonicalName!!
        )
        changeBottomTabs(binding.tvHome)
    }

    private fun changeBottomTabs(textView: TextView) {
        binding.tvHome.setTextColor(ContextCompat.getColor(this, R.color.bottom_bar_un_seleceted))
        binding.tvHome.setTextViewDrawableColor(
            ContextCompat.getColor(
                this,
                R.color.bottom_bar_un_seleceted
            )
        )

        binding.tvAbout.setTextColor(ContextCompat.getColor(this, R.color.bottom_bar_un_seleceted))
        binding.tvAbout.setTextViewDrawableColor(
            ContextCompat.getColor(
                this, R.color.bottom_bar_un_seleceted
            )
        )

        binding.tvFile.setTextColor(ContextCompat.getColor(this, R.color.bottom_bar_un_seleceted))
        binding.tvFile.setTextViewDrawableColor(
            ContextCompat.getColor(
                this,
                R.color.bottom_bar_un_seleceted
            )
        )

        binding.tvProgress.setTextColor(
            ContextCompat.getColor(
                this,
                R.color.bottom_bar_un_seleceted
            )
        )
        binding.tvProgress.setTextViewDrawableColor(
            ContextCompat.getColor(
                this,
                R.color.bottom_bar_un_seleceted
            )
        )

        textView.setTextColor(ContextCompat.getColor(this, R.color.bottom_bar_seleceted))
        textView.setTextViewDrawableColor(
            ContextCompat.getColor(
                this,
                R.color.bottom_bar_seleceted
            )
        )
    }
}