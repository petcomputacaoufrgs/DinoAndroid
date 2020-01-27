package br.ufrgs.inf.pet.dinoapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.ufrgs.inf.pet.dinoapp.R
import br.ufrgs.inf.pet.dinoapp.database.UserController

/**
 * Main screen, control the first redirection based on user login
 * Created by joao.silva.
 */
class MainActivity : AppCompatActivity() {

    private val userController = UserController(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val loginUser = userController.getUser()

        if(loginUser == null || !loginUser.isValid()){
            goToLogin()
        } else {
            goToMenu()
        }
    }

    private fun goToMenu() {
        val intent = Intent(this, MenuActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun goToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}
