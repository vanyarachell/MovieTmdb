package ctrl.vanya.movietmdb.core.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GenreListMdl {
    @SerializedName("genres")
    @Expose
    var genres: List<MasterMdl>? = null
}