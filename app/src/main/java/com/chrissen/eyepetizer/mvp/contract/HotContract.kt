package com.chrissen.eyepetizer.mvp.contract

import com.chrissen.eyepetizer.base.BasePresenter
import com.chrissen.eyepetizer.base.BaseView
import com.chrissen.eyepetizer.mvp.model.bean.HotBean

/**
 *  Function:
 *  <br/>
 *  Describe:
 *  <br/>
 *  Author: chris on 2019/2/14.
 *  <br/>
 *  Email: chrissen0814@gmail.com
 */

interface HotContract {

    interface View : BaseView<Presenter>{
        fun setData(bean : HotBean)
    }

    interface Presenter : BasePresenter{
        fun requestData(strategy : String)
    }

}