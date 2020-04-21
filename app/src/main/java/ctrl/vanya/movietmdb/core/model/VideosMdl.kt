package ctrl.vanya.movietmdb.core.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class VideosMdl {
    @SerializedName("results")
    @Expose
    var results: List<VideoResultMdl>? = null
}