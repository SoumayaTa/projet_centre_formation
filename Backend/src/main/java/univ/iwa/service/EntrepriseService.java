package univ.iwa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import univ.iwa.model.Entreprise;
import univ.iwa.repository.EntrepriseRepository;

import java.util.List;
import java.util.Optional;

@Service
public class EntrepriseService {
    @Autowired
    EntrepriseRepository repository;

    public List<Entreprise> getAllEntreprise(){
        return repository.findAll();
    }
    public String addNewEntreprise(Entreprise entreprise){
        entreprise.setName(entreprise.getName());
        entreprise.setAddress(entreprise.getAddress());
        entreprise.setUrl(entreprise.getUrl());
        entreprise.setPhoneNumber(entreprise.getPhoneNumber());
        entreprise.setEmail(entreprise.getEmail());
        repository.save(entreprise);
        return "entreprise added successfuly";
    }

    public String updateEntreprise(long id, Entreprise updatedEntreprise) {
        Optional<Entreprise> existingEntrepriseOptional = repository.findById(id);

        if (existingEntrepriseOptional.isPresent()) {
            Entreprise existingEntreprise = existingEntrepriseOptional.get();
            existingEntreprise.setName(updatedEntreprise.getName());
            existingEntreprise.setAddress(updatedEntreprise.getAddress());
            existingEntreprise.setUrl(updatedEntreprise.getUrl());
            existingEntreprise.setPhoneNumber(updatedEntreprise.getPhoneNumber());
            existingEntreprise.setEmail(updatedEntreprise.getEmail());
            repository.save(existingEntreprise);
            return "Entreprise updated successfully";
        } else {
            return "Entreprise not found";
        }
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
