package br.ufrgs.inf.pet.dinoapp.model.glossary

import br.ufrgs.inf.pet.dinoapp.model.glossary_item.GlossaryItemModel

/**
 * Model para recebimento do glossário com todos seus itens
 *
 * @author joao.silva
 */
data class GlossaryModel(val version: Long, val itemList: List<GlossaryItemModel>)