package anh.ptt.hometest.data.service

import anh.ptt.hometest.data.response.MoviePopularResponse
import anh.ptt.hometest.utils.Constants
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ServiceAPI {

    @GET("/3/movie/popular")
    fun moviePopular(@Query("api_key") apiKey: String = Constants.API_MOVIE_DB,
                     @Query("language") language: String = "en-US",
                     @Query("page") page: Int) : Observable<Response<MoviePopularResponse>>

}