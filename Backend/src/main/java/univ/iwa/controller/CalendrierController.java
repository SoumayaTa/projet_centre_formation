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
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/getEvents")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_ASSISTANT')")
    public List<CalendrierDto> getEvents() {
        return service.getEvents();
    }

    @GetMapping("/getEventById/{eventId}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_ASSISTANT')")
    public ResponseEntity<CalendrierDto> getEventById(@PathVariable Long eventId) {
        try {
            CalendrierDto event = service.getEventById(eventId);
            return new ResponseEntity<>(event, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getEventsByFormateurId/{formateurId}")
    @PreAuthorize("hasAnyAuthority('ROLE_FORMAT')")
    public List<CalendrierDto> getEventsByFormateurId(@PathVariable int formateurId) {
        try {
            List<CalendrierDto> events = service.getEventsByFormateurId(formateurId);
            return events != null ? new ResponseEntity<>(events, HttpStatus.OK).getBody() : (List<CalendrierDto>) new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return (List<CalendrierDto>) new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping("/updateCalendar/{select}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_ASSISTANT')")
    public ResponseEntity<CalendrierDto> updateCalendar(@RequestBody CalendrierDto calendrierDto,
                                                        @PathVariable String select) {
        try {
            CalendrierDto result = service.updateCalendrier(calendrierDto, select);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
