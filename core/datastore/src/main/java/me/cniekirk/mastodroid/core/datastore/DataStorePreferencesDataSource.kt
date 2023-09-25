package me.cniekirk.mastodroid.core.datastore

import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.map
import me.cniekirk.mastodroid.core.model.UserData
import me.cniekirk.mastodroid.datastore.Preferences
import javax.inject.Inject

class DataStorePreferencesDataSource @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : MastodroidPreferencesDataSource {

    override val userData = dataStore.data.map {
        UserData(it.selectedServerUid, it.isLoggedIn)
    }

    override suspend fun updateSelectedServerUid(selectedServerUid: Long) {
        dataStore.updateData { currentPreferences ->
            currentPreferences.toBuilder().setSelectedServerUid(selectedServerUid).build()
        }
    }
}