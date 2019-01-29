package com.chrissen.eyepetizer.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 *  Function:
 *  <br/>
 *  Describe:
 *  <br/>
 *  Author: chris on 2019/1/29.
 *  <br/>
 *  Email: chrissen0814@gmail.com
 */

abstract class BaseFragment : Fragment(){
    var isFirst : Boolean = false
    var rootView : View? = null
    var isFragmentVisible : Boolean = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (rootView == null) {
            rootView = inflater.inflate(getLayoutResources(), container, false)
        }
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            isFragmentVisible = true
        }

        if (rootView == null) {
            return
        }

        if (!isFirst && isFragmentVisible) {
            onFragmentVisibleChange(true)
            return
        }

        if (isFragmentVisible) {
            onFragmentVisibleChange(false)
            isFragmentVisible = false
        }
    }

    open protected fun onFragmentVisibleChange(b : Boolean){

    }

    abstract fun getLayoutResources() : Int

    abstract fun initView()

}