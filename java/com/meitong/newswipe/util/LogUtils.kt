package com.meitong.newswipe.util

import android.util.Log


object LogUtils {


    fun e(msg: String?) {
        if (isDebug && !msg.isNullOrEmpty()) {
            val stackTraceElement = Thread.currentThread().stackTrace[3]
            val tag = getDefaultTag(stackTraceElement)
            Log.e(tag, "<<<<<<<<<<<--------------")
            Log.e(tag, msg.toUTF8())
            Log.e(tag, getLogInfo(stackTraceElement))
        }
    }

    fun e(tag: String?, msg: String?) {
        if (isDebug && !msg.isNullOrEmpty()) {
            val stackTraceElement = Thread.currentThread().stackTrace[3]
            val tag1 = getDefaultTag(stackTraceElement)
            Log.e(tag1, "<<<<<<<<<<<--------------")
            Log.e("$tag1 $tag", msg.toUTF8())
            Log.e(tag1, getLogInfo(stackTraceElement))
        }
    }

    fun i(msg: String?) {
        if (isDebug && !msg.isNullOrEmpty()) {
            val stackTraceElement = Thread.currentThread().stackTrace[3]
            val tag = getDefaultTag(stackTraceElement)
            Log.i(tag, "<<<<<<<<<<<--------------")
            Log.i(tag, msg.toUTF8())
            Log.i("$tag", getLogInfo(stackTraceElement))
        }
    }

    fun i(tag: String?, msg: String?) {
        if (isDebug && !msg.isNullOrEmpty()) {
            val stackTraceElement = Thread.currentThread().stackTrace[3]
            val tag1 = getDefaultTag(stackTraceElement)
            Log.i(tag1, "<<<<<<<<<<<--------------")
            Log.i("$tag1 $tag", msg.toUTF8())
            Log.i(tag1, getLogInfo(stackTraceElement))
        }
    }


    //是否打印
    private val isDebug = true

    //分隔符
    private const val separator = ","


    /**
     * 获取默认的 TAG 名称
     */
    private fun getDefaultTag(stackTraceElement: StackTraceElement): String {
        val fileName = stackTraceElement.fileName
        val stringArray = fileName.split("\\.")
        return if (stringArray.isNotEmpty()) {
            stringArray[0]
        } else {
            ""
        }
    }

    /**
     * 获取相信线程信息
     */
    private fun getLogInfo(stackTraceElement: StackTraceElement): String {
        val logInfo = StringBuilder()
        //获取线程名
        val threadName = Thread.currentThread().name
        //线程ID
        val threadID = Thread.currentThread().id
        //文件名 xx.java
        val fileName = stackTraceElement.fileName
        //类名
//        val className = stackTraceElement.className
        //方法名
        val methodName = stackTraceElement.methodName
        //执行行数
        val lineNumber = stackTraceElement.lineNumber

        logInfo.append("   >[ ")
        logInfo.append(" threadID=$threadID").append(separator)
        logInfo.append(" threadName=$threadName").append(separator)
        logInfo.append(" fileName=$fileName").append(separator)
//        logInfo.append(" className=$className").append(separator)
        logInfo.append(" methodName=$methodName").append(separator)
        logInfo.append(" lineNumber=$lineNumber")
        logInfo.append(" ] ")
        return logInfo.toString()
    }


}


fun String?.toUTF8(): String {
    if (this.isNullOrEmpty()) {
        return ""
    }
    val out = StringBuilder()
    var i = 0
    while (i < this.length) {
        val c = this[i]
        if (i + 6 < this.length && c == '\\' && this[i + 1] == 'u') {
            val hex = this.substring(i + 2, i + 6)
            try {
                out.append(hex.toInt(16).toChar())
            } catch (nfe: NumberFormatException) {
                nfe.fillInStackTrace()
            }
            i += 6
        } else {
            out.append(this[i])
            ++i
        }
    }
    return out.toString()
}
