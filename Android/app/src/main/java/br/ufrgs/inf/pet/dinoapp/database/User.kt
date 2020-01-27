package br.ufrgs.inf.pet.dinoapp.database

import java.util.*

/**
 * Classe com a modelagem da tabela de usuario
 *
 * Created by joao.silva.
 */
class User {
    var token: String? = null

    var expiresIn: Long = 0

    constructor(token: String?, expiresIn: Long) {
        this.token = token
        this.expiresIn = expiresIn
    }

    fun isExpired() : Boolean {
        return true
        //return Date().time >= this.expiresIn - (1000 * 60)
    }

    fun isValid() : Boolean {
        return token != null && token != ""
    }
}