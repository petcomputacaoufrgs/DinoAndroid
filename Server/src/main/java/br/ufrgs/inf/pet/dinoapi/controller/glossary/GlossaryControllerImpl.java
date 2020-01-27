package br.ufrgs.inf.pet.dinoapi.controller.glossary;

import br.ufrgs.inf.pet.dinoapi.entity.GlossaryItem;
import br.ufrgs.inf.pet.dinoapi.model.glossary_item_model.GlossaryItemModel;
import br.ufrgs.inf.pet.dinoapi.model.glossary_model.GlossaryModel;
import br.ufrgs.inf.pet.dinoapi.service.glossary.GlossaryItemService;
import br.ufrgs.inf.pet.dinoapi.service.glossary.GlossaryItemServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Implementação de: {@link GlossaryController}
 *
 * @author joao.silva
 */
@RestController
@RequestMapping("/glossary/")
public class GlossaryControllerImpl implements GlossaryController {

    @Autowired
    GlossaryItemServiceImpl glossaryItemService;

    @Override
    @PostMapping()
    public ResponseEntity<?> save(@RequestBody GlossaryModel glossaryModel) {
        return glossaryItemService.save(glossaryModel);
    }
}
