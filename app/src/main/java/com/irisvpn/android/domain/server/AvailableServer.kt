package com.irisvpn.android.domain.server

import androidx.compose.ui.graphics.Color
import com.irisvpn.android.appConfig.PING_SERVER_INTERVAL
import com.irisvpn.android.appConfig.theme.ConnectTextColor
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient


@OptIn(DelicateCoroutinesApi::class)
@Serializable
data class AvailableServer(
    val id: Int,
    val name: String?,
    val host: String?,
    val flag: String?,
    val tags: String?,
    @Transient
    var ping: ShowingPing = ShowingPing.Disconnected()
) {
    init {
        host.takeIf { !it.isNullOrBlank() }?.also { host ->
            GlobalScope.launch(Dispatchers.IO) {
                while (true) {
                    ping = startPing(host).toProperPing()
                    delay(PING_SERVER_INTERVAL)
                }
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
            match?.groupValues?.get(1)?.toDoubleOrNull()?.toInt()?:Int.MAX_VALUE
        } else {
            Int.MAX_VALUE
        }
    } catch (e: Exception) {
        Int.MAX_VALUE
    }
}

data class ShowingPing(
    val value: String,
    val color: Color
) {
    companion object {
        fun Disconnected() = ShowingPing(
            value = "N/A",
            color = Color.Red
        )
    }
}

private fun Int.toProperPing(): ShowingPing {
    return when (this) {
        Int.MAX_VALUE -> ShowingPing.Disconnected()
        in 0..450 -> ShowingPing(
            "$this ms",
            Color.Green,
        )

        in 450..700 -> ShowingPing(
            "$this ms",
            Color.ConnectTextColor,
        )

        in 700..2000 -> ShowingPing(
            "$this ms",
            Color.Red
        )

        else -> ShowingPing(
            "N/A",
            Color.Red
        )
    }
}
