package com.irisvpn.android.domain.session

import com.irisvpn.android.domain.baseUrl.BaseUrlRepository
import com.irisvpn.android.domain.platform.DeviceIdRepository
import com.irisvpn.android.domain.server.ErrorMessageRepository
import com.irisvpn.android.domain.server.NetworkCapability
import com.irisvpn.android.domain.server.Response
import com.irisvpn.android.domain.server.isSuccess
import com.irisvpn.android.utils.Either
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class OnDemandSessionRepositoryImpl(
    private val networkUtils: NetworkCapability,
    private val httpClient: HttpClient,
    private val baseUrlRepository: BaseUrlRepository,
    private val messageRepository: ErrorMessageRepository,
    private val deviceIdRepository: DeviceIdRepository
) : SessionRepository {

    override suspend fun getSessionFromServer(serverId: String): Either<Error, IrisSession> {
        if (networkUtils.isNetworkConnected()
                .not()
        ) return Either.Left(Error(messageRepository.networkError()))
        val generateConfigRoute = baseUrlRepository.getBaseUrlWithBasePing() + "generate-config"
        return try {
            val response = httpClient.post(generateConfigRoute) {
                contentType(ContentType.Application.Json)
                setBody(
                    GenerateConfigRequest(
                        serverId = serverId.toInt(),
                        clientId = deviceIdRepository.generateUniqueId()
                    )
                )
            }.body<Response<IrisSession>>()
            if (response.isSuccess()) {
                Either.Right(response.data!!)
            } else {
                Either.Left(Error(messageRepository.serverError()))
            }
        } catch (e: Exception) {
            Either.Left(Error(messageRepository.unExpectedError()))
        }
    }
}
