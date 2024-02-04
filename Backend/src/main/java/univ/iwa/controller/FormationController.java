package univ.iwa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import univ.iwa.dto.FormationDto;
import univ.iwa.dto.GroupeDto;
import univ.iwa.dto.IndividusDto;
import univ.iwa.model.Individus;
import univ.iwa.service.FormationService;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/form")
@CrossOrigin(origins = "*")
public class FormationController {

    @Autowired
    FormationService service;


    @PostMapping("formation/addFormation/image")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_ASSISTANT')")
    public FormationDto addFormation(
            @RequestParam String nom,
            @RequestParam Long nombreHeur,
            @RequestParam Long cout,
            @RequestParam String objectifs,
            @RequestParam String programme,
            @RequestParam String categorie,
            @RequestParam String ville,
            @RequestParam Long groupe_seuil,
            @RequestParam MultipartFile image
    )throws IllegalStateException, IOException {
        return service.addFormationim(nom, nombreHeur, cout, objectifs, programme, categorie, ville,groupe_seuil, image);
    }

    @DeleteMapping("formation/deleteFormation/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_ASSISTANT')")
    public void deleteFormation(@PathVariable Long id) {
        System.out.println("fonction called");
        service.deleteFormation(id);
    }
//    @PutMapping("formation/updateFormation/{id}")
//    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_ASSISTANT')")
//    public FormationDto updateFormation(@PathVariable Long id, @RequestBody FormationDto form) {
//        return service.updateFormation(id, form);
//    }


    @PutMapping("formation/updateFormation/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_ASSISTANT')")
    public FormationDto updateFormation(
            @PathVariable Long id,
            @RequestParam(required = false) String nom,
            @RequestParam(required = false) Long nombreHeur,
            @RequestParam(required = false) Long cout,
            @RequestParam(required = false) String objectifs,
            @RequestParam(required = false) String programme,
            @RequestParam(required = false) String categorie,
            @RequestParam(required = false) String ville,
            @RequestParam(required = false) MultipartFile image
    ) throws IllegalStateException, IOException {
        return service.updateFormation(id, nom, nombreHeur, cout, objectifs, programme, categorie, ville, image);
    }

    @PutMapping("formation/editFormationWithoutImage/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_ASSISTANT')")
    public ResponseEntity<FormationDto> editFormationWithoutImage(
            @PathVariable Long id,
            @RequestParam(required = false) String nom,
            @RequestParam(required = false) Long nombreHeur,
            @RequestParam(required = false) Long cout,
            @RequestParam(required = false) String objectifs,
            @RequestParam(required = false) String programme,
            @RequestParam(required = false) String categorie,
            @RequestParam(required = false) String ville
    ) {
        try {
            FormationDto updatedFormation = service.updateFormationWithoutImage(id, nom, nombreHeur, cout, objectifs, programme, categorie, ville);
            return ResponseEntity.ok(updatedFormation);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/getByCategorie/{categorie}")
    public List<FormationDto> findByCategorie(@PathVariable String categorie){
        return service.findByCategorie(categorie);
    }

    @GetMapping("/getByFilters")
    public List<FormationDto> findByFilters(
            @RequestParam(required = false) String categorie,
            @RequestParam(required = false) String ville) {

        if (categorie != null) {
            return service.findByCategorie(categorie);
        } else if (ville != null) {
            return service.findByVille(ville);
        }  else {
            return null;
        }
    }

    @GetMapping("/categories")
    public ResponseEntity<List<String>> getCategories() {
        List<String> categories = service.getAllCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }
    @GetMapping("/villes")
    public ResponseEntity<List<String>> getVilles() {
        List<String> villes = service.getAllVilles();
        return new ResponseEntity<>(villes, HttpStatus.OK);
    }
    @GetMapping("/getall")
    public List<FormationDto> getallFormation(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "") String searchKey
    ) {
        List<FormationDto> result = service.getAllFormations(pageNumber, searchKey);
        System.out.println("Result size is " + result.size());
        return result;
    }

    @GetMapping("formation/getFormationById/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_ASSISTANT')")
    public ResponseEntity<FormationDto> getFormationById(@PathVariable Long id){
        try {
            FormationDto formationDto = service.getFormationById(id);
            if (formationDto != null) {
                return ResponseEntity.ok(formationDto);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }
    @GetMapping("/groupes/{formationId}")

    public List<GroupeDto> getGroupesForFormation(@PathVariable Long formationId) {
        return service.getGroupesForFormation(formationId);
    }
    @GetMapping("/individus/{formationId}")
    public List<String> getInscriptionEmailsForFormation(@PathVariable Long formationId) {
        return service.getInscriptionForFormation(formationId);
    }

    @GetMapping("/sendemail/{formationId}")
    public String sendEmailToInscrits(@PathVariable Long formationId) {
        service.sendEmailsToInscrits(formationId);
        return "Emails sent successfully";
    }


}
