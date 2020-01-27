package br.ufrgs.inf.pet.dinoapp.service

import android.app.Activity
import br.ufrgs.inf.pet.dinoapp.database.UserController
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Manager of Retrofit Factory
 * Created by joao.silva.
 */
class RetrofitService constructor(private val activity : Activity){

    private var baseURL = "https://fcb891a8.ngrok.io/"
    private val userController = UserController(activity)
    private val authService = AuthService(activity)

    fun getAuthHttpClient() : OkHttpClient {
        val userController = UserController(activity)
        var loginUser = userController.getUser()

        if (loginUser != null && loginUser.token != "") {
            return OkHttpClient.Builder()
                .addInterceptor { chain ->
                    val original = chain.request()

                    val requestBuilder = original.newBuilder().addHeader("Authorization", "Bearer " + loginUser!!.token)
                        .method(original.method(), original.body())

                    val request = requestBuilder.build()

                    val response = chain.proceed(request)

                    val header = response.header("Refresh")

                    if (header != null) {
                        val newToken = header.substring(7)

                        val loginUser = userController.getUser()

                        if (loginUser != null) {
                            loginUser.token = newToken

                            userController.insertUser(loginUser)
                        }
                    }

                    response
                }.build()
        }
        return getHttpClient()
    }

    private fun getHttpClient() : OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val original = chain.request()

                val requestBuilder = original.newBuilder()
                    .method(original.method(), original.body())

                val request = requestBuilder.build()

                chain.proceed(request)
            }.build()
    }

    private fun getGsonFactory() : Gson {
        return GsonBuilder()
            .setLenient()
            .create()
    }

    fun getRetrofitFactory() : Retrofit{
        val loginUser = userController.getUser()

        return if (loginUser != null && loginUser.token != "") {
            getRetrofitFactoryWithAuth()
        } else {
            getRetrofitFactoryWithoutAuth()
        }
    }

    fun getRetrofitFactoryWithoutAuth() : Retrofit{
        return Retrofit.Builder()
            .baseUrl(this.baseURL)
            .addConverterFactory(GsonConverterFactory.create(this.getGsonFactory()))
            .client(this.getHttpClient())
            .build()
    }

    private fun getRetrofitFactoryWithAuth() : Retrofit {
        return Retrofit.Builder()
            .baseUrl(this.baseURL)
            .addConverterFactory(GsonConverterFactory.create(this.getGsonFactory()))
            .client(this.getAuthHttpClient())
            .build()
    }

}