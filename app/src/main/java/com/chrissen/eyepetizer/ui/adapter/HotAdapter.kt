package com.chrissen.eyepetizer.ui.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

/**
 *  Function:
 *  <br/>
 *  Describe:
 *  <br/>
 *  Author: chris on 2019/2/14.
 *  <br/>
 *  Email: chrissen0814@gmail.com
 */

class HotAdapter(var fragmentManager: FragmentManager, var list: ArrayList<Fragment>, titles : MutableList<String>) : FragmentPagerAdapter(fragmentManager) {

    var mFm : FragmentManager = fragmentManager
    var mList : ArrayList<Fragment> = list
    var mTitles : MutableList<String> = titles

    override fun getItem(position: Int): Fragment {
        return mList[position]
    }

    override fun getCount(): Int {
        return mList.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mTitles[position]
    }
}