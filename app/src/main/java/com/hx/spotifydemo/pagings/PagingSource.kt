package com.hx.spotifydemo.pagings

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.hx.spotifydemo.http.Services
import com.hx.spotifydemo.viewModels.Item
import com.hx.spotifydemo.viewModels.RepositoryResp
import java.lang.Exception

class PagingSource(
    private val services: Services,
    private val q: String
) : PagingSource<Int, Item>() {
    override fun getRefreshKey(state: PagingState<Int, Item>): Int? {
        return state.anchorPosition?.let {
            val anchorPage = state.closestPageToPosition(it)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Item> {
        return try {
            val nextKey = params.key ?: 1
            val resp = services.searchRepository(q, nextKey, 30)
            LoadResult.Page(
                resp.items,
                if (nextKey == 1) null else nextKey - 1,
                if (resp.items.isNotEmpty()) nextKey + 1 else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}