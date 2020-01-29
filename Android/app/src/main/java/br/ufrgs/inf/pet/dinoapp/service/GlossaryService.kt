package br.ufrgs.inf.pet.dinoapp.service

import android.app.Activity
import android.widget.Toast
import br.ufrgs.inf.pet.dinoapp.communication.RetrofitClient
import br.ufrgs.inf.pet.dinoapp.database.glossary_item.GlossaryItem
import br.ufrgs.inf.pet.dinoapp.database.glossary_item.GlossaryItemController
import br.ufrgs.inf.pet.dinoapp.database.glossary_version.GlossaryVersion
import br.ufrgs.inf.pet.dinoapp.database.glossary_version.GlossaryVersionController
import br.ufrgs.inf.pet.dinoapp.model.glossary.GlossaryModel
import br.ufrgs.inf.pet.dinoapp.model.glossary_version.GlossaryVersionModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/**
 * Administra a comunicação com a API para obter o glossário
 * @author joao.silva
 */
class GlossaryService constructor(private val activity : Activity) {

    private val glossaryVersionController = GlossaryVersionController(activity)
    private val glossaryItemController = GlossaryItemController(activity)
    private val apiService = APIService(activity)

    /**
     * Busca pela lista de itens do glossário no banco de dados interno
     *
     * @return lista com todos os itens salavos no banco interno
     * @author joao.silva
     */
    fun getGlossary(): List<GlossaryItem?>? {
        return glossaryItemController.getAll()
    }

    /**
     * Busca por atualizações no glossário pela API
     * Primeiro busca a versão atual do glossário da API.
     *
     * @author joao.silva
     */
    fun updateGlossaryIfNecessary() {
        val retrofitClient = RetrofitClient(activity)

        retrofitClient.glossary.getGlossaryVersion()
            .enqueue(object: Callback<GlossaryVersionModel> {
                override fun onFailure(call: Call<GlossaryVersionModel>, t: Throwable) {
                    Toast.makeText(activity, "Falha na comunicação com o servidor para buscar atualizações do glossário.", Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(
                    call: Call<GlossaryVersionModel>,
                    response: Response<GlossaryVersionModel>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        updateGlossaryVersionIfNecessary(retrofitClient, response.body()!!.version!!)
                    } else {
                        apiService.handlerAPIError(response)
                    }
                }
            })
    }

    /**
     * Recebe a versão atual do glossário da API e realiza uma atualização se necessário
     *
     * @param retrofitClient cliente Retrofit para realizar requisições
     * @param version versão atual do glossário na API
     * @author joao.silva
     */
    private fun updateGlossaryVersionIfNecessary(retrofitClient: RetrofitClient, version : Long) {
        // Busca pela versão atual
        val glossaryVersion = glossaryVersionController.get()

        // Se a versão atual for menor que a disponível então atualiza
        if (glossaryVersion?.version == null || glossaryVersion.version < version) {
            updateGlossary(retrofitClient)
        }
    }

    /**
     * Dado que necessário realiza a atualização do glossário salvo no dispositivo.
     * Primeiro requisita a versão mais atual na API
     *
     * @param retrofitClient cliente Retrofit para realizar requisições
     * @author joao.silva
     */
    private fun updateGlossary(retrofitClient: RetrofitClient) {
        retrofitClient.glossary.getGlossary()
            .enqueue(object: Callback<GlossaryModel> {
                override fun onFailure(call: Call<GlossaryModel>, t: Throwable) {
                    Toast.makeText(activity, "Falha na comunicação com o servidor para atualizar o glossário.", Toast.LENGTH_SHORT).show()
                    tryAgainMessage()
                }

                override fun onResponse(
                    call: Call<GlossaryModel>,
                    response: Response<GlossaryModel>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        saveGlossary(response.body()!!)
                    } else {
                        apiService.handlerAPIError(response)
                    }
                }
            })
    }

    /**
     * Dado uma versão do glossário com todos seus itens, salva ela no banco de dados interno
     *
     * @param glossaryModel model contendo o glossário atual
     * @author joao.silva
     */
    private fun saveGlossary(glossaryModel: GlossaryModel) {
        val glossaryVersion = GlossaryVersion(glossaryModel.version)
        val oldVersion = glossaryVersionController.get()

        // Atualiza a versão do glossário, se sucesso prossegue com o processo de atualização
        if(glossaryVersionController.insert(glossaryVersion)){
            // Se atualizar com sucesso, atualiza o glossário
            val savedItemsCount = glossaryItemController.insert(glossaryModel)

            if (savedItemsCount == glossaryModel.itemList.size) {
                Toast.makeText(activity, "Glossário atualizado para a versão " + glossaryModel.version, Toast.LENGTH_SHORT).show()
            } else if(oldVersion != null){ // Retorna a versão antiga devido a falha na atualização total
                glossaryVersion.version = oldVersion.version
                glossaryVersionController.insert(glossaryVersion)
                tryAgainMessage()
            }
        } else {
            Toast.makeText(activity, "Falha ao salvar atualização do glossário.", Toast.LENGTH_SHORT).show()
            tryAgainMessage()
        }
    }

    /**
     * Exibe mensagens de erro ao tentar atualizar o glossário
     *
     * @param glossaryModel model contendo o glossário atual
     * @author joao.silva
     */
    private fun tryAgainMessage() {
        Toast.makeText(activity, "Você estará utilizando uma versão desatualizada do glossário.", Toast.LENGTH_SHORT).show()
        Toast.makeText(activity, "Reinicie o aplicativo para tentar atualizar novamente.", Toast.LENGTH_SHORT).show()
    }
}