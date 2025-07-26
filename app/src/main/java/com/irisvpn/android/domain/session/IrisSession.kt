package com.irisvpn.android.domain.session

import kotlinx.serialization.Serializable


@Serializable
data class IrisSession(
    val config: String
)