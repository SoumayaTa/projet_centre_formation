package univ.iwa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import univ.iwa.dto.FormationDto;
import univ.iwa.model.Formation;
import univ.iwa.repository.FormationRepository;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FormationService {
    @Autowired
    FormationRepository repository;

    public FormationDto addFormation(FormationDto form) throws ParseException {
        form.setNom(form.getNom());
        form.setCategorie(form.getCategorie());
        form.setCout(form.getCout());
        LocalDate currentDate = LocalDate.now();
        form.setDate(currentDate);
        form.setObjectifs(form.getObjectifs());
        form.setProgramme(form.getProgramme());
        form.setVille(form.getVille());
        form.setNombreHeur(form.getNombreHeur());
        return FormationDto.toDto(repository.save(Formation.toEntity(form)));
    }

    public String deleteFormation(Long id) {
        Optional<Formation> existingForm = repository.findById(id);

        if (existingForm.isPresent()) {
            repository.deleteById(id);
            return "Formation deleted successfully";
        } else {
            return "Formation not found";
        }
    }

    public FormationDto updateFormation(Long id, FormationDto newForm) {
        Optional<Formation> existingForm = repository.findById(id);
        if (existingForm.isPresent()) {
            Formation form = existingForm.get();
            form.setNom(newForm.getNom());
            form.setCategorie(newForm.getCategorie());
            form.setCout(newForm.getCout());
            form.setObjectifs(newForm.getObjectifs());
            form.setProgramme(newForm.getProgramme());
            form.setVille(newForm.getVille());
            form.setNombreHeur(newForm.getNombreHeur());
            return FormationDto.toDto(repository.save(form));
        } else {
            return null;
        }
    }

    public List<FormationDto> findByCategorie(String categorie){
        List<Formation> formations = repository.findByCategorie(categorie);
        return formations.stream()
                .map(FormationDto::toDto)
                .collect(Collectors.toList());
    }

    public List<FormationDto> findByVille(String ville) {
        List<Formation> formations = repository.findByVille(ville);
        return formations.stream()
                .map(FormationDto::toDto)
                .collect(Collectors.toList());
    }

    public List<FormationDto> findByDate(LocalDate date) {
        List<Formation> formations = repository.findByDate(date);
        return formations.stream()
                .map(FormationDto::toDto)
                .collect(Collectors.toList());
    }

    public List<FormationDto> getallformation() {
        List<Formation> formations = repository.findAll();
        return formations.stream()
                .map(FormationDto::toDto)
                .collect(Collectors.toList());
    }
}
