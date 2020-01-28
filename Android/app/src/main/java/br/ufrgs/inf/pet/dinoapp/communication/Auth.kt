package br.ufrgs.inf.pet.dinoapp.communication

import br.ufrgs.inf.pet.dinoapp.model.auth.AuthRequestModel
import br.ufrgs.inf.pet.dinoapp.model.auth.AuthResponseModel
import br.ufrgs.inf.pet.dinoapp.model.auth.UsernameModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * Controla a autenticação
 *
 * @author joao.silva
 */
interface Auth {

    /**
     * Requisita o token de acesso com o token de autenticação
     *
     * @param authModel Model contendo o token de autenticação
     * @return AuthResponseModel com o token de acesso
     * @author joao.silva
     */
    @POST("auth/google/")
    fun getAccessToken(@Body authModel: AuthRequestModel): Call<AuthResponseModel>

    /**
     * Busca pelo nome do usuário
     *
     * @return UsernameModel com o nome do usuário logado
     * @author joao.silva
     */
    @GET("auth/name/")
    fun name(): Call<UsernameModel>
}