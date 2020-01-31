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

    /**
     * Conexão com os métodos de TestCommunication
     *
     * @author joao.silva
     */
    val testCommunication: TestCommunication by lazy {
        RetrofitService(this.activity).getRetrofitFactory().create(TestCommunication::class.java)
    }

    /**
     * Conexão com os métodos de Auth
     *
     * @author joao.silva
     */
    val auth: Auth by lazy {
        RetrofitService(this.activity).getRetrofitFactory().create(Auth::class.java)
    }

    /**
     * Conexão com os métodos de Glossary
     *
     * @author joao.silva
     */
    val glossary: Glossary by lazy {
        RetrofitService(this.activity).getRetrofitFactory().create(Glossary::class.java)
    }
}
