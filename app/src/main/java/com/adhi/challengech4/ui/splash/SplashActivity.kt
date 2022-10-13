package com.adhi.challengech4.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import com.adhi.challengech4.R
import com.adhi.challengech4.ui.game.GameActivity

class SplashActivity : AppCompatActivity() {

    private var timer: CountDownTimer?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        supportActionBar?.hide()
        setTimerSplashScreen()
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
                val intent = Intent(this@SplashActivity, GameActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            }
        }
        timer?.start()
    }
}