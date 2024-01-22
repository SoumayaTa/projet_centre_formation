package univ.iwa.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import univ.iwa.dto.FormationDto;
import univ.iwa.model.Formation;
import univ.iwa.repository.FormationRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

//    public FormationDto addFormationim(FormationDto form, MultipartFile image) throws IOException {
//        Formation formationEntity = modelMapper.map(form, Formation.class);
//        formationEntity.setDate(LocalDate.now());
//        Formation savedFormation = repository.save(formationEntity);
//        String pathImage = "src/main/resources/static/" + savedFormation.getId() + ".png";
//        image.transferTo(new File(pathImage));
//        String imageUrl = "http://localhost:8080/images/" + savedFormation.getId() + ".png";
//        savedFormation.setPhotos(imageUrl);
//        savedFormation = repository.save(savedFormation);
//        return modelMapper.map(savedFormation, FormationDto.class);
//    }


    public FormationDto addFormationim(
                String nom,
                Long nombreHeur,
                Long cout,
                String objectifs,
                String programme,
                String categorie,
                String ville,
                MultipartFile image
        ) throws IllegalStateException, IOException {
            Formation formationEntity = modelMapper.map(new FormationDto(
                    nom,
                    nombreHeur,
                    cout,
                    objectifs,
                    programme,
                    categorie,
                    ville
                    ), Formation.class);
            formationEntity.setDate(LocalDate.now());
            formationEntity = repository.save(formationEntity);
            String pathImage = "src/main/resources/static/";
            Files.createDirectories(Paths.get(pathImage));
            String imagePath = pathImage + formationEntity.getId() + ".png";
            image.transferTo(Paths.get(imagePath));
            String imageUrl = "http://localhost:8080/images/" + formationEntity.getId() + ".png";
            formationEntity.setPhotos(imageUrl);
            repository.save(formationEntity);
            return modelMapper.map(formationEntity, FormationDto.class);
        }
    }

