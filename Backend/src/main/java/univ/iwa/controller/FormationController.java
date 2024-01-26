package univ.iwa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import univ.iwa.dto.FormationDto;
import univ.iwa.model.Formation;
import univ.iwa.service.FormationService;

import java.io.IOException;
import java.text.ParseException;
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
            @RequestParam MultipartFile image
    )throws IllegalStateException, IOException {
        return service.addFormationim(nom, nombreHeur, cout, objectifs, programme, categorie, ville, image);
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

    @GetMapping("/getByVille/{ville}")
    public List<FormationDto> getByVille(@PathVariable String ville) {
        return  service.findByVille(ville);
    }
    @GetMapping("/getByDate")
    public List<FormationDto> getByDate(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        try {
            return service.findByDate(date);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/getall")
    public List<FormationDto> getallFormation(){
        return service.getAllFormations();
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



}
