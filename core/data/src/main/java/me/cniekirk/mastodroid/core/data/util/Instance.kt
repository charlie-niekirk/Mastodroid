package me.cniekirk.mastodroid.core.data.util

import me.cniekirk.mastodroid.core.database.model.InstanceEntity
import me.cniekirk.mastodroid.core.model.MastodonInstance
import me.cniekirk.mastodroid.core.network.model.Instance

fun Instance?.toMastodonInstance(): MastodonInstance {
    return MastodonInstance(
        this?.name ?: "",
        this?.activeUsers ?: 0,
        this?.info?.shortDescription ?: "",
        this?.thumbnail ?: ""
    )
}

fun Instance?.toInstanceEntity(): InstanceEntity {
    return InstanceEntity(
        name = this?.name ?: "",
        users = this?.activeUsers ?: 0,
        description = this?.info?.shortDescription ?: "",
        thumbnail = this?.thumbnail ?: ""
    )
}

fun InstanceEntity.toMastodonInstance(): MastodonInstance {
    return MastodonInstance(
        name ?: "",
        users ?: 0,
        description ?: "",
        thumbnail ?: ""
    )
}