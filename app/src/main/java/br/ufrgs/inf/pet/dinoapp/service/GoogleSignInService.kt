package br.ufrgs.inf.pet.dinoapp.service

import android.app.Activity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.Scope

/**
 * Administra os dados de autenticação do Google
 * @author joao.silva
 */
class GoogleSignInService {

    private val clientId = "467762039422-b4uo6jc6ioprna4r1sgauhos5inkj475.apps.googleusercontent.com"

    /**
     * Gera uma instância do GoogleSignInOptions com os dados do aplicativo e com as permissões necessárias.
     *
     * @return GoogleSignInOptions para gerar conexão
     * @author joao.silva
     */
    private fun getGso() : GoogleSignInOptions {
        // Define o escopo de acesso ao calendário do usuário
        val scope = Scope("https://www.googleapis.com/auth/calendar.events")

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestServerAuthCode(clientId)
            .requestScopes(scope)
            .requestEmail()
            .build()

        return gso
    }

    /**
     * Gera uma instância do GoogleSignInClient para conexão
     *
     * @return GoogleSignInClient para conexão
     * @author joao.silva
     */
    fun getClient(activity: Activity) : GoogleSignInClient {
        // Build a GoogleSignInClient with the options specified by gso.
        return GoogleSignIn.getClient(activity, getGso())
    }
}
