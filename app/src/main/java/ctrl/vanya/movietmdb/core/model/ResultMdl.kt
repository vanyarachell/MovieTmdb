package ctrl.vanya.movietmdb.core.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class ResultMdl (@field:SerializedName("loading") @field:Expose var loading: String = "",
    @SerializedName("poster_path")
    @Expose
    var posterPath: String? = null,

    @SerializedName("adult")
    @Expose
    var adult: Boolean? = null,

    @SerializedName("overview")
    @Expose
    var overview: String? = null,

    @SerializedName("release_date")
    @Expose
    var releaseDate: String? = null,

    @SerializedName("genre_ids")
    @Expose
    var genreIds: List<Int>? = null,

    @SerializedName("id")
    @Expose
    var id: Int? = null,

    @SerializedName("original_title")
    @Expose
    var originalTitle: String? = null,

    @SerializedName("original_language")
    @Expose
    var originalLanguage: String? = null,

    @SerializedName("title")
    @Expose
    var title: String? = null,

    @SerializedName("backdrop_path")
    @Expose
    var backdropPath: Any? = null,

    @SerializedName("popularity")
    @Expose
    var popularity: Float? = null,

    @SerializedName("vote_count")
    @Expose
    var voteCount: Int? = null,

    @SerializedName("video")
    @Expose
    var video: Boolean? = null,

    @SerializedName("vote_average")
    @Expose
    var voteAverage: Float? = null
)