package anh.ptt.hometest.data.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class MoviePopularResponse : Serializable {

    @SerializedName("results")
    val results: List<Result>? = null

    class Result {
        @SerializedName("id")
        var id: Int? = 0
        @SerializedName("title")
        var title: String? = null
        @SerializedName("vote_average")
        var voteAverage: Double? = null
        @SerializedName("poster_path")
        var posterPath: String? = null
    }

}