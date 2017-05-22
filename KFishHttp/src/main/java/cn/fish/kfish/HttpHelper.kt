package cn.fish.kfish

import android.content.Context
import android.os.Handler
import cn.fish.kfish.domain.Jsonable
import cn.fish.kfish.net.Requester
import java.io.InputStream
import java.io.InputStreamReader
import java.util.concurrent.Executors

/**
 * Created by fish on 17-5-19.
 */
class HttpHelper {
    companion object {
        val MAX_POOL_NUM = Runtime.getRuntime().availableProcessors() * 2
        val EXEC_POOL = Executors.newScheduledThreadPool(MAX_POOL_NUM)

        inline fun <reified T : Jsonable>req(context: Context, handler: Handler, req: Requester<T>) {
            EXEC_POOL.execute {
                var conn = req.getConn()
                conn?.connect()
                try {
                    when (req) {
                        is Requester.SimpleGet, is Requester.ParamGet -> {}
                        else -> req.addData(conn?.outputStream!!)
                    }
                    val code = conn?.responseCode
                    if (code != 200) {
                        handler.post { req.mCallback?.Failed("BAD HTTP REQ,CODE:$code") }
                        return@execute
                    }
                    var respData = getStrFromInputStream(conn?.inputStream)
                    handler.post {req.mCallback?.Success(Jsonable.createFromJson(respData))}
                } catch (e: Exception) {
                    e.printStackTrace()
                } finally {
                    try {
                        req.mConn?.outputStream?.close()
                    } catch (ex: Exception) {
                    }
                }
            }
        }
        //TODO MAKE IT EASIER
        fun getStrFromInputStream(input: InputStream?): String {
            val buf = StringBuilder()
            val reader = InputStreamReader(input)
            val charBuf = CharArray(1024)
            var count = reader.read(charBuf)
            while (count > -1) {
                buf.append(charBuf, 0, count)
                count = reader.read(charBuf)
            }
            reader.close()
            return buf.toString()
        }
    }
}