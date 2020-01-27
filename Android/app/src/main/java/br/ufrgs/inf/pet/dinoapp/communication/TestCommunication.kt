package br.ufrgs.inf.pet.dinoapp.communication

import br.ufrgs.inf.pet.dinoapp.model.TestCommunicationModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

/**
 * Testa a comunicação com a API
 * Created by joao.silva.
 */
interface TestCommunication {

    @GET("test_connection/")
    fun testGet(@Query("entry") entry: String?): Call<TestCommunicationModel>

    @POST("test_connection/")
    fun testPost(@Query("entry") entry: String?): Call<TestCommunicationModel>

    @PUT("test_connection/")
    fun testPut(@Query("entry") entry: String?): Call<TestCommunicationModel>

}