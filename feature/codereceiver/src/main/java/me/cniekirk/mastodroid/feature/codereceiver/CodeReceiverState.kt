package me.cniekirk.mastodroid.feature.codereceiver

import androidx.annotation.StringRes

data class CodeReceiverState(
    @StringRes val loadingMessage: Int = R.string.logging_you_in
)

sealed class CodeReceiverEffect {

    data class Error(@StringRes val errorMessage: Int) : CodeReceiverEffect()

    data object Success : CodeReceiverEffect()
}