package com.hx.spotifydemo.http

import com.hx.spotifydemo.viewModels.RepositoryResp
import retrofit2.http.GET
import retrofit2.http.Query

interface Services {
    @GET("search/repositories")
    suspend fun searchRepository(
        @Query("q") q: String,
        @Query("page") page: Int,
        @Query("per_page") per_page: Int
    ): RepositoryResp
}

data class BaseResp<T>(
    val total_count: Int,

    )