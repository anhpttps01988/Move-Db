package anh.ptt.hometest.activities.main.view

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import anh.ptt.hometest.R
import anh.ptt.hometest.activities.main.adapter.MoviePopularAdapter
import anh.ptt.hometest.activities.main.viewmodel.MainActivityViewModel
import anh.ptt.hometest.common.BaseActivity
import anh.ptt.hometest.common.dagger.ViewModelFactory
import anh.ptt.hometest.databinding.ActivityMainBinding
import anh.ptt.hometest.utils.ItemDecorationAlbumColumns
import javax.inject.Inject

class MainActivity : BaseActivity() {


    @Inject
    lateinit var mViewModelFactory: ViewModelFactory

    private lateinit var mViewModel: MainActivityViewModel
    private lateinit var mBinding: ActivityMainBinding
    private lateinit var moviePopularAdapter: MoviePopularAdapter
    private var gridLayoutManager: GridLayoutManager? = null
    private var isLoading = true
    private var visibleThreshold = 6
    private var page: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = ViewModelProviders.of(this, mViewModelFactory).get(MainActivityViewModel::class.java)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        lifecycle.addObserver(mViewModel)
        mBinding.lifecycleOwner = this
        init()
    }

    private fun init() {
        setupToolbarTitle()
        initMoviePopularAdapter()
        moviePopularResponse()
        setLoadMore()
        setUpRefreshData()
    }

    private fun setupToolbarTitle() {
        supportActionBar?.title = getString(R.string.now_playing_title)
    }

    private fun initMoviePopularAdapter() {
        moviePopularAdapter = MoviePopularAdapter()
        gridLayoutManager = GridLayoutManager(this, 2, RecyclerView.VERTICAL, false)
        mBinding.recyclerView.layoutManager = gridLayoutManager
        mBinding.recyclerView.isNestedScrollingEnabled = true
        mBinding.recyclerView.setHasFixedSize(false)
        mBinding.recyclerView.addItemDecoration(ItemDecorationAlbumColumns(8, 2))
        mBinding.recyclerView.adapter = moviePopularAdapter
        gridLayoutManager?.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (moviePopularAdapter.isPositionFooter(position)) gridLayoutManager!!.spanCount else 1
            }
        }
        mBinding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val totalItemCount = gridLayoutManager?.itemCount!!
                val lastVisibleItem = gridLayoutManager?.findLastVisibleItemPosition()!!
                if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                    setLoadMore()
                    isLoading = true
                }
            }
        })
    }

    private fun setUpRefreshData() {
        mBinding.refreshView.setOnRefreshListener {
            resetLoadMore()
        }
    }

    private fun resetLoadMore() {
        moviePopularAdapter.clearData()
        page = 0
        isLoading = false
    }

    private fun setLoadMore() {
        page++
        addLoadMore()
        mViewModel.getMoviePopularList(page)
    }

    private fun removeLoadMore() {
        moviePopularAdapter.removeLoadMoreView()
    }

    private fun addLoadMore(){
        mBinding.recyclerView.post {
            moviePopularAdapter.addLoadMoreView()
        }
    }

    private fun moviePopularResponse() {
        mViewModel.resultResponseSuccess.observe(this, Observer {
            mBinding.refreshView.isRefreshing = false
            isLoading = false
            removeLoadMore()
            if (it.results!!.isNotEmpty()) {
                moviePopularAdapter.swapData(it.results.toMutableList())
            }
        })
        mViewModel.resultResponseError.observe(this, Observer {
            mBinding.refreshView.isRefreshing = false
            removeLoadMore()
        })
    }
}
