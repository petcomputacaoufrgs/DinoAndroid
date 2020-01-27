package br.ufrgs.inf.pet.dinoapp.communication

import android.app.Activity
import br.ufrgs.inf.pet.dinoapp.service.RetrofitService

/**
 * Configuração do Retrofit
 *
 * Created by joao.silva.
 *
 * Exemplos de comunicação: https://howtodoinjava.com/retrofit2/retrofit-sync-async-calls/
 */
class RetrofitClient constructor(private val activity : Activity) {
    val testCommunication: TestCommunication by lazy {
        RetrofitService(this.activity).getRetrofitFactoryWithoutAuth().create(TestCommunication::class.java)
    }

    val auth: Auth by lazy {
        RetrofitService(this.activity).getRetrofitFactory().create(Auth::class.java)
    }
}
