package br.ufrgs.inf.pet.dinoapp.service

import android.app.Activity
import android.view.View
import android.widget.Toast
import br.ufrgs.inf.pet.dinoapp.communication.RetrofitClient
import br.ufrgs.inf.pet.dinoapp.model.TestCommunicationModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.Scopes
import com.google.android.gms.common.api.Scope
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Manager of Google Auth data
 * Created by joao.silva.
 */
class GoogleSignInService {

    var ClientID = "467762039422-lgajbj314vd4ehfq51n8h7abear3pcjk.apps.googleusercontent.com"

    fun getGso() : GoogleSignInOptions {

        // Define o escopo de acesso ao calendário do usuário
        val scope = Scope("https://www.googleapis.com/auth/calendar.events")

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestServerAuthCode(ClientID)
            .requestScopes(scope)
            .requestEmail()
            .build()

        return gso
    }

    fun getClient(activity: Activity) : GoogleSignInClient {
        // Build a GoogleSignInClient with the options specified by gso.
        return GoogleSignIn.getClient(activity, getGso());
    }

    fun Bulder() : GoogleSignInService{
        return this
    }

    fun getIdToken(account: GoogleSignInAccount) : String? {
        return account.getIdToken()
    }

}