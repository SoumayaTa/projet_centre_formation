package univ.iwa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import univ.iwa.dto.IndividusDto;
import univ.iwa.model.Groupe;
import univ.iwa.service.IndividusService;

@RestController
@RequestMapping("/individus")
@CrossOrigin(origins = "*")
public class IndividusController {
    @Autowired
    IndividusService service;

    //il faut passer l'id de la formation selectionnee
  @PostMapping("/inscription/{formationId}")
   public IndividusDto inscription(@RequestBody IndividusDto individuDto,
                                   @PathVariable Long formationId ) {
        return service.inscription(individuDto, formationId);
    }
 
}
