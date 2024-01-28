package univ.iwa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import univ.iwa.dto.InscriptionDto;
import univ.iwa.service.InscriptionService;

@RestController
@RequestMapping("/externe")
@CrossOrigin(origins = "*")
public class InscriptionController {
    @Autowired
    InscriptionService service;

    @PostMapping("/inscription/{name}/{email}/{mots_cles}")
    public InscriptionDto inscriptionFormateurExtern(@PathVariable String name,
                                                     @PathVariable String email,
                                                     @PathVariable String mots_cles){
        return service.inscrireFormateurExterne(name, email,mots_cles);
    }

    @DeleteMapping("/deleteAndCreateUserInfo/{inscriptionId}")
    public ResponseEntity<String> deleteAndCreateUserInfo(@PathVariable Long inscriptionId) {
        boolean result = service.acceptFormateur(inscriptionId);

        if (result) {
            return new ResponseEntity<>("User Info created successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Unable to perform the operation", HttpStatus.BAD_REQUEST);
        }
    }
}
