package com.chrissen.eyepetizer.mvp.contract

import com.chrissen.eyepetizer.base.BasePresenter
import com.chrissen.eyepetizer.base.BaseView
import com.chrissen.eyepetizer.mvp.model.bean.HomeBean

/**
 *  Function:
 *  <br/>
 *  Describe:
 *  <br/>
 *  Author: chris on 2019/1/31.
 *  <br/>
 *  Email: chrissen0814@gmail.com
 */

interface HomeContract {

    interface View : BaseView<Presenter>{
        fun setData(bean : HomeBean)
    }

    interface Presenter : BasePresenter{
        fun requestData()
    }

}