package com.irisvpn.android.androidSpecific

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.irisvpn.android.utils.PlatformState
import com.irisvpn.android.widgets.InstalledApp
import kotlin.system.exitProcess


@Stable
class AndroidPlatformState(val context: Context) : PlatformState {
    override fun launchTelegram(telegramChannelName: String) {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse("tg://resolve?domain=$telegramChannelName")
        }
        try {
            context.startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            e.printStackTrace()
            val fallbackIntent =
                Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/$telegramChannelName"))
            context.startActivity(fallbackIntent)
        }
    }

    override fun effectOnConnect() {
        val vibrateDuration = 100L
        val vibrator = ContextCompat.getSystemService(context, Vibrator::class.java) ?: return
        if (!vibrator.hasVibrator()) return
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val effect =
                VibrationEffect.createOneShot(vibrateDuration, VibrationEffect.DEFAULT_AMPLITUDE)
            vibrator.vibrate(effect)
        } else {
            vibrator.vibrate(vibrateDuration)
        }
    }

    override fun closeApplication() {
        exitProcess(0)
    }
}


@Composable
fun rememberPlatformState(): PlatformState {
    val context = LocalContext.current
    val state = remember(context) {
        AndroidPlatformState(context)
    }
    return state
}