package br.ufrgs.inf.pet.dinoapp.activity.menu_itens.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import br.ufrgs.inf.pet.dinoapp.R
import br.ufrgs.inf.pet.dinoapp.activity.MenuActivity
import br.ufrgs.inf.pet.dinoapp.communication.RetrofitClient
import br.ufrgs.inf.pet.dinoapp.model.auth.UsernameModel
import br.ufrgs.inf.pet.dinoapp.service.AuthService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Fisrt screen in Menu
 * Created by joao.silva.
 */
class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var authService : AuthService

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        authService = AuthService(requireActivity())
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        this.setTextHello(root)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btSignOut = view.findViewById<Button >(R.id.bt_logout)

        btSignOut.setOnClickListener(clickListener)
    }

    private val clickListener = View.OnClickListener { view ->
        when (view.id) {
            R.id.bt_logout -> authService.onSignOut()
        }
    }

    private fun setTextHello(root : View) {
        val menuActivity = requireActivity()
        val tvHello: TextView = root.findViewById(R.id.tv_hello)

        if (menuActivity is MenuActivity) {
            if (menuActivity.username != null) {
                val username = menuActivity.username
                tvHello.text = "Olá $username !"
            } else {
                setTextHello(menuActivity, tvHello)
            }
        }
    }

    private fun setTextHello(menuActivity: MenuActivity, tvHello: TextView) {
        val retrofitClient = RetrofitClient(requireActivity())

        retrofitClient.auth.name()
            .enqueue(object: Callback<UsernameModel>{
                override fun onFailure(call: Call<UsernameModel>, t: Throwable) {
                    Toast.makeText(activity, "Falha ao se comunicar com o servidor!", Toast.LENGTH_LONG).show()
                }

                override fun onResponse(call: Call<UsernameModel>, response: Response<UsernameModel>) {
                    if (response.isSuccessful) {
                        val name = response.body()?.name
                        tvHello.text = "Olá $name!"
                        menuActivity.username = name
                    } else {
                        Toast.makeText(activity, "Falha ao carregar dados do usuário", Toast.LENGTH_LONG).show()
                        authService.onSignOut()
                    }

                }
            })
    }
}