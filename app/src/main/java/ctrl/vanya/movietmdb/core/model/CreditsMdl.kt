package ctrl.vanya.movietmdb.core.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class CreditsMdl {
    @SerializedName("cast")
    @Expose
    var cast: List<CastMdl>? = null

    @SerializedName("crew")
    @Expose
    var crew: List<CrewMdl>? = null

}