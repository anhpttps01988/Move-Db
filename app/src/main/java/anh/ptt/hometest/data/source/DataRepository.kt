package anh.ptt.hometest.data.source

import anh.ptt.hometest.common.dagger.scopes.Remote
import anh.ptt.hometest.data.response.MoviePopularResponse
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataRepository @Inject constructor(@Remote private var remoteDataSource: DataSource) : DataSource {

    override fun getMoviePopularList(page: Int): Observable<Response<MoviePopularResponse>> {
        return remoteDataSource.getMoviePopularList(page).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
    }

}