package br.ufrgs.inf.pet.dinoapp.database.glossary_item

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteException
import android.util.Log
import br.ufrgs.inf.pet.dinoapp.database.Database
import br.ufrgs.inf.pet.dinoapp.database.DefaultController
import br.ufrgs.inf.pet.dinoapp.database.glossary_version.GlossaryVersion
import br.ufrgs.inf.pet.dinoapp.model.glossary.GlossaryModel
import br.ufrgs.inf.pet.dinoapp.model.glossary_item.GlossaryItemModel

/**
 * Classe para controlar a leitura e escrita dos itens do glossário no banco
 *
 * @author joao.silva
 */
class GlossaryItemController(context: Context?) : DefaultController(context) {

    /**
     * Insere todos os items dentro da GlossaryModel
     * @param glossaryModel com todos os itens do tipo GlossaryItemModel
     * @return número de itens inseridos no banco
     * @author joao.silva.
     */
    fun insert(glossaryModel: GlossaryModel): Int {
        openDataBase()
        dontCloseDataBase = true
        delete()
        var result: Long
        var successCount = 0
        for (glossaryItemModel in glossaryModel.itemList) {
            val values = ContentValues()
            values.put(Database.TEXT, glossaryItemModel.text)
            values.put(Database.TITLE, glossaryItemModel.title)
            result = dataBaseSystem!!.insert(Database.GLOSSARY_ITEM_TABLE, null, values)
            if (result != -1L) {
                successCount++
            }
        }
        dontCloseDataBase = false
        closeDataBase()

        return successCount
    }

    /**
     * Busca por todos os itens do glossário no banco de dados
     *
     * @return lista com todos os itens do glossário do tipo GlossaryItem?
     * @author joao.silva
     */
    fun getAll() : List<GlossaryItem?>? {
        openDataBase()
        dontCloseDataBase = true
        val cursor: Cursor?
        val fields = arrayOf(Database.TEXT, Database.TITLE)
        cursor = dataBaseSystem!!.query(
            Database.GLOSSARY_ITEM_TABLE,
            fields,
            null,
            null,
            null,
            null,
            null,
            null
        )
        return try {
            if (cursor != null) {
                if (cursor.count > 0) {
                    val items = ArrayList<GlossaryItem?>()
                    cursor.moveToFirst()

                    while(!cursor.isAfterLast) {
                        val glossaryItem = GlossaryItem(
                            cursor.getString(0), cursor.getString(1))

                        items.add(glossaryItem)
                        cursor.moveToNext()
                    }

                    return items
                }
                return null
            }
            null
        } catch (e: SQLiteException) {
            Log.d("SQL Error", e.message!!)
            null
        } finally {
            cursor.close()
            dontCloseDataBase = false
            closeDataBase()
        }
    }

    /**
     * Deleta toda a tabela
     *
     * @author joao.silva
     */
    fun delete() {
        openDataBase()
        dataBaseSystem!!.delete(Database.GLOSSARY_ITEM_TABLE, null, null)
        closeDataBase()
    }
}