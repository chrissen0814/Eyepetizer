package com.chrissen.eyepetizer.ui

import android.graphics.Typeface
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.ScaleAnimation
import com.chrissen.eyepetizer.R
import com.chrissen.eyepetizer.utils.newIntent
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //设置全屏
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_splash)
        initView()
        setAnimation()
    }

    private fun setAnimation() {
        val alphaAnimation = AlphaAnimation(0.1f, 1.0f)
        alphaAnimation.duration = 1000
        val scaleAnimation = ScaleAnimation(0.1f, 1.0f, 0.1f, 1.0f, ScaleAnimation.RELATIVE_TO_SELF
        ,0.5f, ScaleAnimation.RELATIVE_TO_SELF, 0.5f)
        scaleAnimation.duration = 1000
        val animationSet = AnimationSet(true)
        animationSet.addAnimation(alphaAnimation)
        animationSet.addAnimation(scaleAnimation)
        animationSet.duration = 1000
        iv_icon_splash.startAnimation(animationSet)
        animationSet.setAnimationListener(object : Animation.AnimationListener{
            override fun onAnimationRepeat(animation: Animation?) {

            }

            override fun onAnimationEnd(animation: Animation?) {
                newIntent<MainActivity>()
                finish()
            }

            override fun onAnimationStart(animation: Animation?) {

            }
        })
    }

    private fun initView() {
        //设置字体
        val font : Typeface = Typeface.createFromAsset(this.assets, "fonts/Lobster-1.4.otf")
        tv_name_english.typeface = font
        tv_english_intro.typeface = font
    }
}
