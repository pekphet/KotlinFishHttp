package cn.fish.kfish.net

import cn.fish.kfish.ZLog
import cn.fish.kfish.domain.Jsonable
import java.io.OutputStream
import java.net.HttpURLConnection
import java.net.URL

/**
 * Created by fish on 17-5-19.
 */
sealed class Requester<T : Jsonable>(url: String = "",
                          urlParam: List<Pair<String, String>>? = null,
                          headerParam: List<Pair<String, String>>? = null,
                          callback: KFishHttpCallback<T>? = null,
                          data: Any? = null) {
    companion object {
        val CONN_TIMEOUT = 5000
        val MUL_UPLOAD_BOUNDARY = "Boundary+AAABBB2212341234"
    }

    var mConn: HttpURLConnection? = null
    var mUrl = url
    var mUrlParam: List<Pair<String, String>>? = urlParam
    var mHeaderParam: List<Pair<String, String>>? = headerParam
    var mCallback: KFishHttpCallback<T>? = callback
    var mData: Any? = data


    class SimpleGet<T : Jsonable>(url: String,
                       urlParam: List<Pair<String, String>>? = null,
                       headerParam: List<Pair<String, String>>? = null,
                       callback: KFishHttpCallback<T>? = null) : Requester<T>(url, urlParam, headerParam, callback)

    class ParamGet<T : Jsonable>(url: String,
                      urlParam: List<Pair<String, String>>? = null,
                      headerParam: List<Pair<String, String>>? = null,
                      callback: KFishHttpCallback<T>? = null) : Requester<T>(url, urlParam, headerParam, callback)

    class SimplePost<T : Jsonable>(url: String,
                        urlParam: List<Pair<String, String>>? = null,
                        headerParam: List<Pair<String, String>>? = null,
                        callback: KFishHttpCallback<T>? = null,
                        data: List<Pair<String, String>>? = null) : Requester<T>(url, urlParam, headerParam, callback, data)

    class FormPost<T : Jsonable>(url: String,
                      urlParam: List<Pair<String, String>>? = null,
                      headerParam: List<Pair<String, String>>? = null,
                      callback: KFishHttpCallback<T>? = null,
                      data: List<Pair<String, String>>) : Requester<T>(url, urlParam, headerParam, callback, data)

//    class UploadFile<T>(url: String,
//                        urlParam: ArrayList<Pair<String, String>>? = null,
//                        headerParam: ArrayList<Pair<String, String>>? = null,
//                        callback: KFishHttpCallback<T>? = null,
//                        file: String) : Requester<T>(url, urlParam, headerParam, callback)

    class JsonPost<T : Jsonable>(url: String,
                      urlParam: List<Pair<String, String>>? = null,
                      headerParam: List<Pair<String, String>>? = null,
                      callback: KFishHttpCallback<T>? = null,
                      json: String) : Requester<T>(url, urlParam, headerParam, callback, json)

    fun getConn(): HttpURLConnection? {
        try {
            var isLooped = false
            mUrlParam?.forEach {
                mUrl = mUrl + if (!isLooped) {
                    isLooped = true
                    "?"
                } else "&"
                mUrl = mUrl + "${it.first}=${it.second}"
            }
            var conn = URL(mUrl).openConnection() as HttpURLConnection
            ZLog.e("URL", mUrl)
            conn.connectTimeout = CONN_TIMEOUT
            conn.doInput = true
            conn.useCaches = false
            conn.setRequestProperty("Charset", "UTF-8")
            conn.setRequestProperty("Connection", "Keep-Alive")
            conn.requestMethod = when (Requester@ this) {
                is SimpleGet, is ParamGet -> "GET"
                else -> "POST"
            }
            conn.setRequestProperty("Content-Type", when (Requester@ this) {
                is JsonPost -> "application/json"
//                is UploadFile -> "multipart/form-data; boundary=$MUL_UPLOAD_BOUNDARY"
                else -> "application/x-www-form-urlencoded"
            })
            mHeaderParam?.forEach {
                conn.addRequestProperty(it.first, it.second)
            }
            mConn = conn
            return conn
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    fun addData(output: OutputStream) {
        output.write(when (Requester@ this) {
            is JsonPost -> (mData as String?)?.toByteArray()
            else -> {
                var looped = false
                var tmpData: String = ""
                (mData as ArrayList<Pair<String, String>>?)?.forEach {
                    tmpData = tmpData + if (!looped) "" else "&" + "${it.first}=${it.second}"
                }
                tmpData.toByteArray()
            }
        })
        output.flush()
        output.close()
    }
}
