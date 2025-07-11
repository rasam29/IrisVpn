package com.irisvpn.android.di

import android.content.Context
import com.irisvpn.android.di.modules.excludeModule
import com.irisvpn.android.di.modules.mainModule
import com.irisvpn.android.di.modules.settingModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

fun startDi(androidContext: Context) {
    startKoin {
        androidContext(androidContext)
        modules(mainModule, settingModule, excludeModule)
    }
}

