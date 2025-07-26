package com.irisvpn.android.domain.baseUrl

import com.irisvpn.android.appConfig.PING_URL_INTERVAL
import com.irisvpn.android.appConfig.SORT_URL_INTERVAL
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


data class BaseUrl(
    val url: String, var ping: Int = Int.MAX_VALUE
) {
    init {
        url.takeIf { !it.isNullOrBlank() }?.also { host ->
            GlobalScope.launch(Dispatchers.IO) {
                while (true) {
                    ping = startPing(host)
                    delay(PING_URL_INTERVAL)
                }
            }
        }
    }

    private fun startPing(ip: String): Int {
        return try {
            val process = Runtime.getRuntime().exec("/system/bin/ping -c 1 $ip")
            val exitCode = process.waitFor()
            if (exitCode == 0) {
                val reader = process.inputStream.bufferedReader()
                val output = reader.readText()
                val timeRegex = "time=([0-9.]+) ms".toRegex()
                val match = timeRegex.find(output)
                match?.groupValues?.get(1)?.toDoubleOrNull()?.toInt() ?: Int.MAX_VALUE
            } else {
                Int.MAX_VALUE
            }
        } catch (e: Exception) {
            Int.MAX_VALUE
        }
    }
}

interface BaseUrlRepository {
    fun getBaseUrlWithBasePing(): String
    fun setAllUrlList(list: List<BaseUrl>)
}

class InMemoryBaseUrlRepository() : BaseUrlRepository {
    private var urlList: List<BaseUrl> = listOf(BaseUrl("https://iris-service.shop/api/v1/"))

    init {
        GlobalScope.launch(Dispatchers.IO) {
            while (true) {
                urlList = urlList.sortedBy {
                    it.ping
                }
                delay(SORT_URL_INTERVAL)
            }
        }
    }

    override fun getBaseUrlWithBasePing(): String = urlList.first().url.normalizedUrl()

    override fun setAllUrlList(list: List<BaseUrl>) {
        this.urlList = list
    }

}

private fun String.normalizedUrl(): String = if (endsWith("/")) this else "$this/"