package univ.iwa.service;

import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import univ.iwa.dto.FormationDto;
import univ.iwa.dto.GroupeDto;
import univ.iwa.dto.IndividusDto;
import univ.iwa.exception.FormationNotFoundException;
import univ.iwa.model.Formation;
import univ.iwa.model.Groupe;
import univ.iwa.model.Individus;
import univ.iwa.repository.FormationRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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
    @Autowired
    private EmailServiceImpl emailservice;

//    public FormationDto addFormation(FormationDto form) {
//        Formation formationEntity = modelMapper.map(form, Formation.class);
//        formationEntity.setDate(LocalDate.now());
//        Formation savedFormation = repository.save(formationEntity);
//        return modelMapper.map(savedFormation, FormationDto.class);
//    }


    //wiam's version
    public FormationDto addFormation(FormationDto form, MultipartFile image) throws IOException {
        Formation formationEntity = modelMapper.map(form, Formation.class);
        formationEntity.setDate(LocalDate.now());
        String pathImage = "src/main/resources/static/" + formationEntity.getId() + ".png";
        image.transferTo(new File(pathImage));
        String imageUrl = "src/main/resources/static/images/" + formationEntity.getId() + ".png";
        formationEntity.setPhotos(imageUrl);
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

//    public FormationDto updateFormation(Long id, FormationDto newForm) {
//        Optional<Formation> existingFormOptional = repository.findById(id);
//        Formation existingForm = existingFormOptional.get();
//        //la date a modifie f front
//        modelMapper.map(newForm, existingForm);
//        repository.save(existingForm);
//        return modelMapper.map(existingForm, FormationDto.class);
//    }

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

        public List<FormationDto> getAllFormations(int pageNumber, String searchKey) {
            Pageable pageable = PageRequest.of(pageNumber, 10);

            Page<Formation> page;
            if (searchKey.equals("")) {
                page = repository.findAll(pageable);
            } else {
                page = repository.findByNomContainingIgnoreCaseOrVilleContainingIgnoreCase(
                        searchKey, searchKey, pageable
                );
            }
            return page.getContent().stream()
                    .map(formation -> modelMapper.map(formation, FormationDto.class))
                    .collect(Collectors.toList());
        }

    public FormationDto addFormationim(
            String nom,
            Long nombreHeur,
            Long cout,
            String objectifs,
            String programme,
            String categorie,
            String ville,
            Long groupe_seuil,
            MultipartFile image
    ) throws IllegalStateException, IOException {
        Formation formationEntity = modelMapper.map(new FormationDto(
                nom,
                nombreHeur,
                cout,
                objectifs,
                programme,
                categorie,
                ville,
                groupe_seuil
        ), Formation.class);
        formationEntity.setDate(LocalDate.now());
        formationEntity = repository.save(formationEntity);
        String pathImage = "src/main/resources/static/images/";
        Files.createDirectories(Paths.get(pathImage));
        String imagePath = pathImage + formationEntity.getId() + ".png";
        image.transferTo(Paths.get(imagePath));
        String imageUrl = "http://localhost:8080/images/" + formationEntity.getId() + ".png";
        formationEntity.setPhotos(imageUrl);
        repository.save(formationEntity);
        return modelMapper.map(formationEntity, FormationDto.class);
    }
    public FormationDto updateFormation(
            Long id,
            String nom,
            Long nombreHeur,
            Long cout,
            String objectifs,
            String programme,
            String categorie,
            String ville,
            MultipartFile image
    ) throws IllegalStateException, IOException {
        Optional<Formation> optionalFormation = repository.findById(id);

        if (optionalFormation.isPresent()) {
            Formation existingFormation = optionalFormation.get();

            // Mettez à jour les champs nécessaires
            existingFormation.setNom(nom);
            existingFormation.setNombreHeur(nombreHeur);
            existingFormation.setCout(cout);
            existingFormation.setObjectifs(objectifs);
            existingFormation.setProgramme(programme);
            existingFormation.setCategorie(categorie);
            existingFormation.setVille(ville);

            if (image != null) {
                // Mettez à jour l'image seulement si elle est fournie
                String pathImage = "src/main/resources/static/images/";
                Files.createDirectories(Paths.get(pathImage));
                String imagePath = pathImage + existingFormation.getId() + ".png";
                image.transferTo(Paths.get(imagePath));
                String imageUrl = "http://localhost:8080/images/" + existingFormation.getId() + ".png";
                existingFormation.setPhotos(imageUrl);
            }

            existingFormation = repository.save(existingFormation);

            return modelMapper.map(existingFormation, FormationDto.class);
        } else {
            throw new FormationNotFoundException("Formation with id " + id + " not found");
        }
    }
        public List<String> getAllVilles () {
            return repository.getDistinctVilles();
        }

        public FormationDto updateFormationWithoutImage (
                Long id,
                String nom,
                Long nombreHeur,
                Long cout,
                String objectifs,
                String programme,
                String categorie,
                String ville
    ){
            Optional<Formation> optionalFormation = repository.findById(id);
            if (optionalFormation.isPresent()) {
                Formation existingFormation = optionalFormation.get();

                // Mettez à jour les champs nécessaires
                existingFormation.setNom(nom);
                existingFormation.setNombreHeur(nombreHeur);
                existingFormation.setCout(cout);
                existingFormation.setObjectifs(objectifs);
                existingFormation.setProgramme(programme);
                existingFormation.setCategorie(categorie);
                existingFormation.setVille(ville);
                existingFormation = repository.save(existingFormation);
                return modelMapper.map(existingFormation, FormationDto.class);
            } else {
                throw new FormationNotFoundException("Formation with id " + id + " not found");
            }
        }


        public FormationDto getFormationById (Long id){
            Optional<Formation> optionalFormation = repository.findById(id);

            if (optionalFormation.isPresent()) {
                Formation formation = optionalFormation.get();
                return modelMapper.map(formation, FormationDto.class);
            } else {
                throw new FormationNotFoundException("Formation with id " + id + " not found");
            }
        }

    public List<String> getAllCategories() {
        return repository.getDistinctCategories();
    }

    public List<Individus> getInscritsForFormation(Long formationId) {
        Formation formation = repository.findById(formationId)
                .orElseThrow(() -> new EntityNotFoundException("Formation not found with ID: " + formationId));

        return formation.getInscrits();
    }
    public Optional<Formation> findById(Long formationId) {
        return repository.findById(formationId);
    }
    public List<GroupeDto> getGroupesForFormation(Long formationId) {
        Optional<Formation> formationOptional = repository.findById(formationId);

        if (formationOptional.isPresent()) {
            Formation formation = formationOptional.get();
            List<Groupe> groupes = formation.getGroupes();
            return groupes.stream()
                    .map(groupe -> modelMapper.map(groupe, GroupeDto.class))
                    .collect(Collectors.toList());
        } else {
            throw new FormationNotFoundException("Formation avec l'ID " + formationId + " introuvable");
        }
    }
    public List<String> getInscriptionForFormation(Long formationId) {
        Optional<Formation> formationOptional = repository.findById(formationId);

        if (formationOptional.isPresent()) {
            Formation formation = formationOptional.get();
            List<Individus> individus = formation.getInscrits();
            return individus.stream()
                    .map(Individus::getEmail)
                    .collect(Collectors.toList());
        } else {
            throw new FormationNotFoundException("Inscrit avec l'ID " + formationId + " introuvable");
        }
    }

    public void sendEmailsToInscrits(Long formationId) {
        Optional<Formation> formationOptional = repository.findById(formationId);
        if (formationOptional.isPresent()) {
            Formation formation = formationOptional.get();
            List<Individus> individus = formation.getInscrits();

            for (Individus individu : individus) {
                String to = individu.getEmail();
                String subject ="hhwj";
                String body = "http://localhost:4200/evaluation?id="+individu.getId();
                emailservice.sendMail(to, "tayoubsoumaya21@gmail.com", subject, body);
            }
        } else {
            throw new FormationNotFoundException("Inscrit avec l'ID " + formationId + " introuvable");
        }
    }



}


