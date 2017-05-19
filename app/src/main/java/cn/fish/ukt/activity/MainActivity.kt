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
import cn.fish.ukt.entity.Person

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tryNet()
        findViewById(R.id.tv1).setOnClickListener {
            val intent = Intent(this@MainActivity, Main2Activity::class.java)
            intent.putExtra(KEY_PERSON, Person(age = 8))
            startActivity(intent)
        }
    }

    var up = ArrayList<Pair<String, String>>()

    fun tryNet() {
        up.add(Pair("content", "123123"))
        var req = Requester.SimplePost<String>(url = "http://192.168.1.201:59001", urlParam = up, callback = object : KFishHttpCallback<String>{
            override fun Failed(msg: String) {
                super.Failed(msg)
                ZLog.e("failed", msg)
            }

            override fun Success(t: Any?) {
                super.Success(t)
                ZLog.e("SUCCESS", t as String)
            }
        })

        HttpHelper.req(MainActivity@this, Handler(Looper.getMainLooper()), req)
    }

    companion object {

        val KEY_PERSON = "person"
    }
}
