package com.adhi.challengech4.ui.onboarding

import android.os.Bundle
import android.os.PersistableBundle
import androidx.fragment.app.Fragment
import com.adhi.challengech4.R
import com.github.appintro.AppIntro2
import com.github.appintro.AppIntroFragment

class OnBoardingActivity:AppIntro2() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setupSliderFragment()
    }

    override fun onDonePressed(currentFragment: Fragment?) {
        super.onDonePressed(currentFragment)
    }

    private fun setupSliderFragment(){
        isSkipButtonEnabled = false
        addSlide(AppIntroFragment.createInstance(
            imageDrawable = R.drawable.ic_landing_page1,
            description = getString(R.string.text_desc_onboarding_1)
        ))
        addSlide(AppIntroFragment.createInstance(
            imageDrawable = R.drawable.ic_landing_page1,
            description = getString(R.string.text_desc_onboarding_2)
        ))
    }
}

interface OnFinishNavigateListener{
    fun onFinishNavigateListener()
}