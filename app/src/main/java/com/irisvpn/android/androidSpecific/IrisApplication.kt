package com.irisvpn.android.androidSpecific

import android.app.Application
import com.irisvpn.android.di.startDi

class IrisApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startDi(this)
    }
}