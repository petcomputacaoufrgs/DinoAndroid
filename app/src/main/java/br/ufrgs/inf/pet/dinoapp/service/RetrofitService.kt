package br.ufrgs.inf.pet.dinoapp.service

import android.app.Activity
import br.ufrgs.inf.pet.dinoapp.database.user.UserController
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Fábrica de conexão do Retrofit
 * @author joao.silva
 */
class RetrofitService constructor(private val activity : Activity){

    private var baseURL = "https://7f846c8e.ngrok.io"
    private val userController = UserController(activity)


    /**
     * Gera o OkHttpClient com token de autenticação para comunicação com a API.
     * Caso o token esteja expirado trata o recebimento de um novo.
     *
     * @return OkHttpClient para conexão
     * @author joao.silva
     */
    fun getAuthHttpClient() : OkHttpClient {
        val userController = UserController(activity)
        val loginUser = userController.get()

        if (loginUser != null && loginUser.token != "") {
            return OkHttpClient.Builder()
                .addInterceptor { chain ->
                    val original = chain.request()

                    val requestBuilder = original.newBuilder().addHeader("Authorization", "Bearer " + loginUser.token)
                        .method(original.method(), original.body())

                    val request = requestBuilder.build()

                    val response = chain.proceed(request)

                    val header = response.header("Refresh")

                    if (header != null) {
                        val newToken = header.substring(7)

                        loginUser.token = newToken

                        userController.insert(loginUser)
                    }

                    response
                }.build()
        }
        return getHttpClient()
    }

    /**
     * Gera o OkHttpClient sem token de autenticação para comunicação com a API.
     *
     * @return OkHttpClient para conexão
     * @author joao.silva
     */
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

    /**
     * Gera o conversor de JSON
     *
     * @return instância do Gson configurada
     * @author joao.silva
     */
    private fun getGsonFactory() : Gson {
        return GsonBuilder()
            .setLenient()
            .create()
    }

    /**
     * Retorna a comunicação Retrofit com ou sem autenticação conforme dados salvos no banco interno
     *
     * @return instância do Retrofit configurada
     * @author joao.silva
     */
    fun getRetrofitFactory() : Retrofit{
        val loginUser = userController.get()

        return if (loginUser != null && loginUser.token != "") {
            getRetrofitFactoryWithAuth()
        } else {
            getRetrofitFactoryWithoutAuth()
        }
    }

    /**
     * Retorna a comunicação Retrofit sem autenticação
     *
     * @return instância do Retrofit configurada
     * @author joao.silva
     */
    private fun getRetrofitFactoryWithoutAuth() : Retrofit{
        return Retrofit.Builder()
            .baseUrl(this.baseURL)
            .addConverterFactory(GsonConverterFactory.create(this.getGsonFactory()))
            .client(this.getHttpClient())
            .build()
    }

    /**
     * Retorna a comunicação Retrofit com autenticação
     *
     * @return instância do Retrofit configurada
     * @author joao.silva
     */
    private fun getRetrofitFactoryWithAuth() : Retrofit {
        return Retrofit.Builder()
            .baseUrl(this.baseURL)
            .addConverterFactory(GsonConverterFactory.create(this.getGsonFactory()))
            .client(this.getAuthHttpClient())
            .build()
    }

}