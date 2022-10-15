package com.adhi.challengech4.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import com.adhi.challengech4.R
import com.adhi.challengech4.databinding.ActivityGameBinding
import com.adhi.challengech4.databinding.ActivitySplashBinding
import com.adhi.challengech4.ui.game.GameActivity
import com.adhi.challengech4.ui.onboarding.OnBoardingActivity
import com.bumptech.glide.Glide

class SplashActivity : AppCompatActivity() {

    private val binding : ActivitySplashBinding by lazy {
        ActivitySplashBinding.inflate(layoutInflater)
    }

    private var timer: CountDownTimer?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.hide()
        setTimerSplashScreen()


        val image = "https://i.ibb.co/HC5ZPgD/splash-screen1.png"
        Glide.with(this)
            .load(image)
            .into(binding.ivHeaderSplash)
    }

    override fun onDestroy() {
        super.onDestroy()
        if(timer!=null){
            timer?.cancel()
            timer = null
        }
    }

    private fun setTimerSplashScreen(){
        timer = object : CountDownTimer(2000,1000){
            override fun onTick(millisUntilFinished: Long) {}
            override fun onFinish() {
                val intent = Intent(this@SplashActivity, OnBoardingActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            }
        }
        timer?.start()
    }

    private fun getImageFromInternet(){
        val image = "https://i.ibb.co/HC5ZPgD/splash-screen1.png"
        Glide.with(this)
            .load(image)
            .into(binding.ivHeaderSplash)
    }
}