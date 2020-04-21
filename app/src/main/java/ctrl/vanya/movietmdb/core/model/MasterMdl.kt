package ctrl.vanya.movietmdb.core.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class MasterMdl {
    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("logo_path")
    @Expose
    var logoPath: String? = null

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("origin_country")
    @Expose
    var originCountry: String? = null

    @SerializedName("iso_3166_1")
    @Expose
    var iso31661: String? = null

    @SerializedName("iso_639_1")
    @Expose
    var iso6391: String? = null
}