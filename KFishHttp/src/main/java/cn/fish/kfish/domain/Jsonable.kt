package cn.fish.kfish.domain

import com.google.gson.Gson

/**
 * Created by fish on 17-5-22.
 */
open interface Jsonable {
    fun toJson(): String = Gson().toJson(this)
    companion object {
        inline fun <reified T : Jsonable>createFromJson(src: String): T {
            return Gson().fromJson(src, T::class.java)
        }
    }
}