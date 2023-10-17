package me.cniekirk.mastodroid.core.data.repository

import me.cniekirk.mastodroid.core.common.util.Result
import me.cniekirk.mastodroid.core.model.UserStatusAndContext

interface StatusRepository {

    suspend fun getStatusAndContext(id: String): Result<UserStatusAndContext>
}