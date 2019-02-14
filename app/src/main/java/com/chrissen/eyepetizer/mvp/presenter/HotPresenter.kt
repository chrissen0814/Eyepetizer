package com.chrissen.eyepetizer.mvp.presenter

import android.content.Context
import com.chrissen.eyepetizer.mvp.contract.HotContract
import com.chrissen.eyepetizer.mvp.model.HotModel
import com.chrissen.eyepetizer.mvp.model.bean.HotBean
import com.chrissen.eyepetizer.utils.applySchedulers
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 *  Function:
 *  <br/>
 *  Describe:
 *  <br/>
 *  Author: chris on 2019/2/14.
 *  <br/>
 *  Email: chrissen0814@gmail.com
 */

class HotPresenter(context: Context, view : HotContract.View) : HotContract.Presenter {

    var mContext : Context? = null
    var mView : HotContract.View? = null
    val mModel : HotModel by lazy {
        HotModel()
    }

    init {
        mContext = context
        mView = view
    }

    override fun requestData(strategy: String) {
        val observable: Observable<HotBean>? = mContext?.let { mModel.loadData(mContext!!, strategy) }
        observable?.applySchedulers()?.subscribe(object : Observer<HotBean>{
            override fun onComplete() {

            }

            override fun onSubscribe(d: Disposable?) {

            }

            override fun onNext(t: HotBean) {
                mView?.setData(t)
            }

            override fun onError(e: Throwable?) {

            }
        })
    }

    override fun start() {

    }
}