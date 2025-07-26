package com.irisvpn.android.domain.platform

import com.irisvpn.android.androidSpecific.AdState

interface AdManager {
    fun initialize()
    suspend fun show(): AdState
}