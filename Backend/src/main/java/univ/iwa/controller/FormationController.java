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

//    @PostMapping("formation/addFormation")
//    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_ASSISTANT')")
//    public FormationDto addNewFormation(@RequestBody FormationDto formation) throws ParseException {
//        return service.addFormation(formation);
//    }

//    @PostMapping(value ={"/formation/addFormation"},consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_ASSISTANT')")
//    public FormationDto addFormation(
//            @RequestPart("formationDto") FormationDto formationDto,
//            @RequestParam("imageUrl") MultipartFile image
//    ) throws IOException {
//        return service.addFormation(formationDto, image);
//    }


    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @PostMapping(value = "formation/addFormation", consumes = {"multipart/form-data"})
    public ResponseEntity<FormationDto> addFormation(
            @RequestPart(value = "formationDto") FormationDto formationDto,
            @RequestPart(value = "image") MultipartFile image
    ) throws IOException {
        FormationDto result = service.addFormation(formationDto, image);
        return ResponseEntity.ok(result);
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
        return service.getAllFormations();
    }

}
