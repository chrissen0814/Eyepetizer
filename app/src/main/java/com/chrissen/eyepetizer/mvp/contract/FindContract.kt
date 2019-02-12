package com.chrissen.eyepetizer.mvp.contract

import com.chrissen.eyepetizer.base.BasePresenter
import com.chrissen.eyepetizer.base.BaseView
import com.chrissen.eyepetizer.mvp.model.bean.FindBean

/**
 *  Function:
 *  <br/>
 *  Describe:
 *  <br/>
 *  Author: chris on 2019/2/12.
 *  <br/>
 *  Email: chrissen0814@gmail.com
 */

interface FindContract {

    interface View : BaseView<Presenter>{
        fun setData(beans : MutableList<FindBean>)
    }

    interface Presenter : BasePresenter{
        fun requestData()
    }

}