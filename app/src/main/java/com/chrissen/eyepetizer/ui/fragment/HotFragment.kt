package com.chrissen.eyepetizer.ui.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import com.chrissen.eyepetizer.R
import com.chrissen.eyepetizer.ui.adapter.HotAdapter
import kotlinx.android.synthetic.main.fragment_hot.*

/**
 * A simple [Fragment] subclass.
 *
 */
class HotFragment : BaseFragment() {

    var mTabs = arrayOf("周排行", "月排行", "总排行").toMutableList()
    lateinit var mFragments : ArrayList<Fragment>
    val STRATEGY = arrayOf("weekly","monthly", "historical")



    override fun getLayoutResources(): Int {
        return R.layout.fragment_hot
    }

    override fun initView() {
        var weekFragment : RankFragment = RankFragment()
        var weekBundle = Bundle()
        weekBundle.putString("strategy", STRATEGY[0])
        weekFragment.arguments = weekBundle

        var monthFragment : RankFragment = RankFragment()
        var monthBundle = Bundle()
        monthBundle.putString("strategy", STRATEGY[1])
        monthFragment.arguments = monthBundle

        var allFragment : RankFragment = RankFragment()
        var allBundle = Bundle()
        allBundle.putString("strategy", STRATEGY[2])
        allFragment.arguments = allBundle

        mFragments = ArrayList()
        mFragments.add(weekFragment)
        mFragments.add(monthFragment)
        mFragments.add(allFragment)

        vp_content.adapter = HotAdapter(fragmentManager!!, mFragments, mTabs)
        tabs.setupWithViewPager(vp_content)
    }


}
