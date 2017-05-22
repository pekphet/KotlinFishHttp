package cn.fish.kfish.net

/**
 * Created by fish on 17-5-19.
 */
interface KFishHttpCallback<in T> {
    fun Success(t: T) {}
    fun Failed(msg: String) {}
}