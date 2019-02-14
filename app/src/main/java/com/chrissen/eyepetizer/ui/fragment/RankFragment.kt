package com.chrissen.eyepetizer.ui.fragment


import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import com.chrissen.eyepetizer.R
import com.chrissen.eyepetizer.mvp.contract.HotContract
import com.chrissen.eyepetizer.mvp.model.bean.HotBean
import com.chrissen.eyepetizer.mvp.presenter.HotPresenter
import com.chrissen.eyepetizer.ui.adapter.RankAdapter
import kotlinx.android.synthetic.main.fragment_home.*


/**
 * A simple [Fragment] subclass.
 *
 */
class RankFragment : BaseFragment(), HotContract.View {

    lateinit var mPresenter : HotPresenter
    lateinit var mStrategy: String
    lateinit var mAdapter : RankAdapter
    var mList : ArrayList<HotBean.ItemListBean.DataBean> = ArrayList()

    override fun setData(bean: HotBean) {
        if(mList.size > 0){
            mList.clear()
        }
        bean.itemList?.forEach { it.data?.let { itl -> mList.add(itl) } }
        mAdapter.notifyDataSetChanged()
    }

    override fun getLayoutResources(): Int {
        return R.layout.fragment_rank
    }

    override fun initView() {
        recyclerView.layoutManager = LinearLayoutManager(context)
        mAdapter = RankAdapter(context!!, mList)
        recyclerView.adapter = mAdapter
        if (arguments != null) {
            mStrategy = arguments!!.getString("strategy")
            mPresenter = HotPresenter(context!!, this)
            mPresenter.requestData(mStrategy)
        }
    }


}
