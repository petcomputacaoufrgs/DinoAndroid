package br.ufrgs.inf.pet.dinoapp.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase

import android.database.sqlite.SQLiteOpenHelper


/**
 * Classe de criação do banco de dados interno
 *
 * Created by joao.silva.
 */
class DataBase(context: Context?) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE $USER_TABLE($ID integer primary key autoincrement,$TOKEN text,$EXPIRES_IN long)")
    }

    override fun onUpgrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int
    ) {
        db.execSQL("DROP TABLE IF EXISTS $USER_TABLE")
        onCreate(db)
    }

    companion object {
        private const val DATABASE_NAME = "dinoapp.db"
        const val USER_TABLE = "user"
        const val ID = "id"
        const val TOKEN = "token"
        const val EXPIRES_IN = "expires_in"
        private const val VERSION = 1
    }
}