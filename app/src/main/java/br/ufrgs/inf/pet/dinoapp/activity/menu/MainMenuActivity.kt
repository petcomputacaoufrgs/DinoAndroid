package br.ufrgs.inf.pet.dinoapp.activity.menu

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import br.ufrgs.inf.pet.dinoapp.R
import br.ufrgs.inf.pet.dinoapp.service.GlossaryService


/**
 * Main menu for users with valid login
 * Created by joao.silva.
 */
class MainMenuActivity : AppCompatActivity() {

    private val glossaryService = GlossaryService(this)
    var username : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        // Busca atualizações no glossário em segundo plano
        glossaryService.updateGlossaryIfNecessary()

        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        navView.setupWithNavController(navController)
    }
}
