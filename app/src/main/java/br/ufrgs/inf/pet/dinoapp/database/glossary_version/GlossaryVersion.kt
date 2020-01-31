package br.ufrgs.inf.pet.dinoapp.database.glossary_version

/**
 * Classe com a modelagem da tabela de versão do glossário
 *
 * Created by joao.silva.
 */
class GlossaryVersion {
    var version: Long = 0

    constructor(version: Long) {
        this.version = version
    }
}