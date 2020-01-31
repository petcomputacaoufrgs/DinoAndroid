package br.ufrgs.inf.pet.dinoapp.database.glossary_item

/**
 * Classe com a modelagem da tabela com os itens do glossário
 *
 * @author joao.silva
 */
class GlossaryItem {
    var title: String? = null
    var text: String? = null

    constructor(title: String?, text: String?) {
        this.title = title
        this.text = text
    }
}