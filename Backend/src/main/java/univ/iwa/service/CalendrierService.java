package univ.iwa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import univ.iwa.model.Calendrier;
import univ.iwa.model.Entreprise;
import univ.iwa.model.Formation;
import univ.iwa.model.UserInfo;
import univ.iwa.repository.CalendrierRepository;
import univ.iwa.repository.EntrepriseRepository;
import univ.iwa.repository.FormationRepository;
import univ.iwa.repository.UserInfoRepository;

import java.util.Optional;

@Service
public class CalendrierService {
    @Autowired
    CalendrierRepository repository;
    @Autowired
    FormationRepository formationRepository;
    @Autowired
    UserInfoRepository userInfoRepository;
    @Autowired
    EntrepriseRepository entrepriseRepository;

    public String addCalendrier(Calendrier calendrier, Long formationId, int formateurId,
                                Long entrepriseId){
        calendrier.setDatedebut(calendrier.getDatedebut());
        calendrier.setDatefin(calendrier.getDatefin());
        Optional<Formation> formation = formationRepository.findById(formationId);
        Optional<UserInfo> formateur = userInfoRepository.findById(formateurId);
        Optional<Entreprise> entreprise = entrepriseRepository.findById(entrepriseId);
        calendrier.setFormation(formation.get());
        calendrier.setFormateur(formateur.get());
        calendrier.setEntreprise(entreprise.get());
        repository.save(calendrier);
        return "calendar added successfully";
    }
}
