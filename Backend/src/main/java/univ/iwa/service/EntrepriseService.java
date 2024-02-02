package univ.iwa.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import univ.iwa.dto.EntrepriseDto;
import univ.iwa.dto.FormationDto;
import univ.iwa.exception.FormationNotFoundException;
import univ.iwa.model.Entreprise;
import univ.iwa.model.Formation;
import univ.iwa.repository.CalendrierRepository;
import univ.iwa.repository.EntrepriseRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EntrepriseService {
    @Autowired
    EntrepriseRepository repository;
    @Autowired
    private CalendrierService calendrierService;
    @Autowired
    private ModelMapper modelMapper;

    public List<EntrepriseDto> getAllEntreprise() {
    List<Entreprise> entreprises = repository.findAll();
    return entreprises.stream()
            .map(entreprise -> modelMapper.map(entreprise, EntrepriseDto.class))
            .collect(Collectors.toList());
}


    public EntrepriseDto addNewEntreprise(EntrepriseDto entrepriseDto) {
        Entreprise entreprise = modelMapper.map(entrepriseDto, Entreprise.class);
        Entreprise savedEntreprise = repository.save(entreprise);
        return modelMapper.map(savedEntreprise, EntrepriseDto.class);
    }

    /* public EntrepriseDto updateEntreprise(long id, EntrepriseDto entreprisedto) {
        Optional<Entreprise> entrepriseOptional = repository.findById(id);
            Entreprise entity = entrepriseOptional.get();
            entity.setName(entreprisedto.getName());
            entity.setAddress(entreprisedto.getAddress());
            entity.setUrl(entreprisedto.getUrl());
            entity.setPhoneNumber(entreprisedto.getPhoneNumber());
            entity.setEmail(entreprisedto.getEmail());
            Entreprise updatedEntity = Entreprise.toEntity(entreprisedto);
            repository.save(updatedEntity);
            return EntrepriseDto.toDto(updatedEntity);
    }*/
    @Transactional
    public EntrepriseDto updateEntreprise(long id, String name, String address, String phoneNumber, String email, String url) {
        // Recherchez l'entreprise par son ID
        Optional<Entreprise> optionalEntreprise = repository.findById(id);
        if (optionalEntreprise.isPresent()) {
            Entreprise existingEntreprise = optionalEntreprise.get();
            System.out.println("Avant la mise à jour : " + existingEntreprise.toString());
                existingEntreprise.setName(name);
                existingEntreprise.setAddress(address);
                existingEntreprise.setPhoneNumber(phoneNumber);
                existingEntreprise.setEmail(email);
                existingEntreprise.setUrl(url);
            existingEntreprise = repository.save(existingEntreprise);
            System.out.println("Après la mise à jour : " + existingEntreprise.toString());
            
            return modelMapper.map(existingEntreprise, EntrepriseDto.class);
        } else {
        	throw new FormationNotFoundException("Entreprise with id " + id + " not found");
            
        }
    }


    @Transactional
    public String removeEntreprise(long id) {
        Optional<Entreprise> entrepriseOptional = repository.findById(id);
        if (entrepriseOptional.isPresent()) {
            Entreprise entreprise = entrepriseOptional.get();
            System.out.printf("dgdgdgdgggg",entreprise);
            // Utilisez le service de calendrier pour supprimer les calendriers associés à l'entreprise
            calendrierService.deleteCalendriersByEntreprise(entreprise);

            repository.deleteById(id);
            return "Entreprise removed successfully";
        } else {
            return "Entreprise not found";
        }
    }
    public EntrepriseDto getEntrepriseById (Long id){
        Optional<Entreprise> optionalEntreprise = repository.findById(id);

        if (optionalEntreprise.isPresent()) {
            Entreprise entreprise = optionalEntreprise.get();
            return modelMapper.map(entreprise, EntrepriseDto.class);
        } else {
            throw new FormationNotFoundException("Entreprise  with id " + id + " not found");
        }
    }
}
