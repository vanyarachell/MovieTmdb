package ctrl.vanya.movietmdb.core.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class MovieListMdl {
    @SerializedName("page")
    @Expose
    var page: Int? = null

    @SerializedName("results")
    @Expose
    var results: List<ResultMdl>? = null

    @SerializedName("total_results")
    @Expose
    var totalResults: Int? = null

    @SerializedName("total_pages")
    @Expose
    var totalPages: Int? = null
}