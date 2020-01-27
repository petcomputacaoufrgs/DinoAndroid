package br.ufrgs.inf.pet.dinoapi.service.glossary;

import br.ufrgs.inf.pet.dinoapi.entity.GlossaryItem;
import br.ufrgs.inf.pet.dinoapi.model.glossary_item_model.GlossaryItemModel;
import br.ufrgs.inf.pet.dinoapi.model.glossary_model.GlossaryModel;
import br.ufrgs.inf.pet.dinoapi.repository.GlossaryItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementação de {@link GlossaryItemService}
 *
 * @author joao.silva
 */
@Service
public class GlossaryItemServiceImpl implements GlossaryItemService {
    @Autowired
    GlossaryItemRepository glossaryItemRepository;

    @Override
    public ResponseEntity<?> save(GlossaryModel glossaryModel) {
        if (glossaryModel != null) {
            List<GlossaryItemModel> glossaryItemModelList = glossaryModel.getItemList();
            List<GlossaryItemModel> savedItens = new ArrayList<>();

            if (glossaryItemModelList != null) {

                for (GlossaryItemModel glossaryItemModel : glossaryItemModelList) {
                    if(glossaryItemModel.isValid()) {

                        GlossaryItem glossaryItem;

                        // Verifica se há um id
                        if(glossaryItemModel.getId() != null) {
                            // Verifica se o dado já existe em banco
                            glossaryItem = glossaryItemRepository.findOneById(glossaryItemModel.getId());

                            // Se existir, atualiza o texto, se não existir cria
                            if (glossaryItem == null) {
                                glossaryItem = new GlossaryItem(glossaryItemModel.getTitle(), glossaryItemModel.getText());
                            } else {
                                // Verifica se os títulos são iguais
                                if (glossaryItem.getTitle().equals(glossaryItemModel.getTitle())) {
                                    glossaryItem.setText(glossaryItemModel.getText());
                                } else {
                                    // Caso não sejam cria um novo
                                    glossaryItem = new GlossaryItem(glossaryItemModel.getTitle(), glossaryItemModel.getText());
                                }
                            }
                        } else {
                            glossaryItem = glossaryItemRepository.findOneByTitle(glossaryItemModel.getTitle());

                            if (glossaryItem == null) {
                                glossaryItem = new GlossaryItem(glossaryItemModel.getTitle(), glossaryItemModel.getText());
                            } else {
                                glossaryItem.setText(glossaryItem.getText());
                            }
                        }

                        glossaryItemRepository.save(glossaryItem);
                        glossaryItemModel.setId(glossaryItem.getId());
                        savedItens.add(glossaryItemModel);
                    }
                }

                glossaryModel.setItemList(savedItens);

                return new ResponseEntity<>(glossaryModel, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("Item inválido!", HttpStatus.BAD_REQUEST);
    }
}
