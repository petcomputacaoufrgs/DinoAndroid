package br.ufrgs.inf.pet.dinoapp.database.user

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteException
import android.util.Log
import br.ufrgs.inf.pet.dinoapp.database.Database
import br.ufrgs.inf.pet.dinoapp.database.DefaultController

/**
 * Classe para controlar a leitura e escrita do Usuário no banco
 *
 * Created by joao.silva.
 */
class UserController(context: Context?) : DefaultController(context) {

    /**
     * Salva um novo usuario
     *
     * @param user usuario atual
     * @return True para sucesso e False para erro
     * @author joao.silva
     */
    fun insert(user: User): Boolean {
        openDataBase()
        dontCloseDataBase = true
        delete()
        val result: Long
        val values = ContentValues()
        values.put(Database.TOKEN, user.token)
        result = dataBaseSystem!!.insert(Database.USER_TABLE, null, values)
        dontCloseDataBase = false
        closeDataBase()

        return result != -1L
    }

    /**
     * Retorna o usuario atual
     *
     * @return User com os dados atuais
     * @author joao.silva
     */
    fun get() : User? {
        openDataBase()
        dontCloseDataBase = true
        val cursor: Cursor?
        val user: User
        val fields = arrayOf(Database.TOKEN)
        cursor = dataBaseSystem!!.query(
            Database.USER_TABLE,
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
                    user =
                        User(
                            cursor.getString(
                                0
                            )
                        )
                    return user
                }
                return null
            }
            null
        } catch (e: SQLiteException) {
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
        dataBaseSystem!!.delete(Database.USER_TABLE, null, null)
        closeDataBase()
    }
}