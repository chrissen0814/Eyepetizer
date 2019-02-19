package com.chrissen.eyepetizer.ui.fragment


import android.content.DialogInterface
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.widget.DefaultItemAnimator
import android.text.TextUtils
import android.view.*
import android.widget.Toast
import com.chrissen.eyepetizer.R
import com.chrissen.eyepetizer.helper.CircularRevealAnim
import com.chrissen.eyepetizer.ui.ResultActivity
import com.chrissen.eyepetizer.ui.adapter.SearchAdapter
import com.chrissen.eyepetizer.utils.KeyboardUtils
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment : DialogFragment(), CircularRevealAnim.AnimListener, ViewTreeObserver.OnPreDrawListener, DialogInterface.OnKeyListener, View.OnClickListener {

    var data : MutableList<String> = arrayListOf("脱口秀","城会玩","666","笑cry","漫威",
            "清新","匠心","VR","心理学","舞蹈","品牌广告","粉丝自制","电影相关","萝莉","魔性"
            ,"第一视角","教程","毕业设计","奥斯卡","燃","冰与火之歌","温情","线下campaign","公益")
    lateinit var mRootView : View
    lateinit var mCircularRevealAnim: CircularRevealAnim
    lateinit var mAdapter : SearchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.DialogStyle)
    }

    override fun onStart() {
        super.onStart()
        initDialog()
    }

    private fun initDialog() {
        val window = dialog.window
        val metrics = resources.displayMetrics
        val width = (metrics.widthPixels * 0.98).toInt()
        window!!.setLayout(width, WindowManager.LayoutParams.MATCH_PARENT)
        window.setGravity(Gravity.TOP)
        window.setWindowAnimations(R.style.DialogEmptyAnimation)
    }

    override fun onHideAnimationEnd() {
        et_search_keyword.setText("")
        dismiss()
    }

    override fun onShowAnimationEnd() {
        if(isVisible){
            KeyboardUtils.openKeyboard(activity!!, et_search_keyword)
        }
    }

    override fun onPreDraw(): Boolean {
        iv_search_search.viewTreeObserver.removeOnPreDrawListener(this)
        mCircularRevealAnim.show(iv_search_search, mRootView)
        return true
    }

    override fun onKey(dialog: DialogInterface?, keyCode: Int, event: KeyEvent?): Boolean {
        if(keyCode == KeyEvent.KEYCODE_BACK && event?.action == KeyEvent.ACTION_UP){
            hideAnim()
        }else if(keyCode == KeyEvent.KEYCODE_ENTER && event?.action == KeyEvent.ACTION_DOWN){
            search()
        }
        return false
    }

    private fun search() {
        val searchKey = et_search_keyword.text.toString()
        if(TextUtils.isEmpty(searchKey.trim({it <= ' '}))){
            Toast.makeText(activity!!, "请输入关键字", Toast.LENGTH_SHORT).show()
        }else{
            hideAnim()
            var keyWord = et_search_keyword.text.toString().trim()
            var intent : Intent = Intent(activity!!, ResultActivity::class.java)
            intent.putExtra("keyWord", keyWord)
            activity?.startActivity(intent)
        }
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.iv_search_back -> {
                hideAnim()
            }
            R.id.iv_search_search -> {
                search()
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        mRootView = inflater.inflate(R.layout.fragment_search, container, false)
        return mRootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        setData()
    }

    private fun setData() {
        mAdapter = SearchAdapter(activity!!, data as ArrayList<String>)
        mAdapter.setListener( object : SearchAdapter.OnDialogDismissListener{
            override fun onDismiss() {
                hideAnim()
            }
        })
        val manager = FlexboxLayoutManager()
        manager.flexDirection = FlexDirection.ROW
        manager.flexWrap = FlexWrap.WRAP
        recyclerView.layoutManager = manager
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.adapter = mAdapter
    }

    private fun hideAnim() {
        KeyboardUtils.closeKeyboard(activity!!, et_search_keyword)
        mCircularRevealAnim.hide(iv_search_search, mRootView)
    }

    private fun init() {
        tv_hint.typeface = Typeface.createFromAsset(activity?.assets, "fonts/FZLanTingHeiS-DB1-GB-Regular.TTF")
        mCircularRevealAnim = CircularRevealAnim()
        mCircularRevealAnim.setAnimListener(this)
        dialog.setOnKeyListener(this)
        iv_search_search.viewTreeObserver.addOnPreDrawListener(this)
        iv_search_search.setOnClickListener(this)
        iv_search_back.setOnClickListener(this)
    }


}
