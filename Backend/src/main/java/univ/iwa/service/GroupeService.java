package univ.iwa.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import univ.iwa.dto.GroupeDto;
import univ.iwa.dto.IndividusDto;
import univ.iwa.exception.FormationNotFoundException;
import univ.iwa.model.Groupe;
import univ.iwa.model.Individus;
import univ.iwa.repository.GroupeRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GroupeService {
    @Autowired
    GroupeRepository repository;
    @Autowired
    ModelMapper modelMapper;

    @Autowired
    EmailServiceImpl emailservice;
    @Autowired
    CryptUrlService crypt;

    public List<GroupeDto> getAllGroupes() {
        List<Groupe> groupes = repository.findAll();
        return groupes.stream()
                .map(groupe -> modelMapper.map(groupe, GroupeDto.class))
                .collect(Collectors.toList());
    }

    public List<IndividusDto> getInscriptionForGroupe(Long groupeId) {
        Optional<Groupe> groupeOptional = repository.findById(groupeId);
        if (groupeOptional.isPresent()) {
            Groupe groupe = groupeOptional.get();
            List<IndividusDto> individusDtoList = groupe.getInscrits()
                    .stream()
                    .map(individus -> modelMapper.map(individus, IndividusDto.class))
                    .collect(Collectors.toList());
            return individusDtoList;
        } else {
            throw new FormationNotFoundException("Inscrit avec l'ID " + groupeId + " introuvable");
        }
    }


    public void sendEmailsToInscrits(Long groupeId) {
        Optional<Groupe> groupeOptional = repository.findById(groupeId);
        if (groupeOptional.isPresent()) {
            Groupe groupe = groupeOptional.get();
            List<Individus> individus = groupe.getInscrits();

            for (Individus individu : individus) {
                String to = individu.getEmail();
                String subject ="FeedBack";
                String encryptedId = crypt.encryptId(individu.getId());
                String body = "http://localhost:4200/evaluation?id="+encryptedId;
                emailservice.sendMail(to, "tayoubsoumaya21@gmail.com", subject, body);
            }
        } else {
            throw new FormationNotFoundException("Inscrit avec l'ID " + groupeId + " introuvable");
        }
    }


}
