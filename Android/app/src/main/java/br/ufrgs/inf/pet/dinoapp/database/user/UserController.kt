package br.ufrgs.inf.pet.dinoapp.database.user

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.util.Log
import br.ufrgs.inf.pet.dinoapp.database.Database

/**
 * Classe para controlar a leitura e escrita do Usuário no banco
 *
 * Created by joao.silva.
 */
class UserController(context: Context?) {
    private var dataBaseSystem: SQLiteDatabase? = null
    private val database: Database = Database(context)
    private var dontCloseDataBase: Boolean

    fun insert(token: String?): Boolean {
        openDataBase()
        dontCloseDataBase = true
        delete()
        val result: Long
        val values = ContentValues()
        values.put(Database.TOKEN, token)
        result = dataBaseSystem!!.insert(Database.USER_TABLE, null, values)
        dontCloseDataBase = false
        closeDataBase()

        return result != -1L
    }

    fun insert(user : User): Boolean {
        return insert(user.token)
    }

    fun getUser() : User? {
        openDataBase()
        dontCloseDataBase = true
        val cursor: Cursor?
        val user: User
        val fields = arrayOf(
            Database.TOKEN
        )
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
                    user = User(cursor.getString(0))
                    return user
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

    fun delete() {
        openDataBase()
        dataBaseSystem!!.delete(Database.USER_TABLE, null, null)
        closeDataBase()
    }

    private fun openDataBase() {
        if (dataBaseSystem == null || !dataBaseSystem!!.isOpen) {
            dataBaseSystem = database.readableDatabase
        }
    }

    private fun closeDataBase() {
        if (!dontCloseDataBase) {
            dataBaseSystem!!.close()
        }
    }

    init {
        dontCloseDataBase = false
    }
}