package me.cniekirk.mastodroid.core.common.util

sealed interface Result<out T> {

    data class Success<out T>(val data: T) : Result<T>
    data class Failure(val error: Error) : Result<Nothing>
}

class UnexpectedError : Error() {

    override fun toString(): String {
        return "UnexpectedError"
    }
}


/**
 * Represents an error in which the server could not be reached.
 */
class NetworkError : Error() {

    override fun toString(): String {
        return "NetworkError"
    }
}


/**
 * An error response from the server.
 */
open class RemoteServiceError : Error() {

    override fun toString(): String {
        return "RemoteServiceError(message: $message)"
    }
}