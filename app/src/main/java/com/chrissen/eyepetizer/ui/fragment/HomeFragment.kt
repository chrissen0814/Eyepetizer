package com.chrissen.eyepetizer.ui.fragment


import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.chrissen.eyepetizer.R
import com.chrissen.eyepetizer.mvp.contract.HomeContract
import com.chrissen.eyepetizer.mvp.model.bean.HomeBean
import com.chrissen.eyepetizer.mvp.presenter.HomePresenter
import com.chrissen.eyepetizer.ui.adapter.HomeAdapter
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.regex.Pattern

/**
 * A simple [Fragment] subclass.
 *
 */
class HomeFragment : BaseFragment() , HomeContract.View, SwipeRefreshLayout.OnRefreshListener{

    var mIsRefresh: Boolean = false
    var mPresenter : HomePresenter? = null
    var mList = ArrayList<HomeBean.IssueListBean.ItemListBean>()
    var mAdapter : HomeAdapter? = null
    var data : String? = null

    override fun onRefresh() {
        if (mIsRefresh.not()) {
            mIsRefresh = true
            mPresenter?.start()
        }
    }


    override fun setData(bean: HomeBean) {
        val regEx = "[^0-9]"
        val p = Pattern.compile(regEx)
        val m = p.matcher(bean?.nextPageUrl)
        data = m.replaceAll("").subSequence(1, m.replaceAll("").length - 1).toString()
        if (mIsRefresh) {
            mIsRefresh = false
            refreshLayout.isRefreshing = false
            if(mList.size > 0){
                mList.clear()
            }
        }
        bean.issueList!!
                .flatMap { it.itemList!! }
                .filter { it.type.equals("video") }
                .forEach{mList.add(it)}
        mAdapter?.notifyDataSetChanged()
    }


    override fun getLayoutResources(): Int {
        return R.layout.fragment_home
    }

    override fun initView() {
        mPresenter = HomePresenter(context!!, this)
        mPresenter?.start()
        recyclerView.layoutManager = LinearLayoutManager(context)
        mAdapter = HomeAdapter(context!!, mList)
        recyclerView.adapter = mAdapter
        refreshLayout.setOnRefreshListener(this)
        recyclerView.addOnScrollListener(object  : RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                var layoutManager : LinearLayoutManager = recyclerView?.layoutManager as LinearLayoutManager
                var lastPosition = layoutManager.findLastVisibleItemPosition()
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastPosition == mList.size - 1) {
                    if (data != null){
                        mPresenter?.moreData(data)
                    }
                }
            }
        })
    }


}
