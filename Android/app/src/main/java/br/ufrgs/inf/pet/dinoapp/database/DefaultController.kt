package br.ufrgs.inf.pet.dinoapp.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase

/**
 * Classe básica para gerar uma controller no banco
 *
 * Created by joao.silva.
 */
open class DefaultController(context: Context?) {
    open var dataBaseSystem: SQLiteDatabase? = null
    open val database: Database = Database(context)
    open var dontCloseDataBase: Boolean = false


    /**
     * Método default para iniciar um conexão com o banco de dados
     *
     * @author joao.silva
     */
    open fun openDataBase() {
        if (dataBaseSystem == null || !dataBaseSystem!!.isOpen) {
            dataBaseSystem = database.readableDatabase
        }
    }

    /**
     * Método default para encerrar a conexão com o banco de dados
     *
     * @author joao.silva
     */
    open fun closeDataBase() {
        if (!dontCloseDataBase) {
            dataBaseSystem!!.close()
        }
    }
}