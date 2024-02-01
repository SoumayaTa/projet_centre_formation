package univ.iwa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import univ.iwa.dto.EntrepriseDto;
import univ.iwa.dto.GroupeDto;
import univ.iwa.model.Groupe;
import univ.iwa.service.GroupeService;

import java.util.List;
@RestController
@RequestMapping("/groupe")
@CrossOrigin(origins = "*")
public class GroupeController {

    @Autowired
    private GroupeService groupeService;

    @GetMapping("/getAllGroupes")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_ASSISTANT')")
    public List<GroupeDto> getAllGrouopes(){
        return groupeService.getAllGroupes();
    }


}
