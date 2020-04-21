package ctrl.vanya.movietmdb.data.source.remote

import android.annotation.SuppressLint
import android.os.Build
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import ctrl.vanya.movietmdb.data.utils.AuthInterceptor
import ctrl.vanya.movietmdb.data.utils.TLSSocketFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.HttpsURLConnection

object ApiClient {
    @SuppressLint("ObsoleteSdkInt")
    fun retrofitClient(
        url: String,
        authInterceptor: AuthInterceptor? = null): Retrofit {

        val okHttpBuilder = OkHttpClient.Builder()

        if (authInterceptor != null) {
            okHttpBuilder.addInterceptor(authInterceptor)
        }

        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val hostnameVerifier = HostnameVerifier { hostname, session ->
            val hv = HttpsURLConnection.getDefaultHostnameVerifier()
            hv.verify(hostname, session)
        }

        okHttpBuilder.hostnameVerifier(hostnameVerifier)

        okHttpBuilder.addInterceptor(createLoggingInterceptor())
            .pingInterval(30, TimeUnit.SECONDS)
            .readTimeout(1, TimeUnit.MINUTES)
            .connectTimeout(1, TimeUnit.MINUTES)


        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN &&
            Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT_WATCH) {
            try {
                okHttpBuilder.sslSocketFactory(TLSSocketFactory())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        val okHttpClient = okHttpBuilder
            .addInterceptor(logging)
            .addInterceptor(AuthInterceptor())
            .build()

        val gson = GsonBuilder()
            .setLenient()
            .create()

        return Retrofit.Builder()
            .baseUrl(url)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
    }

    fun oauthRetrofitClient(
        url: String,
        authInterceptor: AuthInterceptor? = null): Retrofit {
        val okHttpBuilder = OkHttpClient.Builder()

        if (authInterceptor != null) {
            okHttpBuilder.addInterceptor(authInterceptor)
        }

        okHttpBuilder.addInterceptor(createLoggingInterceptor())
            .pingInterval(30, TimeUnit.SECONDS)
            .readTimeout(1, TimeUnit.MINUTES)
            .connectTimeout(1, TimeUnit.MINUTES)

        val okHttpClient = okHttpBuilder.build()

        return Retrofit.Builder()
            .baseUrl(url)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    private fun createLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }
}