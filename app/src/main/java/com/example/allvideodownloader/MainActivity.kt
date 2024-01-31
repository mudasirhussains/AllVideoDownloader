package com.example.allvideodownloader

import android.content.res.Configuration
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.allvideodownloader.about.AboutFragment
import com.example.allvideodownloader.base.BaseActivity
import com.example.allvideodownloader.databinding.ActivityMainBinding
import com.example.allvideodownloader.file.FileFragment
import com.example.allvideodownloader.home.HomeFragment
import com.example.allvideodownloader.progress.ProgressFragment
import com.example.allvideodownloader.utils.setTextViewDrawableColor
import com.google.android.material.navigation.NavigationView


class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var currentFragment: Fragment
    private lateinit var toggle: ActionBarDrawerToggle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setBinding()
        setupDrawerLayout()
        initFragments()
        clickListener()

    }


    private fun setupDrawerLayout() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        val customTitleView = layoutInflater.inflate(R.layout.toolbar_title_layout, null)
        binding.toolbar.addView(customTitleView, Toolbar.LayoutParams(Gravity.CENTER))

        toggle = ActionBarDrawerToggle(
            this@MainActivity,
            drawerLayout,
            binding.toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        binding.navView.itemIconTintList = null
        binding.navView.setNavigationItemSelectedListener(this@MainActivity)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        val drawable = toggle.drawerArrowDrawable
        drawable.setColorFilter(
            ContextCompat.getColor(this, R.color.white),
            PorterDuff.Mode.SRC_ATOP
        )

        toggle.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        toggle.onConfigurationChanged(newConfig)
    }

    private fun setBinding() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        drawerLayout = binding.drawerLayout
    }

    private fun clickListener() {
        //bottom bar
        binding.tvHome.setOnClickListener {
            openHomeFragment()
            binding.navView.menu.findItem(R.id.nav_home).isChecked = true
        }

        binding.tvProgress.setOnClickListener {
            openProgressFragment()
            binding.navView.menu.findItem(R.id.nav_progress).isChecked = true
        }

        binding.tvFile.setOnClickListener {
            openFileFragment()
            binding.navView.menu.findItem(R.id.nav_download).isChecked = true
        }

        binding.tvAbout.setOnClickListener {
            openAboutFragment()
            binding.navView.menu.findItem(R.id.nav_about).isChecked = true
        }
    }

    private fun openHomeFragment() {
        currentFragment = HomeFragment.newInstance()
        changeFragmentWithoutReCreation(
            currentFragment,
            HomeFragment::class.java.canonicalName!!
        )
        changeBottomTabs(binding.tvHome)
    }

    private fun openFileFragment() {
        currentFragment = FileFragment.newInstance()
        changeFragmentWithoutReCreation(
            currentFragment,
            FileFragment::class.java.canonicalName!!
        )
        changeBottomTabs(binding.tvFile)
    }

    private fun openProgressFragment() {
        currentFragment = ProgressFragment.newInstance()
        changeFragmentWithoutReCreation(
            currentFragment,
            ProgressFragment::class.java.canonicalName!!
        )
        changeBottomTabs(binding.tvProgress)
    }

    private fun openAboutFragment() {
        currentFragment = AboutFragment.newInstance()
        changeFragmentWithoutReCreation(
            currentFragment,
            AboutFragment::class.java.canonicalName!!
        )
        changeBottomTabs(binding.tvAbout)
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



    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home -> {
                drawerLayout.closeDrawer(GravityCompat.START)
                openHomeFragment()
            }

            R.id.nav_download -> {
                drawerLayout.closeDrawer(GravityCompat.START)
                openFileFragment()
            }

            R.id.nav_progress -> {
                drawerLayout.closeDrawer(GravityCompat.START)
                openProgressFragment()
            }

            R.id.nav_share_app -> {
                drawerLayout.closeDrawer(GravityCompat.START)
                shareApp()
            }

            R.id.nav_about -> {
                drawerLayout.closeDrawer(GravityCompat.START)
                openAboutFragment()
            }
        }

        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun shareApp() {

    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onResume() {
        super.onResume()
        drawerLayout.post {
            drawerLayout.openDrawer(GravityCompat.START, false)
            drawerLayout.closeDrawer(GravityCompat.START, false)
        }
    }
}