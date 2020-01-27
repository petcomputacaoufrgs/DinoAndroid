
package br.ufrgs.inf.pet.dinoapp.service

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import br.ufrgs.inf.pet.dinoapp.activity.LoginActivity
import br.ufrgs.inf.pet.dinoapp.activity.MenuActivity
import br.ufrgs.inf.pet.dinoapp.communication.RetrofitClient
import br.ufrgs.inf.pet.dinoapp.database.UserController
import br.ufrgs.inf.pet.dinoapp.model.auth.AuthRequestModel
import br.ufrgs.inf.pet.dinoapp.model.auth.AuthResponseModel
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/**
 * Manager of Google Auth data
 * Created by joao.silva.
 */
class AuthService constructor(private val activity : Activity) {

    private val googleSignInService = GoogleSignInService()

    private val userController = UserController(activity)

    fun onSignOut() {
        userController.deleteUser()
        googleSignInService.getClient(activity).signOut()
            .addOnCompleteListener(activity) {
                goToLogin()
            }
    }

    fun onSignIn() {
        val signInIntent = googleSignInService.getClient(activity).signInIntent
        activity.startActivityForResult(signInIntent, 0)
    }

    fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)

            if (account != null) {
                val authCode = account.serverAuthCode

                this.getAccessToken(authCode)
            }
            /*TO DO: Tratar erro de login*/
        } catch (e: ApiException) {
            // TO DO: Tratar erro de login
            Log.w(FragmentActivity.ACCOUNT_SERVICE, "Erro no login, código: " + e.statusCode)
        }

    }

    private fun getAccessToken(token: String?) {
        if (token != null) {
            val authRequestModel = AuthRequestModel(token)
            val retrofitClient = RetrofitClient(activity)

            retrofitClient.auth.getAccessToken(authRequestModel)
                .enqueue(object: Callback<AuthResponseModel> {
                    override fun onFailure(call: Call<AuthResponseModel>, t: Throwable) {
                        Toast.makeText(activity, "Falha na comunicação com o servidor.", Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(
                        call: Call<AuthResponseModel>,
                        response: Response<AuthResponseModel>
                    ) {
                        if (response.isSuccessful && response.body() != null) {
                            saveLogin(response.body()!!.accessToken, response.body()!!.expireIn)
                            goToMenu()
                        } else {
                            Toast.makeText(activity, "Falha na autenticação com a API.", Toast.LENGTH_SHORT).show()
                        }
                    }
                })
        } else {
            //TO-DO Tratar erro
        }
    }

    private fun saveLogin(accessToken: String, expiresIn: Long) {
        userController.insertUser(accessToken, expiresIn)
    }

    private fun goToMenu() {
        val intent = Intent(activity, MenuActivity::class.java)
        activity.startActivity(intent)
        activity.finish()
    }

    private fun goToLogin() {
        val intent = Intent(activity, LoginActivity::class.java)
        activity.startActivity(intent)
        activity.finish()
    }
}