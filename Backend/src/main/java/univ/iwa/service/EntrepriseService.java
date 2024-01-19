package univ.iwa.service;

import org.modelmapper.ModelMapper;
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
    public EntrepriseDto updateEntreprise(long id, EntrepriseDto entrepriseDto) {
        Entreprise entity = repository.findById(id).get();
        modelMapper.map(entrepriseDto, entity);
        return modelMapper.map(repository.save(entity), EntrepriseDto.class);

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
