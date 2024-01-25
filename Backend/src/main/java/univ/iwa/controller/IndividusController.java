package univ.iwa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import univ.iwa.dto.IndividusDto;
import univ.iwa.service.IndividusService;

@RestController
@RequestMapping("/individus")
public class IndividusController {
    @Autowired
    IndividusService service;

    //il faut passer l'id de la formation selectionnee
//    @PostMapping("/inscription/{formationId}")
//    public ResponseEntity<IndividusDto> inscription(@RequestBody IndividusDto individuDto,
//                                                    @PathVariable long formationId) {
//        IndividusDto result = service.inscription(individuDto, formationId);
//        return new ResponseEntity<>(result, HttpStatus.CREATED);
//    }
}
