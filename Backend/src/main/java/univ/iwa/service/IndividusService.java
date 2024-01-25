package univ.iwa.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import univ.iwa.dto.FormationDto;
import univ.iwa.dto.IndividusDto;
import univ.iwa.model.Formation;
import univ.iwa.model.Individus;
import univ.iwa.repository.FormationRepository;
import univ.iwa.repository.IndividusRepository;

@Service
public class IndividusService {
    @Autowired
    IndividusRepository repository;
    @Autowired
    FormationRepository frepo;
    @Autowired
    ModelMapper modelMapper;

//    public IndividusDto inscription(IndividusDto individu, long formationId){
//        Formation formation = frepo.findById(formationId).get();
//        Individus individus = modelMapper.map(individu, Individus.class);
//        individus.setGroupe(formation);
//        return modelMapper.map(repository.save(individus), IndividusDto.class);
//    }
}
