package br.ufrgs.inf.pet.dinoapi.service.glossary;

import br.ufrgs.inf.pet.dinoapi.model.glossary_item_model.GlossaryItemModel;
import br.ufrgs.inf.pet.dinoapi.model.glossary_model.GlossaryModel;
import org.springframework.http.ResponseEntity;

/**
 * Service para gerenciar os dados dos item do glossário
 *
 * @author joao.silva
 */
public interface GlossaryItemService {
    /**
     * Ao receber o model de glossário valida suas informações e salva em banco
     *
     * @param glossaryModel - Model com os dados para a criação de um item do glossário
     * @return lista com os itens salvos com sucesso ou erro
     */
    ResponseEntity<?> save(GlossaryModel glossaryModel);
}
