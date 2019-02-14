package com.chrissen.eyepetizer.ui.fragment


import android.content.Intent
import android.graphics.Typeface
import android.support.v4.app.Fragment
import android.view.View
import com.chrissen.eyepetizer.R
import com.chrissen.eyepetizer.ui.AdviceActivity
import com.chrissen.eyepetizer.ui.CacheActivity
import com.chrissen.eyepetizer.ui.WatchActivity
import kotlinx.android.synthetic.main.fragment_mine.*

/**
 * A simple [Fragment] subclass.
 *
 */
class MineFragment : BaseFragment(), View.OnClickListener {

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.tv_watch ->{
                var intent = Intent(context, WatchActivity::class.java)
                context?.startActivity(intent)
            }

            R.id.tv_advise -> {
                var intent = Intent(context, AdviceActivity::class.java)
                startActivity(intent)
            }

            R.id.tv_save -> {
                var intent = Intent(context, CacheActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun getLayoutResources(): Int {
        return R.layout.fragment_mine
    }

    override fun initView() {
        tv_advise.setOnClickListener(this)
        tv_watch.setOnClickListener(this)
        tv_save.setOnClickListener(this)
        tv_advise.typeface = Typeface.createFromAsset(context?.assets, "fonts/FZLanTingHeiS-DB1-GB-Regular.TTF")
        tv_watch.typeface = Typeface.createFromAsset(context?.assets, "fonts/FZLanTingHeiS-DB1-GB-Regular.TTF")
        tv_save.typeface = Typeface.createFromAsset(context?.assets, "fonts/FZLanTingHeiS-DB1-GB-Regular.TTF")
    }


}
