package ctrl.vanya.movietmdb.core.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class ReviewResultMdl (@field:SerializedName("loading") @field:Expose var loading: String = "",
    @SerializedName("author")
    @Expose
    var author: String? = null,

    @SerializedName("content")
    @Expose
    var content: String? = null,

    @SerializedName("id")
    @Expose
    var id: String? = null,

    @SerializedName("url")
    @Expose
    var url: String? = null

)