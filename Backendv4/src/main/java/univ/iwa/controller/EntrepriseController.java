package univ.iwa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import univ.iwa.dto.EntrepriseDto;
import univ.iwa.model.Entreprise;
import univ.iwa.service.EntrepriseService;

import java.util.List;

@RestController
@RequestMapping("/entreprise")
@CrossOrigin(origins = "*")
public class EntrepriseController {

    @Autowired
    EntrepriseService service;

   @GetMapping("/getallEntreprise")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_ASSISTANT')")
    public List<EntrepriseDto> getAllEntreprise(){
        return service.getAllEntreprise();
    }

    @PostMapping("/addNewEntreprise")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_ASSISTANT')")
    public EntrepriseDto addNewEntreprise (@RequestBody EntrepriseDto entreprise){
        return  service.addNewEntreprise(entreprise);
    }

   @PutMapping("/updateEntreprise/{id}")
   @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_ASSISTANT')")
    public EntrepriseDto updateEntreprise(@PathVariable long id, @RequestBody EntrepriseDto updatedEntreprise) {
        return service.updateEntreprise(id, updatedEntreprise);
    }

  @DeleteMapping("/removeEntreprise/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_ASSISTANT')")
    public String removeEntreprise(@PathVariable long id) {
        return service.removeEntreprise(id);
    }
}
