package univ.iwa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import univ.iwa.model.Calendrier;
import univ.iwa.service.CalendrierService;

@RestController
@RequestMapping("/calendrier")
public class CalendrierController {
    @Autowired
    CalendrierService service;

    @PostMapping("/addnewCalendar/{formationId}/{formateurId}/{entrepriseId}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_ASSISTANT')")
    public String addNewCalendar(@RequestBody Calendrier calendrier,
                                 @PathVariable Long formationId,
                                 @PathVariable int formateurId,
                                 @PathVariable Long entrepriseId){
        return service.addCalendrier(calendrier,formationId,formateurId, entrepriseId);
    }
}
