package com.irisvpn.android.di.modules

import com.irisvpn.android.androidSpecific.AndroidInstalledAppRepository
import com.irisvpn.android.androidSpecific.AndroidSharedPreferences
import com.irisvpn.android.domain.platform.AppPackagesRepository
import com.irisvpn.android.domain.platform.Preference
import com.irisvpn.android.screens.excludeApp.ExcludeAppViewModel
import com.irisvpn.android.screens.main.MainViewModel
import com.irisvpn.android.screens.setting.SettingViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {
    viewModel {
        MainViewModel(get())
    }
}

val settingModule = module {
    single<Preference> {
        AndroidSharedPreferences(androidContext())
    }
    single<AppPackagesRepository> {
        AndroidInstalledAppRepository(androidContext())
    }
    viewModel {
        SettingViewModel(get(), get())
    }
}

val excludeModule = module {
    single<AppPackagesRepository> {
        AndroidInstalledAppRepository(androidContext())
    }
    viewModel<ExcludeAppViewModel> {
        ExcludeAppViewModel(get(), get())
    }
}