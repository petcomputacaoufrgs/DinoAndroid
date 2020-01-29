
package br.ufrgs.inf.pet.dinoapp.service

import android.app.Activity
import android.content.Intent
import android.widget.Toast
import br.ufrgs.inf.pet.dinoapp.activity.LoginActivity
import br.ufrgs.inf.pet.dinoapp.activity.MenuActivity
import br.ufrgs.inf.pet.dinoapp.communication.RetrofitClient
import br.ufrgs.inf.pet.dinoapp.database.user.User
import br.ufrgs.inf.pet.dinoapp.database.user.UserController
import br.ufrgs.inf.pet.dinoapp.model.auth.AuthRequestModel
import br.ufrgs.inf.pet.dinoapp.model.auth.AuthResponseModel
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/**
 * Administra o login e logout
 * @author joao.silva
 */
class AuthService constructor(private val activity : Activity) {

    private val googleSignInService = GoogleSignInService()

    private val userController = UserController(activity)

    /**
     * Busca por login salvo no banco de dados local.
     * Caso não encontre requisita ao usuário que entre com seu login.
     *
     * @author joao.silva
     */
    fun searchLogin() {
        val loginUser = userController.get()

        if(loginUser == null){
            goToLogin()
        } else {
            goToMenu()
        }
    }

    /**
     * Realiza o signout do usuário
     *
     * @author joao.silva
     */
    fun onSignOut() {
        userController.delete()
        googleSignInService.getClient(activity).signOut()
            .addOnCompleteListener(activity) {
                goToLogin()
            }
    }

    /**
     * Realiza o signin do usuário
     *
     * @author joao.silva
     */
    fun onSignIn() {
        val signInIntent = googleSignInService.getClient(activity).signInIntent
        activity.startActivityForResult(signInIntent, 0)
    }

    /**
     * Trata o retorno da API do Google com o token de autenticação para realizar o login do usuário
     *
     * @param completedTask response do servidor do Google
     * @author joao.silva
     */
    fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)

            if (account != null) {
                val authCode = account.serverAuthCode

                this.getAccessToken(authCode)
            } else {
                Toast.makeText(activity, "Falha ao obter login com o Google.", Toast.LENGTH_SHORT).show()
            }
        } catch (e: ApiException) {
            Toast.makeText(activity, "Falha no login com o Google.", Toast.LENGTH_SHORT).show()
        }

    }

    /**
     * Requisita um token de acesso na API utilizando o token de autenticação do Google
     *
     * @param token token de acesso gerado pelo Google no signIn
     * @author joao.silva
     */
    private fun getAccessToken(token: String?) {
        if (token != null) {
            val authRequestModel = AuthRequestModel(token)
            val retrofitClient = RetrofitClient(activity)

            retrofitClient.auth.getAccessToken(authRequestModel)
                .enqueue(object: Callback<AuthResponseModel> {
                    override fun onFailure(call: Call<AuthResponseModel>, t: Throwable) {
                        Toast.makeText(activity, "Falha na comunicação com o servidor.", Toast.LENGTH_SHORT).show()
                        onSignOut()
                    }

                    override fun onResponse(
                        call: Call<AuthResponseModel>,
                        response: Response<AuthResponseModel>
                    ) {
                        if (response.isSuccessful && response.body() != null) {
                            saveLogin(response.body()!!.accessToken)
                            goToMenu()
                        } else {
                            Toast.makeText(activity, "Falha na autenticação com a API.", Toast.LENGTH_SHORT).show()
                            onSignOut()
                        }
                    }
                })
        } else {
            Toast.makeText(activity, "Falha ao obter autenticação com o Google.", Toast.LENGTH_SHORT).show()
            onSignOut()
        }
    }

    /**
     * Salva o novo login no banco de dados interno
     *
     * @param accessToken token de acesso dado pela API
     * @author joao.silva
     */
    private fun saveLogin(accessToken: String) {
        userController.delete()
        val user = User(accessToken)
        userController.insert(user)
    }

    /**
     * Redireciona o usuário para o menu principal
     *
     * @author joao.silva
     */
    private fun goToMenu() {
        val intent = Intent(activity, MenuActivity::class.java)
        activity.startActivity(intent)
        activity.finish()
    }

    /**
     * Redireciona o usuário para a tela de login
     *
     * @author joao.silva
     */
    fun goToLogin() {
        val intent = Intent(activity, LoginActivity::class.java)
        activity.startActivity(intent)
        activity.finish()
    }
}