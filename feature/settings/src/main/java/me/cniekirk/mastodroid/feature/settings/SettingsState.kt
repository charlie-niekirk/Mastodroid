package me.cniekirk.mastodroid.feature.settings

import me.cniekirk.mastodroid.core.model.Theme

data class SettingsState(
    val theme: Theme = Theme.LIGHT,
    val animatedProfileImagesDisabled: Boolean = false
)

sealed class SettingsEffect {

    data object Logout : SettingsEffect()

    data object CacheCleared : SettingsEffect()
}