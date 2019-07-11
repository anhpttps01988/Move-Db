package anh.ptt.hometest.data.source.remote

import anh.ptt.hometest.data.response.MoviePopularResponse
import anh.ptt.hometest.data.service.ServiceAPI
import anh.ptt.hometest.data.source.DataSource
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(retrofit: Retrofit) : DataSource {

    private val mServiceAPI = retrofit.create(ServiceAPI::class.java)

    override fun getMoviePopularList(page: Int): Observable<Response<MoviePopularResponse>> {
        return mServiceAPI.moviePopular(page = page)
    }
}