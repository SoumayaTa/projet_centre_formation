package univ.iwa.service;

import org.modelmapper.ModelMapper;
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
    @Autowired
    private ModelMapper modelMapper;

    public FormationDto addFormation(FormationDto form) {
        Formation formationEntity = modelMapper.map(form, Formation.class);
        formationEntity.setDate(LocalDate.now());
        Formation savedFormation = repository.save(formationEntity);
        return modelMapper.map(savedFormation, FormationDto.class);
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
        Optional<Formation> existingFormOptional = repository.findById(id);
        Formation existingForm = existingFormOptional.get();
        //la date a modifie f front
        modelMapper.map(newForm, existingForm);
        repository.save(existingForm);
        return modelMapper.map(existingForm, FormationDto.class);
    }

    public List<FormationDto> findByCategorie(String categorie) {
        List<Formation> formations = repository.findByCategorie(categorie);
        return formations.stream()
                .map(formation -> modelMapper.map(formation, FormationDto.class))
                .collect(Collectors.toList());
    }

    public List<FormationDto> findByVille(String ville) {
        List<Formation> formations = repository.findByVille(ville);
        return formations.stream()
                .map(formation -> modelMapper.map(formation, FormationDto.class))
                .collect(Collectors.toList());
    }

    public List<FormationDto> findByDate(LocalDate date) {
        List<Formation> formations = repository.findByDate(date);
        return formations.stream()
                .map(formation -> modelMapper.map(formation, FormationDto.class))
                .collect(Collectors.toList());
    }

    public List<FormationDto> getAllFormations() {
        List<Formation> formations = repository.findAll();
        return formations.stream()
                .map(formation -> modelMapper.map(formation, FormationDto.class))
                .collect(Collectors.toList());
    }
}
