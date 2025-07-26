package com.irisvpn.android.domain.analytics

interface AnalyticService {
    fun connectAttempt()
    fun adDismissed()
    fun succussfullConnect()
    fun disConnectedManually()
    fun adShowFailed()
    fun adLoaded()
    fun serverListNotLoaded()
    fun sessionConfigFetchFailed()
}