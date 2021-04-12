package ru.navifromnorth.homeworking.repository.api

import android.content.SharedPreferences
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.net.*

object RetrofitMoviesCreator {

    const val NETWORK_SETTINGS = "NetworkSettings"
    const val PROXY_TYPE = "ProxyType"
    const val PROXY_HOST = "ProxyHost"
    const val PROXY_PORT = "ProxyPort"
    val PROXY_TYPES = Proxy.Type.values()

    private const val BASE_URL = "https://api.themoviedb.org/3/"
    private val okHttpClient = OkHttpClient.Builder()

    @Volatile
    private var retrofit: Retrofit? = null

    fun setProxy(proxyType: String?, host: String?, port: Int) {

        val proxy = if (proxyType != "DIRECT")
            Proxy(
                Proxy.Type.valueOf(proxyType!!),
                InetSocketAddress(host, port)
            )
        else
            Proxy.NO_PROXY

        if (proxy != Proxy.NO_PROXY && (proxy.address() as InetSocketAddress).isUnresolved)
            throw IllegalArgumentException("Host is unresolvable!")

        //todo: Добавить попытку соединиться с BASE_URL
//        URL(BASE_URL).openConnection(proxy).connect()

        okHttpClient.proxy(proxy)
        rebuildRetrofit()
    }

    fun setSettings(sharedPreferences: SharedPreferences): RetrofitMoviesCreator {
        if (sharedPreferences.all.keys.containsAll(listOf(PROXY_TYPE, PROXY_HOST, PROXY_PORT)))
            setProxy(
                proxyType = sharedPreferences.getString(PROXY_TYPE, Proxy.Type.DIRECT.toString()),
                host = sharedPreferences.getString(PROXY_HOST, ""),
                port = sharedPreferences.getInt(PROXY_PORT, 80)
            )

        return this
    }

    @Synchronized
    fun rebuildRetrofit() {
        retrofit = createRetrofit()
    }

    fun getInstance(): Retrofit {
        val ins = retrofit
        if (ins != null)
            return ins

        return synchronized(this) {
            val ins2 = retrofit
            if (ins2 != null) {
                ins2
            } else {
                val created = createRetrofit()
                retrofit = created
                created
            }
        }
    }

    private fun createRetrofit() = Retrofit.Builder()
        .client(okHttpClient.build())
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

}