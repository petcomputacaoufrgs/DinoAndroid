package br.ufrgs.inf.pet.dinoapp.service


import android.app.Activity
import android.widget.Toast
import retrofit2.Response


/**
 * Administra dados da API
 * @author joao.silva
 */
class APIService constructor(private val activity : Activity) {
    private val authService = AuthService(activity)

    /**
     * Lida com erros vindos da API
     *
     * @author joao.silva
     */
    fun <T> handlerAPIError(response : Response<T>){
        if (response.code() == 403) {
            Toast.makeText(activity, "Falha na autenticação com a API.", Toast.LENGTH_SHORT).show()
            authService.goToLogin()
        } else {
            Toast.makeText(activity, "Falha na API.", Toast.LENGTH_SHORT).show()
        }
    }
}