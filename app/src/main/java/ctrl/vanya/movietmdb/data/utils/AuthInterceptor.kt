package ctrl.vanya.movietmdb.data.utils

import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        val url: HttpUrl = request.url().newBuilder()
            .addQueryParameter("api_key", "9a1a4d8d07b89f0c57458dbaf6d58a99")
            .build()

        request = request.newBuilder().url(url).build()

        return chain.proceed(request)
    }

}