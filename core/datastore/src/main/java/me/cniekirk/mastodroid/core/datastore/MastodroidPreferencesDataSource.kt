package me.cniekirk.mastodroid.core.datastore

import kotlinx.coroutines.flow.Flow
import me.cniekirk.mastodroid.core.model.UserData
import me.cniekirk.mastodroid.datastore.Preferences

interface MastodroidPreferencesDataSource {

    val userData: Flow<UserData>

    suspend fun updateSelectedServerUid(selectedServerUid: Long)
}