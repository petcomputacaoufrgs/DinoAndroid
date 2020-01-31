package br.ufrgs.inf.pet.dinoapp.communication

import br.ufrgs.inf.pet.dinoapp.model.TestCommunicationModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

/**
 * Testa a comunicação com a API
 * Created by joao.silva
 */
interface TestCommunication {

    /**
     * Teste de conexão via GET
     *
     * @param entry texto para teste
     * @return TestCommunicationModel com o texto enviado como teste
     * @author joao.silva
     */
    @GET("test_connection/")
    fun testGet(@Query("entry") entry: String?): Call<TestCommunicationModel>

    /**
     * Teste de conexão via POST
     *
     * @param entry texto para teste
     * @return TestCommunicationModel com o texto enviado como teste
     * @author joao.silva
     */
    @POST("test_connection/")
    fun testPost(@Query("entry") entry: String?): Call<TestCommunicationModel>

    /**
     * Teste de conexão via PUT
     *
     * @param entry texto para teste
     * @return TestCommunicationModel com o texto enviado como teste
     * @author joao.silva
     */
    @PUT("test_connection/")
    fun testPut(@Query("entry") entry: String?): Call<TestCommunicationModel>

}