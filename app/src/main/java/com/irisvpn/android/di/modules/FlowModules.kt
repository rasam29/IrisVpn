package com.irisvpn.android.di.modules

import com.irisvpn.android.androidSpecific.AndroidInstalledAppRepository
import com.irisvpn.android.androidSpecific.AndroidSharedPreferences
import com.irisvpn.android.domain.ExcludeAppViewModel
import com.irisvpn.android.domain.MainViewModel
import com.irisvpn.android.domain.SettingViewModel
import com.irisvpn.android.utils.AppPackagesRepository
import com.irisvpn.android.utils.Preference
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {
    viewModel {
        MainViewModel()
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
        SettingViewModel(get())
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