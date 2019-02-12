package com.chrissen.eyepetizer.ui.fragment


import android.support.v4.app.Fragment
import android.widget.AdapterView
import com.chrissen.eyepetizer.R
import com.chrissen.eyepetizer.mvp.contract.FindContract
import com.chrissen.eyepetizer.mvp.model.bean.FindBean
import com.chrissen.eyepetizer.mvp.presenter.FindPresenter
import com.chrissen.eyepetizer.ui.adapter.FindAdapter
import kotlinx.android.synthetic.main.fragment_find.*

/**
 * A simple [Fragment] subclass.
 *
 */
class FindFragment : BaseFragment(), FindContract.View {

    var mPresenter : FindPresenter? = null
    var mAdapter : FindAdapter? = null
    var mList : MutableList<FindBean>? = null


    override fun setData(beans: MutableList<FindBean>) {
        mAdapter?.mList = beans
        mList = beans
        mAdapter?.notifyDataSetChanged()
    }

    override fun getLayoutResources(): Int {
        return R.layout.fragment_find
    }

    override fun initView() {
        mPresenter = FindPresenter(context!!, this)
        mPresenter?.start()
        mAdapter = FindAdapter(context!!, mList)
        gv_find.adapter = mAdapter
        gv_find.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            var bean = mList?.get(position)
            var name = bean?.name
        }
    }


}
