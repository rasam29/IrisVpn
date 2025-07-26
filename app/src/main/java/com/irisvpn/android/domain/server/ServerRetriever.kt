package com.irisvpn.android.domain.server

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.core.content.ContextCompat
import com.irisvpn.android.R
import com.irisvpn.android.domain.baseUrl.BaseUrlRepository
import com.irisvpn.android.utils.Either
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.serialization.Serializable

@Serializable
data class Response<out T>(
    val data: T?,
    val status: String
)

fun <T> Response<T>.isSuccess(): Boolean = status == "success" && data != null

interface NetworkCapability {
    fun isNetworkConnected(): Boolean
}

class AndroidNetworkCapability(private val context: Context) : NetworkCapability {
    override fun isNetworkConnected(): Boolean {
        val connectivityManager =
            ContextCompat.getSystemService(context, ConnectivityManager::class.java)
        if (connectivityManager == null) return false
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
}


interface ErrorMessageRepository {
    fun networkError(): String
    fun unExpectedError(): String
    fun serverError(): String
}

class AndroidMessageRepository(private val context: Context) : ErrorMessageRepository {
    override fun networkError(): String {
        return context.getString(R.string.check_your_network_connection)
    }

    override fun unExpectedError(): String {
        return context.getString(R.string.unExpected_error_happens)
    }

    override fun serverError(): String {
        return context.getString(R.string.server_error)
    }

}


interface ServerRetriever {
    suspend fun retrieve(): Either<Error,List<AvailableServer>>
}


class ServerRetrieverImpl(
    private val networkCapability: NetworkCapability,
    private val httpClient: HttpClient,
    private val baseUrlRepository: BaseUrlRepository,
    private val messageRepository: ErrorMessageRepository,
) : ServerRetriever {
    override suspend fun retrieve(): Either<Error, List<AvailableServer>> {
        val serverListRoute = baseUrlRepository.getBaseUrlWithBasePing() + "servers"
        if (!(networkCapability.isNetworkConnected())) return Either.Left(Error(messageRepository.networkError()))
        return try {
            val response = httpClient.get(serverListRoute).body<Response<List<AvailableServer>>>()
            if (response.isSuccess()) {
                Either.Right(response.data ?: emptyList())
            } else {
                Either.Left(Error(messageRepository.serverError()))
            }
        } catch (e: Exception) {
            Either.Left(Error(messageRepository.unExpectedError()))
        }
    }
}

