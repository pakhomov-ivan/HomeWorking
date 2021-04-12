package ru.navifromnorth.homeworking.settings.proxy

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.*
import ru.navifromnorth.homeworking.repository.api.RetrofitMoviesCreator

class ProxySettingsViewModel(application: Application) : AndroidViewModel(application) {

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _proxyMessage = MutableLiveData<String?>(null)
    val proxyMessage: LiveData<String?> = _proxyMessage

    private val sharedPreferences: SharedPreferences = application
        .getSharedPreferences(RetrofitMoviesCreator.NETWORK_SETTINGS, Context.MODE_PRIVATE)

    fun getSelectedProxyType() =
        sharedPreferences.getString(RetrofitMoviesCreator.PROXY_TYPE, "DIRECT")

    fun getProxyTypes() = RetrofitMoviesCreator.PROXY_TYPES.map { it.toString() }
    fun getProxyHost() = sharedPreferences.getString(RetrofitMoviesCreator.PROXY_HOST, "")
    fun getProxyPort() = sharedPreferences.getInt(RetrofitMoviesCreator.PROXY_PORT, -1)

    fun setProxy(type: String, host: String, port: Int) {
        _isLoading.value = true
        _proxyMessage.value = null
        try {
            RetrofitMoviesCreator.setProxy(type, host, port)
            sharedPreferences.edit().apply {
                putString(RetrofitMoviesCreator.PROXY_TYPE, type)
                putString(RetrofitMoviesCreator.PROXY_HOST, host)
                putInt(RetrofitMoviesCreator.PROXY_PORT, port)
            }.apply()
        } catch (exception: Exception) {
            Log.e(this.javaClass.simpleName, exception.toString())
            //todo: добавить в ресурсы
            _proxyMessage.value = "Хост недоступен"
        } finally {
            _isLoading.value = false
        }
    }
}