package com.chrissen.eyepetizer.mvp.presenter

import android.content.Context
import com.chrissen.eyepetizer.mvp.contract.HomeContract
import com.chrissen.eyepetizer.mvp.model.HomeModel
import com.chrissen.eyepetizer.mvp.model.bean.HomeBean
import com.chrissen.eyepetizer.utils.applySchedulers
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 *  Function:
 *  <br/>
 *  Describe:
 *  <br/>
 *  Author: chris on 2019/1/31.
 *  <br/>
 *  Email: chrissen0814@gmail.com
 */

class HomePresenter(context: Context, view: HomeContract.View) : HomeContract.Presenter {

    var mContext : Context? = null
    var mView : HomeContract.View? = null
    //by 委托属性Delegate 延迟属性 Lazy ; 可观察属性 Observable; 把属性储存在映射中
    val mModel : HomeModel by lazy {
        HomeModel()
    }

    init {
        mView = view
        mContext = context
    }


    override fun requestData() {
        val observable : Observable<HomeBean>? = mContext?.let {
            mModel.loadData(it, true, "0")
        }
        observable?.applySchedulers()?.subscribe(object : Observer<HomeBean>{
            override fun onComplete() {

            }

            override fun onSubscribe(d: Disposable?) {

            }

            override fun onNext(t: HomeBean?) {
                mView?.setData(t!!)
            }

            override fun onError(e: Throwable?) {

            }
        })
    }

    override fun start() {
        requestData()
    }

    fun moreData(data: String?){
        val observable : Observable<HomeBean>? = mContext?.let {
            mModel.loadData(it, false, data)
        }
        //匿名内部类使用object : Class[考虑父类型有没有构造函数]
        observable?.applySchedulers()?.subscribe(object : Observer<HomeBean>{
            override fun onNext(t: HomeBean?) {
                mView?.setData(t!!)
            }

            override fun onComplete() {

            }

            override fun onSubscribe(d: Disposable?) {

            }

            override fun onError(e: Throwable?) {

            }
        })
    }
}