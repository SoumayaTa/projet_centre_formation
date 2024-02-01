package univ.iwa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import univ.iwa.dto.FormationDto;
import univ.iwa.dto.GroupeDto;
import univ.iwa.dto.IndividusDto;
import univ.iwa.model.Formation;
import univ.iwa.model.Groupe;
import univ.iwa.service.EmailService;
import univ.iwa.service.FormationService;
import univ.iwa.service.IndividusService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/individus")
@CrossOrigin(origins = "*")
public class IndividusController {
    @Autowired
    IndividusService service;

    @Autowired
    EmailService emailService;

    @Autowired
    FormationService formationService;

    @PostMapping("/inscription/{formationId}")
    public IndividusDto inscription(@RequestBody IndividusDto individuDto,
                                    @PathVariable Long formationId) {
        IndividusDto result = service.inscription(individuDto, formationId);
        Optional<Formation> optionalFormation = formationService.findById(formationId);
            Formation formation = optionalFormation.get();
            String formationNom = formation.getNom();
            sendInscriptionEmail(individuDto.getEmail(), individuDto.getNom(), formationNom);
        return result;
    }

    private void sendInscriptionEmail(String to, String nom, String formationNom) {
        String subject = "Formation " + formationNom;
        String body = "Bienvenue " + nom + ",<br/><br/>"+
                "Merci de vous être inscrit à la formation " + formationNom + ",<br/><br/>" +
                "Nous vous contacterons lorsque la formation commencera.";
        String cc = "tayoubsoumaya21@gmail.com";
        emailService.sendMail(to, cc, subject, body);
    }

    @GetMapping("/getAllIndividus")
    public List<IndividusDto> getAllIndividus(){
        return service.getAllIndividus();
    }
}




