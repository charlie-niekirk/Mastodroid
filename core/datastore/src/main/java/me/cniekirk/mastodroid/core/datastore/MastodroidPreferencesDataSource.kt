package me.cniekirk.mastodroid.core.datastore

import androidx.datastore.core.DataStore
import me.cniekirk.mastodroid.datastore.Preferences
import javax.inject.Inject

class MastodroidPreferencesDataSource @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {


}