package br.ufrgs.inf.pet.dinoapp.database.user

/**
 * Classe com a modelagem da tabela de usuario
 *
 * Created by joao.silva.
 */
class User {
    var token: String? = null

    constructor(token: String?) {
        this.token = token
    }
}