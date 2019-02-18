package com.chrissen.eyepetizer.ui

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.chrissen.eyepetizer.R
import com.gyf.barlibrary.ImmersionBar
import kotlinx.android.synthetic.main.activity_advice.*

class AdviceActivity : AppCompatActivity(), View.OnClickListener {


    override fun onClick(v: View?) {
        when(v?.id){
            R.id.tvEmail -> {
                try {
                    var intent = Intent(Intent.ACTION_SENDTO)
                    intent.data = Uri.parse("mailto:${tvEmail.text}")
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Eyepetizer项目反馈")
                    startActivity(intent)
                } catch (e: ActivityNotFoundException) {
                    Toast.makeText(this, "没有找到邮箱应用", Toast.LENGTH_SHORT).show()
                }
            }

            R.id.tvGithub -> {
                try {
                    var intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse(tvGithub.text.toString())
                    startActivity(intent)
                } catch (e: ActivityNotFoundException) {
                    Toast.makeText(this, "没有找到浏览器", Toast.LENGTH_SHORT).show()
                }
            }

            R.id.tvBlog -> {
                try {
                    var intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse(tvBlog.text.toString())
                    startActivity(intent)
                } catch (e: ActivityNotFoundException) {
                    Toast.makeText(this, "没有找到浏览器", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_advice)
        ImmersionBar.with(this).transparentBar().barAlpha(0.3f).fitsSystemWindows(true).init()
        setToolbar()
        tvEmail.setOnClickListener(this)
        tvGithub.setOnClickListener(this)
        tvBlog.setOnClickListener(this)
    }

    private fun setToolbar() {
        setSupportActionBar(adviceToolbar)
        var bar = supportActionBar
        bar?.title = "意见反馈"
        bar?.setDisplayHomeAsUpEnabled(true)
        adviceToolbar.setNavigationOnClickListener { onBackPressed() }
    }
}
