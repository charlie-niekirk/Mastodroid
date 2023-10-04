package me.cniekirk.mastodroid.feature.settings

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(

) : ViewModel(), ContainerHost<SettingsState, SettingsEffect> {

    override val container = container<SettingsState, SettingsEffect>(SettingsState())
}