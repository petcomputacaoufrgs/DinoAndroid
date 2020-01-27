package br.ufrgs.inf.pet.dinoapp.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.util.Log

/**
 * Classe para controlar a leitura e escrita do Usuário no banco
 *
 * Created by joao.silva.
 */
class UserController(context: Context?) {
    private var dataBaseSystem: SQLiteDatabase? = null
    private val dataBase: DataBase = DataBase(context)
    private var dontCloseDataBase: Boolean

    fun insertUser(token: String?, expiresIn: Long?): Boolean {
        openDataBase()
        dontCloseDataBase = true
        deleteUser()
        val values: ContentValues
        val result: Long
        values = ContentValues()
        values.put(DataBase.TOKEN, token)
        values.put(DataBase.EXPIRES_IN, expiresIn)
        result = dataBaseSystem!!.insert(DataBase.USER_TABLE, null, values)
        dontCloseDataBase = false
        closeDataBase()

        return result != -1L
    }

    fun insertUser(user : User): Boolean {
        return insertUser(user.token, user.expiresIn)
    }

    fun getUser() : User? {
        openDataBase()
        dontCloseDataBase = true
        val cursor: Cursor?
        val user: User
        val fields = arrayOf(DataBase.TOKEN, DataBase.EXPIRES_IN)
        cursor = dataBaseSystem!!.query(
            DataBase.USER_TABLE,
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
                    deleteUser()
                } else if (cursor.count == 1) {
                    cursor.moveToFirst()
                    user = User(cursor.getString(0), cursor.getLong(1))
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

    fun deleteUser() {
        openDataBase()
        dataBaseSystem!!.delete(DataBase.USER_TABLE, null, null)
        closeDataBase()
    }

    private fun openDataBase() {
        if (dataBaseSystem == null || !dataBaseSystem!!.isOpen) {
            dataBaseSystem = dataBase.readableDatabase
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