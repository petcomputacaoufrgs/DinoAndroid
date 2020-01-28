package br.ufrgs.inf.pet.dinoapp.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase

import android.database.sqlite.SQLiteOpenHelper


/**
 * Classe de criação do banco de dados interno
 *
 * Created by joao.silva.
 */
class Database(context: Context?) : SQLiteOpenHelper(context, DATABASE_NAME, null, VERSION) {

    /**
     * Método com o script de criação do banco
     *
     * @author joao.silva
     */
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE $USER_TABLE($ID integer primary key autoincrement,$TOKEN text)")
        db.execSQL("CREATE TABLE $GLOSSARY_VERSION_TABLE($ID integer primary key autoincrement,$GLOSSARY_VERSION long)")
        db.execSQL("CREATE TABLE $GLOSSARY_ITEM_TABLE($ID integer primary key autoincrement,$TITLE text,$TEXT text)")
    }

    /**
     * Método com o script de atualização do banco
     * Caso necessário atualizar um banco em produção faça com o máximo de cautela e teste possíveis
     * @author joao.silva
     */
    override fun onUpgrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int
    ) {
        db.execSQL("DROP TABLE IF EXISTS $USER_TABLE")
        onCreate(db)
    }

    /**
     * Nomes utilizados para tabelas e colunas do banco
     *
     * @author joao.silva
     */
    companion object {
        // Banco de Dados
        private const val DATABASE_NAME = "dinoapp.db"
        private const val VERSION = 1
        // Tabela usuário
        const val USER_TABLE = "user"
        const val ID = "id"
        const val TOKEN = "token"
        // Tabela de versão do glossário
        const val GLOSSARY_VERSION_TABLE = "glossary_version"
        const val GLOSSARY_VERSION = "glossary_version"
        // Tabela de itens do glossário
        const val GLOSSARY_ITEM_TABLE = "glossary_item"
        const val TITLE = "title"
        const val TEXT = "text"
    }
}