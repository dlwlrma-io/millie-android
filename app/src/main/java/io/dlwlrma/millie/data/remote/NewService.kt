package io.dlwlrma.millie.data.remote

import com.skydoves.sandwich.ApiResponse
import io.dlwlrma.millie.data.remote.model.HeadlineResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewService {

    @GET("top-headlines")
    suspend fun getHeadlines(
        @Query("country") country: String = "kr",
        @Query("apiKey") apiKey: String
    ): ApiResponse<HeadlineResponse>
}
