package univ.iwa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import univ.iwa.dto.CalendrierDto;
import univ.iwa.model.Calendrier;
import univ.iwa.service.CalendrierService;

@RestController
@RequestMapping("/calendrier")
public class CalendrierController {
    @Autowired
    CalendrierService service;

    @PostMapping("/addnewCalendar/{formationId}/{formateurId}/{entrepriseId}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_ASSISTANT')")
    public CalendrierDto addNewCalendar(@RequestBody CalendrierDto calendrierdto,
                                        @PathVariable Long formationId,
                                        @PathVariable int formateurId,
                                        @PathVariable Long entrepriseId){
        return service.addCalendrier(calendrierdto,formationId,formateurId, entrepriseId);
    }
}
