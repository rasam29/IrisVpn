package com.irisvpn.android.di.modules

import com.irisvpn.android.androidSpecific.AndroidSharedPreferences
import com.irisvpn.android.androidSpecific.ConnectCountPreferences
import com.irisvpn.android.androidSpecific.V2rayVpnService
import com.irisvpn.android.domain.analytics.AnalyticService
import com.irisvpn.android.domain.analytics.AnalyticServiceImpl
import com.irisvpn.android.domain.core.IrisUseCase
import com.irisvpn.android.domain.core.IrisUseCaseImpl
import com.irisvpn.android.domain.platform.AndroidDeviceIdRepository
import com.irisvpn.android.domain.platform.ConnectCountRepository
import com.irisvpn.android.domain.platform.DeviceIdRepository
import com.irisvpn.android.domain.platform.Preference
import com.irisvpn.android.domain.server.InMemoryServerRepository
import com.irisvpn.android.domain.server.ServerRepository
import com.irisvpn.android.domain.session.SessionRepository
import com.irisvpn.android.domain.session.OnDemandSessionRepositoryImpl
import com.irisvpn.android.domain.vpn.VpnService
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module


val useCaseModule = module{

    single<DeviceIdRepository>{
        AndroidDeviceIdRepository(androidContext())
    }
    single<SessionRepository>{
        OnDemandSessionRepositoryImpl(get(),get(),get(),get(),get())
    }
    single<Preference>{
        AndroidSharedPreferences(androidContext())
    }
    single<VpnService>{
        V2rayVpnService(androidContext(),get())
    }

    single<ServerRepository>{
        InMemoryServerRepository()
    }
    single<AnalyticService>{
        AnalyticServiceImpl()
    }
    single<IrisUseCase>{
        IrisUseCaseImpl(get(),get(),get(),get(),get(),get())
    }
    single<ConnectCountRepository>{
        ConnectCountPreferences(androidContext())
    }
}
