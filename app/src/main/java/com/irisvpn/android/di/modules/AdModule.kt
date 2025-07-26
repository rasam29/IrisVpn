package com.irisvpn.android.di.modules

import com.irisvpn.android.androidSpecific.GoogleAdsManager
import com.irisvpn.android.domain.platform.AdManager
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val adModule = module{
    single<AdManager>{
        GoogleAdsManager(androidContext())
    }
}