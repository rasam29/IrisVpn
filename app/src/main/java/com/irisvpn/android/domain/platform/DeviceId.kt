package com.irisvpn.android.domain.platform

import android.content.Context
import android.os.Build
import android.provider.Settings
import java.security.MessageDigest

interface DeviceIdRepository {
    fun generateUniqueId(): String
}

class AndroidDeviceIdRepository(val context: Context) : DeviceIdRepository {

    val uniqueId by lazy {
        sha256(Build.MODEL + Build.MANUFACTURER + Build.BRAND + Build.DEVICE + Build.HARDWARE + Build.PRODUCT + Build.VERSION.SDK_INT + Settings.Secure.ANDROID_ID)
    }

    override fun generateUniqueId(): String = uniqueId

    private fun sha256(input: String): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val hashBytes = digest.digest(input.toByteArray(Charsets.UTF_8))
        return hashBytes.joinToString("") { "%02x".format(it) }
    }
}