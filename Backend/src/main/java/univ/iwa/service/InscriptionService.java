package univ.iwa.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import univ.iwa.dto.InscriptionDto;
import univ.iwa.model.Inscription;
import univ.iwa.model.UserInfo;
import univ.iwa.repository.InscriptionRepository;
import univ.iwa.repository.UserInfoRepository;

import java.util.Date;

@Service
public class InscriptionService {
    @Autowired
    InscriptionRepository repo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    UserInfoRepository repository;
    @Autowired
    PasswordEncoder encoder;

    public InscriptionDto inscrireFormateurExterne(String name, String email,String  mots_cles){
        Inscription entity = modelMapper.map(new InscriptionDto(name,email,mots_cles), Inscription.class);
        entity.setStatus(false);
        entity.setDate(new Date());
        return modelMapper.map(repo.save(entity), InscriptionDto.class);
    }
    public boolean acceptFormateur(Long inscriptionId) {
        Inscription inscription = repo.findById(inscriptionId).orElse(null);
        if (inscription != null && !inscription.isStatus()) {
            repo.delete(inscription);
            UserInfo userInfo = new UserInfo();
            userInfo.setName(inscription.getName());
            userInfo.setEmail(inscription.getEmail());
            userInfo.setMots_cles(inscription.getMots_cles());
            userInfo.setRoles("ROLE_FORMAT");
            userInfo.setPassword(encoder.encode("1234"));
            repository.save(userInfo);
            return true;
        }
        return false;
    }

}
