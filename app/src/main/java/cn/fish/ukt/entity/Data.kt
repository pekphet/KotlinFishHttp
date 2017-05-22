package cn.fish.ukt.entity

import cn.fish.kfish.domain.Jsonable

/**
 * Created by fish on 17-5-22.
 */
class Data {
    data class Person(val name: String, val age: Int) : Jsonable
    data class EncryptData(val con: String, val m: String) : Jsonable
}