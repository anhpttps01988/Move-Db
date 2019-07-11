package anh.ptt.hometest.activities.main.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import anh.ptt.hometest.R
import anh.ptt.hometest.activities.main.viewmodel.MainActivityViewModel
import anh.ptt.hometest.common.BaseActivity
import anh.ptt.hometest.common.dagger.ViewModelFactory
import anh.ptt.hometest.data.source.DataRepository
import anh.ptt.hometest.databinding.ActivityMainBinding
import javax.inject.Inject

class MainActivity : BaseActivity() {


    @Inject
    lateinit var mViewModelFactory: ViewModelFactory
    private lateinit var mViewModel: MainActivityViewModel
    private lateinit var mBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = ViewModelProviders.of(this, mViewModelFactory).get(MainActivityViewModel::class.java)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mBinding.lifecycleOwner = this
        lifecycle.addObserver(mViewModel)
        init()
    }

    private fun init() {
        loadMoviePopular()
    }

    private fun loadMoviePopular() {
        mViewModel.getMoviePopularList(1)
    }

}
