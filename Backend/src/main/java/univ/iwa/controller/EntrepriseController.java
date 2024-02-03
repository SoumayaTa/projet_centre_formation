package univ.iwa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import univ.iwa.dto.EntrepriseDto;
import univ.iwa.dto.FormationDto;
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
    public ResponseEntity<EntrepriseDto> updateEntreprise(@PathVariable long id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String address,
            @RequestParam(required = false) String phoneNumber,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String url) 

	   {
	        try {
	        	
	            EntrepriseDto updatedEntreprise = service.updateEntreprise(id, name, address, phoneNumber, email, url);
	            System.out.println(updatedEntreprise);
	            return ResponseEntity.ok(updatedEntreprise);
	        } catch (Exception e) {
	            e.printStackTrace();
	            return ResponseEntity.status(500).build();
	        }
    }
   

  @DeleteMapping("/removeEntreprise/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_ASSISTANT')")
    public String removeEntreprise(@PathVariable long id) {
        return service.removeEntreprise(id);
    }
  @GetMapping("/geEntrepriseById/{id}")
  @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_ASSISTANT')")
  public ResponseEntity<EntrepriseDto> getEntrepriseById(@PathVariable Long id){
      try {
    	  EntrepriseDto entrepriseDto = service.getEntrepriseById(id);
          if (entrepriseDto != null) {
              return ResponseEntity.ok(entrepriseDto);
          } else {
              return ResponseEntity.notFound().build();
          }
      } catch (Exception e) {
          e.printStackTrace();
          return ResponseEntity.status(500).build();
      }
  }
}
