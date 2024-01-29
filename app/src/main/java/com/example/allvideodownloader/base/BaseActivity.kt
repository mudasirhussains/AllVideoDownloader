package com.example.allvideodownloader.base

import android.graphics.Typeface
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.example.allvideodownloader.R

open class BaseActivity(private val screenName: String = "") : AppCompatActivity() {
    var fontBold: Typeface? = null
    var fontSemiBold: Typeface? = null
    var fontRegular: Typeface? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initFonts()
    }

    fun changeFragmentWithoutReCreation(fragment: Fragment, tagFragmentName: String) {
        val mFragmentManager = supportFragmentManager
        val fragmentTransaction = mFragmentManager.beginTransaction()
        val currentFragment = mFragmentManager.primaryNavigationFragment
        if (currentFragment != null) {
            fragmentTransaction.hide(currentFragment)
        }

        var wasSaved = false
        var fragmentTemp = mFragmentManager.findFragmentByTag(tagFragmentName)
        if (fragmentTemp == null) {
            fragmentTemp = fragment
            fragmentTransaction.add(R.id.fragment_container, fragmentTemp, tagFragmentName)
        } else {
            wasSaved = true
            fragmentTransaction.show(fragmentTemp)
        }

        fragmentTransaction.setPrimaryNavigationFragment(fragmentTemp)
        fragmentTransaction.setReorderingAllowed(true)
        fragmentTransaction.commitNowAllowingStateLoss()
        //return if (wasSaved) fragmentTemp else null
    }

    private fun initFonts() {
//        fontBold = ResourcesCompat.getFont(this, R.font.montserrat_bold)
//        fontSemiBold = ResourcesCompat.getFont(this, R.font.montserrat_medium)
//        fontRegular =
//            ResourcesCompat.getFont(this, R.font.montserrat_regular)

    }


}