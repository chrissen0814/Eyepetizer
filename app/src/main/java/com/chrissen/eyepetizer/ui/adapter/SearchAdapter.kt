package com.chrissen.eyepetizer.ui.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.chrissen.eyepetizer.R
import com.chrissen.eyepetizer.ui.ResultActivity
import com.google.android.flexbox.FlexboxLayoutManager

/**
 *  Function:
 *  <br/>
 *  Describe:
 *  <br/>
 *  Author: chris on 2019/2/19.
 *  <br/>
 *  Email: chrissen0814@gmail.com
 */

class SearchAdapter(context: Context, list: ArrayList<String>) : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    private var mContext : Context? = null
    private var mList : ArrayList<String>? = null
    var mInflater : LayoutInflater? = null
    var mListener : OnDialogDismissListener? = null

    init {
        mContext = context
        mList = list
        mInflater = LayoutInflater.from(mContext)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): SearchViewHolder {
        var view = mInflater?.inflate(R.layout.item_search, parent, false)
        return SearchViewHolder(mContext, view)
    }

    override fun getItemCount(): Int {
        return mList?.size ?: 0
    }

    override fun onBindViewHolder(holder: SearchViewHolder?, position: Int) {
        holder?.tv_title?.text = mList!![position]
        val params = holder?.tv_title?.layoutParams
        if(params is FlexboxLayoutManager.LayoutParams){
            (holder?.tv_title?.layoutParams as FlexboxLayoutManager.LayoutParams).flexGrow = 1.0f
        }
        holder?.itemView?.setOnClickListener {
            var keyWord = mList?.get(position)
            var intent : Intent = Intent(mContext, ResultActivity::class.java)
            intent.putExtra("keyWord", keyWord)
            mContext?.startActivity(intent)
            mListener?.onDismiss()
        }
    }


    class SearchViewHolder(context : Context?, itemView: View?) : RecyclerView.ViewHolder(itemView){
        var tv_title : TextView = itemView?.findViewById(R.id.tv_title) as TextView
    }

    interface OnDialogDismissListener{
        fun onDismiss()
    }

    fun setListener(listener: OnDialogDismissListener){
        mListener = listener
    }

}