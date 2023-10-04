package me.cniekirk.mastodroid.feature.settings

enum class Theme {
    SYSTEM,
    DARK,
    LIGHT
}

data class SettingsState(
    val theme: Theme = Theme.SYSTEM
)

sealed class SettingsEffect {

    data object Logout : SettingsEffect()

    data object CacheCleared : SettingsEffect()
}