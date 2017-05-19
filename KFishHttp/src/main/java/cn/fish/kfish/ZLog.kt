package cn.fish.kfish

import android.util.Log

/**
 * Created by fish on 17-5-19.
 */
sealed class ZLog {

    public companion object {
        val open = true
        fun e(tag: String, content:String) {
            if (open) {
                Log.e(tag, content)
            }
        }
        fun d(tag: String, content:String) {
            if (open) {
                Log.d(tag, content)
            }
        }
    }




}