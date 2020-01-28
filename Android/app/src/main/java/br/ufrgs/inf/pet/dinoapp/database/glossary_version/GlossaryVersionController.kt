package br.ufrgs.inf.pet.dinoapp.database.glossary_version

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteException
import android.util.Log
import br.ufrgs.inf.pet.dinoapp.database.Database
import br.ufrgs.inf.pet.dinoapp.database.DefaultController

/**
 * Classe para controlar a leitura e escrita da versão do glossário no banco
 *
 * Created by joao.silva.
 */
class GlossaryVersionController(context: Context?) : DefaultController(context) {

    /**
     * Salva a versão do glossário no banco
     *
     * @param glossaryVersion versão do glossário
     * @author joao.silva
     */
    fun insert(glossaryVersion: GlossaryVersion): Boolean {
        openDataBase()
        dontCloseDataBase = true
        delete()
        val result: Long
        val values = ContentValues()
        values.put(Database.GLOSSARY_VERSION, glossaryVersion.version)
        result = dataBaseSystem!!.insert(Database.GLOSSARY_VERSION_TABLE, null, values)
        dontCloseDataBase = false
        closeDataBase()

        return result != -1L
    }

    /**
     * Busca pela versão atual do glossário no banco
     *
     * @return GlossaryVersion com os dados atuais
     * @author joao.silva
     */
    fun get() : GlossaryVersion? {
        openDataBase()
        dontCloseDataBase = true
        val cursor: Cursor?
        val glossaryVersion: GlossaryVersion
        val fields = arrayOf(
            Database.GLOSSARY_VERSION
        )
        cursor = dataBaseSystem!!.query(
            Database.GLOSSARY_VERSION_TABLE,
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
                if (cursor.count > 1) {
                    delete()
                } else if (cursor.count == 1) {
                    cursor.moveToFirst()
                    glossaryVersion = GlossaryVersion(
                        cursor.getLong(0)
                    )
                    return glossaryVersion
                }
                return null
            }
            null
        } catch (e: SQLiteException) {
            Log.d("SQL Error", e.message)
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
    private fun delete() {
        openDataBase()
        dataBaseSystem!!.delete(Database.GLOSSARY_VERSION_TABLE, null, null)
        closeDataBase()
    }
}