package univ.iwa.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import univ.iwa.dto.IndividusDto;
import univ.iwa.model.Formation;
import univ.iwa.model.Individus;
import univ.iwa.repository.IndividusRepository;
import univ.iwa.repository.InscriptionRepository;

@Service
public class IndividusService {
    @Autowired
    IndividusRepository repository;
    @Autowired
    InscriptionRepository inscrepository;
    @Autowired
    ModelMapper modelMapper;

    public void inscription(IndividusDto individu, long formationId){
        Individus individusEntity = modelMapper.map(individu, Individus.class);

    }
}
