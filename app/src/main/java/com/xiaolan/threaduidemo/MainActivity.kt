package com.xiaolan.threaduidemo

import android.graphics.Color
import android.graphics.PixelFormat
import android.os.Bundle
import android.os.Looper
import android.support.v7.app.AppCompatActivity
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.TextView

/**
 * 为什么不能在子线程中更新UI
 * https://blog.csdn.net/haoyuegongzi/article/details/79414081
 * https://www.cnblogs.com/lao-liang/p/5108745.html
 */
class MainActivity : AppCompatActivity() {
    var tv1:TextView?  = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        Thread {
            kotlin.run {
                Thread.sleep(1000)
                Looper.prepare()
                tv1 = TextView(this)
                tv1?.setTextColor(Color.WHITE)
                val a = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT)
                tv1?.layoutParams = a
                tv1?.text = "oncreate"
                val windowManager = this@MainActivity.windowManager
                val params = WindowManager.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 200, 200, WindowManager.LayoutParams.FIRST_SUB_WINDOW,
                        WindowManager.LayoutParams.TYPE_TOAST, PixelFormat.OPAQUE)
                windowManager.addView(tv1, params)
                Looper.loop()
            }
        }.start()
    }


}
