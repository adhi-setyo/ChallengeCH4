package com.adhi.challengech4.ui.onboarding

import android.os.Bundle
import android.os.PersistableBundle
import androidx.fragment.app.Fragment
import com.adhi.challengech4.R
import com.adhi.challengech4.ui.onboarding.entername.EnterNameFragment
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
        if(currentFragment is OnFinishNavigateListener){
            currentFragment.onFinishNavigateListener()
        }
    }

    private fun setupSliderFragment(){
        isSkipButtonEnabled = false
        addSlide(AppIntroFragment.createInstance(
            imageDrawable = R.drawable.ic_landing_page1,
            description = getString(R.string.text_desc_onboarding_1),
            descriptionColorRes = R.color.black
        ))
        addSlide(AppIntroFragment.createInstance(
            imageDrawable = R.drawable.ic_landing_page2,
            description = getString(R.string.text_desc_onboarding_2),
            descriptionColorRes = R.color.black
        ))
        addSlide(EnterNameFragment())
    }
}

interface OnFinishNavigateListener{
    fun onFinishNavigateListener()
}