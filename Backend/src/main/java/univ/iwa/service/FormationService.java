package univ.iwa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import univ.iwa.model.Formation;
import univ.iwa.repository.FormationRepository;

import java.util.Date;

@Service
public class FormationService {
    @Autowired
    FormationRepository repository;

    public String addFormation(Formation form){
        form.setNom(form.getNom());
        form.setCategorie(form.getCategorie());
        form.setCout(form.getCout());
        form.setDate(new Date());
        form.setObjectifs(form.getObjectifs());
        form.setProgramme(form.getProgramme());
        form.setVille(form.getVille());
        form.setNombreHeur(form.getNombreHeur());
        repository.save(form);
        return "Formation added successfully";
    }
}
