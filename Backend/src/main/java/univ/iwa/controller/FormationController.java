package univ.iwa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
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

//    @PostMapping("formation/addFormation")
//    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_ASSISTANT')")
//    public FormationDto addNewFormation(@RequestBody FormationDto formation) throws ParseException {
//        return service.addFormation(formation);
//    }

    @PostMapping("addFormation/image")
    //@PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_ASSISTANT')")
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
//    @GetMapping("/getgroupe")
//    public List<Long> getgroupe(){
//
//    }

    @DeleteMapping("formation/deleteFormation/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_ASSISTANT')")
    public String deleteFormation(@PathVariable Long id) {
        return service.deleteFormation(id);
    }
    @PutMapping("formation/updateFormation/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_ASSISTANT')")
    public FormationDto updateFormation(@PathVariable Long id, @RequestBody FormationDto form) {
        return service.updateFormation(id, form);
    }
//    @GetMapping("/getByFilters")
//    public List<FormationDto> findByCategorie(@RequestParam String categorie){
//        return service.findByCategorie(categorie);
//    }
//
//    @GetMapping("/getByFilters")
//    public List<FormationDto> getByVille(@RequestParam String ville) {
//        return  service.findByVille(ville);
//    }
//    @GetMapping("/getByFilters")
//    public List<FormationDto> getByDate(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
//        try {
//            return service.findByDate(date);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }

    @GetMapping("/getByFilters")
    public List<FormationDto> findByFilters(
            @RequestParam(required = false) String categorie,
            @RequestParam(required = false) String ville,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        if (categorie != null) {
            return service.findByCategorie(categorie);
        } else if (ville != null) {
            return service.findByVille(ville);
        } else if (date != null) {
            try {
                return service.findByDate(date);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        } else {
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
    public List<FormationDto> getallFormation(){
        return service.getAllFormations();
    }
}
