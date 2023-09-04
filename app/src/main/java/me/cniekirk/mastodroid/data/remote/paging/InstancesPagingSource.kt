package me.cniekirk.mastodroid.data.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import me.cniekirk.mastodroid.data.model.response.Instance
import me.cniekirk.network.service.InstancesService
import timber.log.Timber
import javax.inject.Inject

class InstancesPagingSource @Inject constructor(
    private val instancesService: me.cniekirk.network.service.InstancesService
) : PagingSource<String, Instance>() {

    private var previous: String? = null
    private var itemCount: Int = 0

    override suspend fun load(params: LoadParams<String>): LoadResult<String, Instance> {
        return try {
            val response = instancesService.getInstances(
                authorization = "Bearer $TOKEN",
                minId = params.key
            )

            if (itemCount > 0 && params.key.equals(previous, true)) {
                itemCount -= response.body()?.instances?.size ?: 0
            } else {
                itemCount += response.body()?.instances?.size ?: 0
            }

            val page = LoadResult.Page(
                response.body()?.instances?.filterNotNull() ?: listOf(),
                previous,
                response.body()?.pagination?.nextId
            )

            previous = response.body()?.pagination?.nextId
            page
        } catch (exception: Exception) {
            Timber.e(exception)
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<String, Instance>): String? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestItemToPosition(anchorPosition)?.id
        }
    }

    companion object {
        const val TOKEN = "kTr2ps4J5LLrKvbYBnxOPaMjti3SCQTzlaCCuWcS2O6JoehCdeUxfwwGdpjrrQbHmWVighDfVl3tTf1oTrNGMiPX3p5sHmItE3tE9SX813kxFLmZE6D2BqKvWjnxet5K"
    }
}