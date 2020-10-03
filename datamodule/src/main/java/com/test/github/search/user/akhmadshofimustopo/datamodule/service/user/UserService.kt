package com.test.github.search.user.akhmadshofimustopo.datamodule.service.user

import com.test.github.search.user.akhmadshofimustopo.datamodule.model.dto.user.UserDto
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UserService {

    @GET("search/users")
    fun getSearchListUserAsync(
        @Query("q") keyword: String?,
        @Query("per_page") perPage: Int?,
        @Query("page") page: Int?
    ): Deferred<Response<UserDto>>

}