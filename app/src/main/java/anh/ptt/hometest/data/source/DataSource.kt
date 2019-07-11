package anh.ptt.hometest.data.source

import anh.ptt.hometest.data.response.MoviePopularResponse
import io.reactivex.Observable
import retrofit2.Response

interface DataSource {

    fun getMoviePopularList(page: Int) : Observable<Response<MoviePopularResponse>>

}