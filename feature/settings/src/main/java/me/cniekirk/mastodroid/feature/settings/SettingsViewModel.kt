package me.cniekirk.mastodroid.feature.settings

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import me.cniekirk.mastodroid.core.model.Theme
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(

) : ViewModel(), ContainerHost<SettingsState, SettingsEffect> {

    override val container = container<SettingsState, SettingsEffect>(SettingsState())

    fun onThemeSelected(theme: Theme) = intent {
        reduce { state.copy(theme = theme) }
    }
}