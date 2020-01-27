package br.ufrgs.inf.pet.dinoapp.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import br.ufrgs.inf.pet.dinoapp.R
import br.ufrgs.inf.pet.dinoapp.service.AuthService
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.SignInButton


/**
 * Manegement of login with Google Account
 * Created by joao.silva.
 */
class LoginActivity : AppCompatActivity() {

    private val authService = AuthService(this)
    val username : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val btSignIn = findViewById<SignInButton>(R.id.bt_sign_in)

        btSignIn.setOnClickListener(clickListener)
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 0) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)

            authService.handleSignInResult(task)
        }
    }

    private val clickListener = View.OnClickListener { view ->
        when (view.id) {
            R.id.bt_sign_in -> authService.onSignIn()
        }
    }
}

