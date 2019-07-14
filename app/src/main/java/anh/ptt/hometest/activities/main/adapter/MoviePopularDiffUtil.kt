package anh.ptt.hometest.activities.main.adapter

import androidx.recyclerview.widget.DiffUtil
import anh.ptt.hometest.data.response.MoviePopularResponse

class MoviePopularDiffUtil constructor(
    var oldMoviePopularList: MutableList<MoviePopularResponse.Result>,
    var newMoviePopularList: MutableList<MoviePopularResponse.Result>
) :
    DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldMoviePopularList[oldItemPosition].id == newMoviePopularList[newItemPosition].id
    }

    override fun getOldListSize(): Int = oldMoviePopularList.size

    override fun getNewListSize(): Int = newMoviePopularList.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldMoviePopularList[oldItemPosition].title == newMoviePopularList[newItemPosition].title
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        return super.getChangePayload(oldItemPosition, newItemPosition)
    }
}