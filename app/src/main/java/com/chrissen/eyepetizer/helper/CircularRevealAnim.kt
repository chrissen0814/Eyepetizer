package com.chrissen.eyepetizer.helper

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.SuppressLint
import android.os.Build
import android.view.View
import android.view.ViewAnimationUtils
import android.view.animation.DecelerateInterpolator

/**
 *  Function:
 *  <br/>
 *  Describe:
 *  <br/>
 *  Author: chris on 2019/2/19.
 *  <br/>
 *  Email: chrissen0814@gmail.com
 */

class CircularRevealAnim {

    companion object {
        val DURATION : Long = 500
    }

    public interface AnimListener{
        fun onHideAnimationEnd()
        fun onShowAnimationEnd()
    }

    private var mListener : AnimListener? = null

    @SuppressLint("NewApi")
    private fun actionOtherVisible(isShow : Boolean, triggerView : View, animView : View){
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP){
            if (isShow) {
                animView.visibility = View.VISIBLE
                if(mListener != null) mListener!!.onShowAnimationEnd()
            }else{
                animView.visibility = View.GONE
                if(mListener != null)mListener!!.onHideAnimationEnd()
            }
            return
        }

        /**
         * 计算trigglerView的中心位置
         */
        val tvLocation = IntArray(2)
        triggerView.getLocationInWindow(tvLocation)
        val tvX = tvLocation[0] + triggerView.width/2
        val tvY = tvLocation[1] + triggerView.height/2

        /**
         * 计算animView的中心位置
         */
        val avLocation = IntArray(2)
        animView.getLocationInWindow(avLocation)
        val avX = avLocation[0] + animView.width/2
        val avY = avLocation[1] + animView.height/2

        val rippleW = if(tvX < avX) animView.width - tvX else tvX - avLocation[0]
        val rippleH = if(tvY < avY) animView.height - tvY else tvY - avLocation[1]

        val maxRadius = Math.sqrt((rippleW*rippleW + rippleH*rippleH).toDouble()).toFloat()
        val startRadius : Float
        val endRadius : Float

        if(isShow){
            startRadius = 0f
            endRadius = maxRadius
        }else{
            startRadius = maxRadius
            endRadius = 0f
        }

        val anim = ViewAnimationUtils.createCircularReveal(animView, tvX, tvY, startRadius, endRadius)
        animView.visibility = View.VISIBLE
        anim.duration = DURATION
        anim.interpolator = DecelerateInterpolator()
        anim.addListener(object : AnimatorListenerAdapter(){
            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
                if(isShow){
                    animView.visibility = View.VISIBLE
                    if(mListener != null)mListener!!.onShowAnimationEnd()
                }else{
                    animView.visibility = View.GONE
                    if(mListener != null) mListener!!.onHideAnimationEnd()
                }
            }
        })
        anim.start()
    }


    public fun show(triggleView : View, showView: View){
        actionOtherVisible(true, triggleView, showView)
    }

    fun hide(triggleView: View, hideView : View){
        actionOtherVisible(false, triggleView, hideView)
    }

    fun setAnimListener(listener: AnimListener){
        mListener = listener
    }

}