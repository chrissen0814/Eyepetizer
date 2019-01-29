package com.chrissen.eyepetizer.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.Gravity
import android.widget.Toast
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 *  Function:扩展函数
 *  <br/>
 *  Describe:扩展一个类的新功能而无需继承该类或使用像装饰者这样的任何类型的设计模式。
 *  声明一个扩展函数，我们需要用一个 接收者类型 也就是被扩展的类型来作为他的前缀。
 *  <br/>
 *  Author: chris on 2019/1/29.
 *  <br/>
 *  Email: chrissen0814@gmail.com
 */

fun Context.showToast(message : String) : Toast {
    var toast : Toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
    toast.setGravity(Gravity.CENTER, 0, 0)
    toast.show()
    return toast
}

/**
 * inline【内联】 的工作原理就是将内联函数的函数体复制到调用处实现内联。
 * Functions should only be made inline when they use inline-only features like inlined lambda parameters or reified types.
 * 关于这个我还真是疑惑过，说内联就是编译器会在调用的地方直接把整个函数复制过去，
 * 那么由此可能引发的问题就是代码膨胀。你不可能把一个1000行的函数定义为inline，
 * 因为直接复制过去的开销比较大。在写Kotlin的时候也碰到过编译器警告，
 * 最后一句话是inlining works best for functions with lambda parameters，
 * 跟这的意思貌似差不多。自言自语到这我好像有点明白了，普通的函数调用需要入栈出栈，
 * 而内联是直接将函数复制到调用处，有函数的特点，但不需要入栈出栈，效率比较高。
 *
 * reified[具体化] 这个是为了满足inline特性而设计的语法糖，因为给函数使用内联之后，编译器会用其函数体来替换掉函数调用，
 * 而如果该函数里面有泛型就可能会出现编译器不懂该泛型的问题，所以引入reified，使该泛型被智能替换成对应的类型
 */
inline fun<reified T : Activity> Activity.newIntent(){
    val intent = Intent(this, T::class.java)
    startActivity(intent)
}

fun<T> Observable<T>.applySchedulers() : Observable<T>{
    return subscribeOn(Schedulers.io())
            .unsubscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}