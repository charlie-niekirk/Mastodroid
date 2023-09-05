package me.cniekirk.mastodroid.core.common.util

sealed interface Result<out T> {

    data class Success<out T>(val data: T) : Result<T>
    data class Failure(val error: Error) : Result<Nothing>
}