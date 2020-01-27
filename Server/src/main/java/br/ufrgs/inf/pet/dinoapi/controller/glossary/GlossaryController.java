package br.ufrgs.inf.pet.dinoapi.controller.glossary;

import br.ufrgs.inf.pet.dinoapi.entity.GlossaryItem;
import br.ufrgs.inf.pet.dinoapi.model.glossary_model.GlossaryModel;
import org.springframework.http.ResponseEntity;

/**
 * Controller para gerenciar os dados relacionados aos itens do glossário
 *
 * @author joao.silva
 */
public interface GlossaryController {

    /**
     * Ao receber o model do item de glossário valida suas informações e salva em banco
     *
     * @param glossaryModel - Model com os dados para a criação de um item do glossário
     * @return lista com os itens salvos com sucesso ou erro
     */
    ResponseEntity<?> save(GlossaryModel glossaryModel);

}
