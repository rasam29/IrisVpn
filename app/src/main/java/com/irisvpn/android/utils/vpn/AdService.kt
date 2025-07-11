package com.irisvpn.android.utils.vpn

interface AdService {
    fun showAdd(callBack:(AddState)-> Unit)
}

enum class AddState{
    Loading,
    Loaded,
    LoadFailed,
    OnDismiss,
    OnFailedToShow
}