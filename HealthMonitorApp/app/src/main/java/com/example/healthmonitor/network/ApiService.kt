package shubhamji.com.newspaper.network

import com.example.healthmonitor.network.Response.ResponseData
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

private val BASE_URL="https://api.thingspeak.com"
private const val ApiKey="G471ANJA8F7RM35A"
private val okHttpLogger=HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
private val okHttp=OkHttpClient.Builder().addInterceptor(okHttpLogger).build()

private val retrofit=Retrofit.Builder()
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .client(okHttp)
    .build()
interface ApiService{
    @GET("/channels/1354140/feeds.json?api_key==$ApiKey&results=1")
    fun getData(): Deferred<ResponseData>
}
object Api{
    val retrifitService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}
