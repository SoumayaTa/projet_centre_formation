package univ.iwa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import univ.iwa.dto.CalendrierDto;
import univ.iwa.service.CalendrierService;

import java.util.List;

@RestController
@RequestMapping("/calendrier")
@CrossOrigin(origins = "*")
public class CalendrierController {
    @Autowired
    CalendrierService service;

    @PostMapping("/addnewCalendar/{formationId}/{formateurId}/{entrepriseId}/{groupeId}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_ASSISTANT')")
    public CalendrierDto addNewCalendar(@RequestBody CalendrierDto calendrierdto,
                                        @PathVariable Long formationId,
                                        @PathVariable int formateurId,
                                        @PathVariable Long entrepriseId,
                                        @PathVariable Long groupeId){
        System.out.println("ana hnaaaaa ");
        return service.addCalendrier(calendrierdto,formationId,formateurId, entrepriseId,groupeId);
    }
    @GetMapping("/getEvents")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_ASSISTANT')")
    public List<CalendrierDto> getEvents() {
        return service.getEvents();
    }
}
