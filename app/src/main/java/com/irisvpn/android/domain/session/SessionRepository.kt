package com.irisvpn.android.domain.session

import com.irisvpn.android.utils.Either
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

interface SessionRepository {
    suspend fun getSessionFromServer(serverId: String): Either<Error,IrisSession>
}

@Serializable
data class GenerateConfigRequest(
    @SerialName("server_id")
    val serverId: Int,
    @SerialName("client_id")
    val clientId: String
)