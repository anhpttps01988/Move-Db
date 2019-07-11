package anh.ptt.hometest.activities.main.viewmodel

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import anh.ptt.hometest.common.BaseViewModel
import anh.ptt.hometest.data.response.MoviePopularResponse
import anh.ptt.hometest.data.source.DataRepository
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(var dataRepository: DataRepository) : BaseViewModel(), LifecycleObserver {

    private val mDisposeBag = CompositeDisposable()
    val resultResponseSuccess = MutableLiveData<MoviePopularResponse>()
    val resultResponseError = MutableLiveData<String>()

    fun getMoviePopularList(page: Int) {
        mDisposeBag.add(dataRepository.getMoviePopularList(page).flatMap { response ->
            if (response.isSuccessful) {
                val body: MoviePopularResponse = response.body()!!
                Observable.just(body)
            } else {
                Observable.error(Throwable(response.errorBody().toString()))
            }
        }.subscribe(::onMovieResponseSuccess, ::onMovieResponseError))
    }

    private fun onMovieResponseSuccess(response: MoviePopularResponse) {
        resultResponseSuccess.value = response
    }

    private fun onMovieResponseError(throwable: Throwable) {
        resultResponseError.value = throwable.message
    }

    override fun onCleared() {
        mDisposeBag.clear()
        super.onCleared()
    }


}