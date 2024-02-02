package univ.iwa.controller;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/addnewCalendar/{formationId}/{formateurId}/{select}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_ASSISTANT')")
    public ResponseEntity<CalendrierDto> addNewCalendar(@RequestBody CalendrierDto calendrierDto,
                                                        @PathVariable Long formationId,
                                                        @PathVariable int formateurId,
                                                        @PathVariable String select) {
        try {
            CalendrierDto result = service.addCalendrier(calendrierDto, formationId, formateurId, select);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            e.printStackTrace();  // Affichez l'exception dans la console
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();  // Affichez l'exception dans la console
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();  // Affichez l'exception dans la console
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/getEvents")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_ASSISTANT')")
    public List<CalendrierDto> getEvents() {
        return service.getEvents();
    }
}
