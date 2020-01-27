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
 * Created by joao.silva.
 */
interface Auth {
    @POST("auth/google/")
    fun getAccessToken(@Body authModel: AuthRequestModel): Call<AuthResponseModel>

    @GET("auth/name/")
    fun name(): Call<UsernameModel>
}