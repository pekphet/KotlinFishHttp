package cn.fish.ukt.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.support.v7.app.AppCompatActivity
import cn.fish.kfish.HttpHelper
import cn.fish.kfish.ZLog
import cn.fish.kfish.net.KFishHttpCallback
import cn.fish.kfish.net.Requester
import cn.fish.ukt.R
import cn.fish.ukt.entity.Data

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tryNet()
        findViewById(R.id.tv1).setOnClickListener {
            val intent = Intent(this@MainActivity, Main2Activity::class.java)
            startActivity(intent)
        }
    }
    fun tryNet() {
        var up = listOf<Pair<String, String>>(Pair("content", "123456"))

        var req = Requester.SimpleGet<Data.EncryptData>(url = "http://45.78.46.200:59001/test/json/encrypt", urlParam = up, callback = object : KFishHttpCallback<Data.EncryptData>{
            override fun Failed(msg: String) {
                super.Failed(msg)
                ZLog.e("failed", msg)
            }

            override fun Success(t: Data.EncryptData) {
                super.Success(t)
                ZLog.e("Sucess", "Source is ${t.con}, MD5 Result is ${t.m}")
            }
        })

        HttpHelper.req(MainActivity@this, Handler(Looper.getMainLooper()), req)
    }
}
