package univ.iwa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import univ.iwa.dto.FormationDto;
import univ.iwa.model.Formation;
import univ.iwa.service.FormationService;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/form")
public class FormationController {
    @Autowired
    FormationService service;

    @PostMapping("formation/addFormation")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_ASSISTANT')")
    public FormationDto addNewFormation(@RequestBody FormationDto formation) throws ParseException {
        return service.addFormation(formation);
    }

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
        return service.getallformation();
    }

}
