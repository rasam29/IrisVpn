package com.irisvpn.android.di

import android.content.Context
import android.os.Build
import com.irisvpn.android.di.modules.adModule
import com.irisvpn.android.di.modules.excludeModule
import com.irisvpn.android.di.modules.mainModule
import com.irisvpn.android.di.modules.networkModule
import com.irisvpn.android.di.modules.settingModule
import com.irisvpn.android.di.modules.useCaseModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin
import java.io.File
import java.security.KeyStore
import javax.net.ssl.TrustManagerFactory
import kotlin.system.exitProcess


fun checkSecurity() {
    fun isRunningOnEmulator(): Boolean {
        return (Build.FINGERPRINT.contains("generic")
                || Build.MODEL.contains("google_sdk")
                || Build.MANUFACTURER.contains("Genymotion")
                || Build.BRAND.startsWith("generic")
                || Build.DEVICE.startsWith("generic"))
    }

    fun isDeviceRooted(): Boolean {
        val paths = arrayOf(
            "/system/app/Superuser.apk",
            "/sbin/su",
            "/system/bin/su",
            "/system/xbin/su",
            "/data/local/xbin/su",
            "/data/local/bin/su"
        )
        return paths.any { File(it).exists() }
    }

    fun hasUserAddedCertificates(): Boolean {
        return try {
            val userCertDir = File("/data/misc/user/0/cacerts-added")
            userCertDir.exists() && userCertDir.listFiles()?.isNotEmpty() == true
        } catch (e: Exception) {
            false
        }
    }

    fun usesUserAddedCAs(): Boolean {
        return try {
            val tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
            tmf.init(null as KeyStore?) // loads default system trust store

            val trustManagers = tmf.trustManagers
            trustManagers.any {
                it.javaClass.name.contains("TrustManagerImpl") &&
                        it.toString().contains("user") // crude, but sometimes effective
            }
        } catch (e: Exception) {
            false
        }
    }

    if (isRunningOnEmulator()) exitProcess(0)

    if (isDeviceRooted()) exitProcess(0)

    if (usesUserAddedCAs()) exitProcess(0)

    if (hasUserAddedCertificates()) exitProcess(0)
}

fun initiateAnalytics() {

}


fun startDi(androidContext: Context) {
    startKoin {
        androidContext(androidContext)
        modules(mainModule, settingModule, excludeModule, useCaseModule, networkModule, adModule)
    }
}

