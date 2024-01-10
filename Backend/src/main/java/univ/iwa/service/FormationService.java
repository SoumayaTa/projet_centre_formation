package univ.iwa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import univ.iwa.model.Formation;
import univ.iwa.repository.FormationRepository;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class FormationService {
    @Autowired
    FormationRepository repository;

    public String addFormation(Formation form) throws ParseException {
        form.setNom(form.getNom());
        form.setCategorie(form.getCategorie());
        form.setCout(form.getCout());
        LocalDate currentDate = LocalDate.now();
        form.setDate(currentDate);
        form.setObjectifs(form.getObjectifs());
        form.setProgramme(form.getProgramme());
        form.setVille(form.getVille());
        form.setNombreHeur(form.getNombreHeur());
        repository.save(form);
        return "Formation added successfully";
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

    public String updateFormation(Long id, Formation newForm) {
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
            repository.save(form);
            return "Formation updated successfully";
        } else {
            return "Formation not found";
        }
    }
   public List<Formation> findByCategorie(String categorie){
        return repository.findByCategorie(categorie);
    }

    public List<Formation> findByVille(String ville) {
        return repository.findByVille(ville);
    }

    public List<Formation> findByDate(LocalDate date) {
        return repository.findByDate(date);
    }

    public List<Formation> getallformation() {
        return repository.findAll();
    }
}
