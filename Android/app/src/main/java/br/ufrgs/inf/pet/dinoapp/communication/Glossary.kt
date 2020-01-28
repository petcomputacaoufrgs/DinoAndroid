package br.ufrgs.inf.pet.dinoapp.communication

import br.ufrgs.inf.pet.dinoapp.model.glossary.GlossaryModel
import br.ufrgs.inf.pet.dinoapp.model.glossary_version.GlossaryVersionModel
import retrofit2.Call
import retrofit2.http.GET

/**
 * Controla a comunicação para informações do glossário
 * @author joao.silva
 */
interface Glossary {
    /**
     * Busca pela versão do glossário
     *
     * @return GlossaryVersionModel com a versão mais atual do glossário
     * @author joao.silva
     */
    @GET("glossary/version/")
    fun getGlossaryVersion(): Call<GlossaryVersionModel>

    /**
     * Busca pelo glossário
     *
     * @return GlossaryModel com todos os itens do glossário atualizado
     * @author joao.silva
     */
    @GET("glossary/")
    fun getGlossary(): Call<GlossaryModel>
}