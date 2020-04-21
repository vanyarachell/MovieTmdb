package ctrl.vanya.movietmdb.core.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class CrewMdl {
    @SerializedName("credit_id")
    @Expose
    var creditId: String? = null

    @SerializedName("department")
    @Expose
    var department: String? = null

    @SerializedName("gender")
    @Expose
    var gender: Int? = null

    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("job")
    @Expose
    var job: String? = null

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("profile_path")
    @Expose
    var profilePath: String? = null
}