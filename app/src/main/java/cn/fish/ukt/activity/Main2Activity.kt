package cn.fish.ukt.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import cn.fish.ukt.R
import cn.fish.ukt.entity.Person

class Main2Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        initView()
    }

    fun initView() {
        var tv = findViewById(R.id.tv) as TextView
        val (name, age) = intent.getSerializableExtra(MainActivity.KEY_PERSON) as Person
        val content:String = "name:${name}   age:${age}"
        tv.text = content
    }
}
