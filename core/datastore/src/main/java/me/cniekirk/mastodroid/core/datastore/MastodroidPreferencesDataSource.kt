package me.cniekirk.mastodroid.core.datastore

import androidx.datastore.core.DataStore
import javax.inject.Inject

class MastodroidPreferencesDataSource @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {


}