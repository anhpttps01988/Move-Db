package anh.ptt.hometest.activities.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import anh.ptt.hometest.R
import anh.ptt.hometest.data.response.MoviePopularResponse
import anh.ptt.hometest.databinding.ItemMoviePopularBinding
import anh.ptt.hometest.utils.Constants
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import android.view.animation.AnimationUtils.loadAnimation
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.DiffUtil


@Suppress("UNREACHABLE_CODE")
class MoviePopularAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val MOVIE_ITEM_TYPE = 1
        const val LOAD_MORE_TYPE = 2
    }

    private var lastPosition = -1
    private val items: MutableList<MoviePopularResponse.Result> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            MOVIE_ITEM_TYPE -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie_popular, parent, false)
                return MoviePopularViewHolder(view)
            }
            LOAD_MORE_TYPE -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_load_more, parent, false)
                return LoadMoreViewHolder(view)
            }
            else -> {
                null!!
            }
        }
    }

    fun swapData(datas: MutableList<MoviePopularResponse.Result>) {
        val diffCallback = MoviePopularDiffUtil(items, datas)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.items.addAll(datas)
        diffResult.dispatchUpdatesTo(this)
    }

    fun removeLoadMoreView() {
        if (items.isNotEmpty()) {
            items.removeAt(items.size - 1)
            notifyDataSetChanged()
        }
    }

    fun clearData() {
        lastPosition = -1
        items.clear()
        notifyDataSetChanged()
    }

    fun addLoadMoreView() {
        items.add(items.size, MoviePopularResponse.Result())
        notifyItemInserted(items.size)
    }

    fun isPositionFooter(position: Int): Boolean {
        return items[position].id == null
    }

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int {
        return if (items.isNotEmpty() && items[position].id != null) MOVIE_ITEM_TYPE else LOAD_MORE_TYPE
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            MOVIE_ITEM_TYPE -> {
                (holder as MoviePopularViewHolder).bind(items[position])
            }
        }
    }

    inner class MoviePopularViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val mBinding: ItemMoviePopularBinding = DataBindingUtil.bind(itemView)!!

        fun bind(item: MoviePopularResponse.Result) {
            Glide.with(mBinding.root.context).load(Constants.Net.BASE_URL_IMAGE + item.posterPath).centerCrop()
                .onlyRetrieveFromCache(true).placeholder(R.drawable.ic_no_image).into(mBinding.ivImageMovie)
            mBinding.tvVoteRate.text = "${item.voteAverage!!}"
            if (adapterPosition > lastPosition) {
                val animation = loadAnimation(mBinding.root.context, R.anim.item_animation)
                mBinding.root.startAnimation(animation)
                lastPosition = adapterPosition
            }
            mBinding.executePendingBindings()
        }
    }

    inner class LoadMoreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}