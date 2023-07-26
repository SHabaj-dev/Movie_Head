package com.sbz.moviehead

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.sbz.moviehead.utils.Constants.DELAY_TIME

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler(Looper.myLooper()!!).postDelayed({
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            finish()
        }, DELAY_TIME)
    }
}