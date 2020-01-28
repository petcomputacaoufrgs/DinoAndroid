package br.ufrgs.inf.pet.dinoapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.ufrgs.inf.pet.dinoapp.R
import br.ufrgs.inf.pet.dinoapp.database.user.UserController
import br.ufrgs.inf.pet.dinoapp.service.AuthService
import br.ufrgs.inf.pet.dinoapp.service.GlossaryService

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
