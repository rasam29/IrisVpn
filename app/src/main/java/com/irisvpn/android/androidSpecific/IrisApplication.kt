package com.irisvpn.android.androidSpecific

import android.app.Application
import com.irisvpn.android.di.checkSecurity
import com.irisvpn.android.di.initiateAnalytics
import com.irisvpn.android.di.startDi
import com.irisvpn.android.domain.platform.AdManager
import org.koin.android.ext.android.inject

class IrisApplication : Application() {

    private val adManager: AdManager by inject()

    override fun onCreate() {
        super.onCreate()
        checkSecurity()
        startDi(this)
        initiateAnalytics()
        adManager.initialize()
    }
}