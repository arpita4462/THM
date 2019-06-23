package com.bhabha.thm

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.app.AppCompatActivity
import believe.cht.fadeintextview.TextViewListener
import com.bhabha.thm.MainActivity
import com.bhabha.thm.R
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo

import kotlinx.android.synthetic.main.splash_screen.*


/**
 * Created by Arpita Patel on 04-10-2018.
 */
class SplashActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)



//        logo animation
        YoYo.with(Techniques.ZoomIn)
            .duration(2000)
            .playOn(findViewById(R.id.logo))
//        text animation
        val typeface = ResourcesCompat.getFont(this, R.font.bodonbd)
        appname_tv.setTypeface(typeface)

        appname_tv.setListener(object : TextViewListener {
            override fun onTextStart() {
            }

            override fun onTextFinish() {
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                finish()
            }
        })

        appname_tv.setLetterDuration(100) // sets letter duration programmatically
        appname_tv.setText("Welcome to Digital Library") // sets the text with animation and call it from string resources
        appname_tv.isAnimating()

    }


}
