package univ.iwa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import univ.iwa.model.Formation;
import univ.iwa.service.FormationService;

@RestController
@RequestMapping("/form")
public class FormationControler {
    @Autowired
    FormationService service;
    @PostMapping("/addFormation")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_ASSISTANT')")
    public String addNewFormation(@RequestBody Formation formation) {
        return service.addFormation(formation);
    }
}
