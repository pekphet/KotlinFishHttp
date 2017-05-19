package cn.fish.kfish.net

/**
 * Created by fish on 17-5-19.
 */
interface KFishHttpCallback<T> {
    fun Success(t: Any?) {}
    fun Failed(msg: String) {}
}