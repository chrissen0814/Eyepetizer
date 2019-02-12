package com.chrissen.eyepetizer.mvp.presenter

import android.content.Context
import com.chrissen.eyepetizer.mvp.contract.FindContract
import com.chrissen.eyepetizer.mvp.model.FindModel
import com.chrissen.eyepetizer.mvp.model.bean.FindBean
import com.chrissen.eyepetizer.utils.applySchedulers
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 *  Function:
 *  <br/>
 *  Describe:
 *  <br/>
 *  Author: chris on 2019/2/12.
 *  <br/>
 *  Email: chrissen0814@gmail.com
 */

class FindPresenter(context: Context, view : FindContract.View) : FindContract.Presenter {

    var mContext : Context? = null
    var mView : FindContract.View? = null
    val mModel : FindModel by lazy {
        FindModel()
    }

    init {
        mView = view
        mContext = context
    }

    override fun requestData() {
        val observable : Observable<MutableList<FindBean>>? = mContext?.let {
            mModel.loadData(mContext!!)
        }
        observable?.applySchedulers()?.subscribe(object : Observer<MutableList<FindBean>>{
            override fun onComplete() {

            }

            override fun onSubscribe(d: Disposable?) {

            }

            override fun onNext(t: MutableList<FindBean>) {
                mView?.setData(t)
            }

            override fun onError(e: Throwable?) {

            }
        })
    }

    override fun start() {
        requestData()
    }
}