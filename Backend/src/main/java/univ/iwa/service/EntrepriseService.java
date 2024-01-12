package univ.iwa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import univ.iwa.dto.EntrepriseDto;
import univ.iwa.model.Entreprise;
import univ.iwa.repository.EntrepriseRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EntrepriseService {
    @Autowired
    EntrepriseRepository repository;

    public List<EntrepriseDto> getAllEntreprise() {
        List<Entreprise> entreprises = repository.findAll();
        return entreprises.stream()
                .map(EntrepriseDto::toDto)
                .collect(Collectors.toList());
    }
    public EntrepriseDto addNewEntreprise(EntrepriseDto entreprise){
        entreprise.setName(entreprise.getName());
        entreprise.setAddress(entreprise.getAddress());
        entreprise.setUrl(entreprise.getUrl());
        entreprise.setPhoneNumber(entreprise.getPhoneNumber());
        entreprise.setEmail(entreprise.getEmail());
        return EntrepriseDto.toDto(repository.save(Entreprise.toEntity(entreprise)));
    }

    public EntrepriseDto updateEntreprise(long id, EntrepriseDto entreprisedto) {
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
    }


    public String removeEntreprise(long id) {
        Optional<Entreprise> entrepriseOptional = repository.findById(id);

        if (entrepriseOptional.isPresent()) {
            repository.deleteById(id);
            return "Entreprise removed successfully";
        } else {
            return "Entreprise not found";
        }
    }
}
