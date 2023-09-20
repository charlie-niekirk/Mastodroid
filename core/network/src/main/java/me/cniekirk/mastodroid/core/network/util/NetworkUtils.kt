package me.cniekirk.mastodroid.core.network.util

import me.cniekirk.mastodroid.core.common.util.NetworkError
import me.cniekirk.mastodroid.core.common.util.RemoteServiceError
import me.cniekirk.mastodroid.core.common.util.Result
import me.cniekirk.mastodroid.core.common.util.UnexpectedError
import retrofit2.Response
import timber.log.Timber

suspend fun <T : Any> safeApiCall(call: suspend () -> Response<T>): Result<T> {
    return try {
        val response = call.invoke()
        if (response.isSuccessful) {
            Result.Success(response.body()!!)
        } else {
            if (response.code() in 400..499) {
                Result.Failure(RemoteServiceError())
            } else if (response.code() in 500..599) {
                Result.Failure(RemoteServiceError())
            } else {
                Result.Failure(UnexpectedError())
            }
        }
    } catch (error: Throwable) {
        Timber.e(error)
        Result.Failure(NetworkError())
    }
}