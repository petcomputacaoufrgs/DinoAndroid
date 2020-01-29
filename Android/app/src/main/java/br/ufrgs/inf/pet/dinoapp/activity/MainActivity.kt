package br.ufrgs.inf.pet.dinoapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.ufrgs.inf.pet.dinoapp.R
import br.ufrgs.inf.pet.dinoapp.service.AuthService

/**
 * Main screen, control the first redirection based on user login
 * Created by joao.silva.
 */
class MainActivity : AppCompatActivity() {

    private val authService = AuthService(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Busca pelo login e toma ação apartir da resposta
        authService.searchLogin()
    }
}
