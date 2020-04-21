package ctrl.vanya.movietmdb.core.model

class ErrorMdl(
    val status: Boolean = false,
    val http_code: Int? = null,
    val message: String? = null
)