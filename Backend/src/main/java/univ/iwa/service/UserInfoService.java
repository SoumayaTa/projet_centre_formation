package univ.iwa.service;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails; 
import org.springframework.security.core.userdetails.UserDetailsService; 
import org.springframework.security.core.userdetails.UsernameNotFoundException; 
import org.springframework.security.crypto.password.PasswordEncoder; 
import org.springframework.stereotype.Service;

import univ.iwa.dto.UserInfoDto;
import univ.iwa.model.Formation;
import univ.iwa.model.UserInfo;
import univ.iwa.model.UserInfoDetails;
import univ.iwa.repository.FormationRepository;
import univ.iwa.repository.UserInfoRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserInfoService implements UserDetailsService {
	@Autowired UserInfoRepository repository;
	@Autowired
	FormationRepository formationRepo;
	@Autowired PasswordEncoder encoder;
	@Autowired
	private ModelMapper modelMapper;


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<UserInfo> userDetail = repository.findByName(username);

		return userDetail.map(UserInfoDetails::new)
				.orElseThrow(() -> new UsernameNotFoundException("User not found " + username));
	}

	/*public String addUser(UserInfoDto format) {
		format.setName(format.getName());
		format.setPassword(encoder.encode(format.getPassword()));
		format.setEmail(format.getEmail());
		format.setRoles("ROLE_FORMAT");
		repository.save(format);
		return "User Added Successfully";
	}*/
	public String addUser(UserInfoDto userInfoDto) {
		UserInfo userInfo = modelMapper.map(userInfoDto, UserInfo.class);
		userInfo.setPassword(encoder.encode(userInfo.getPassword()));
		userInfo.setRoles("ROLE_FORMAT");
		repository.save(userInfo);
		return "User Added Successfully";
	}

	public String addAssisstant(UserInfoDto userInfoDto) {
		UserInfo userInfo = modelMapper.map(userInfoDto, UserInfo.class);
		userInfo.setPassword(encoder.encode(userInfo.getPassword()));
		userInfo.setRoles("ROLE_ASSISTANT");
		repository.save(userInfo);
		return "User Added Successfully";
	}


	public UserInfoDto updateUser(UserInfoDto userinfodto, int id) {
		Optional<UserInfo> optionalUser = repository.findById(id);
		UserInfo entity = optionalUser.orElseThrow(() -> new RuntimeException("User not found with id: " + id));
		String oldPassword = entity.getPassword();
		modelMapper.map(userinfodto, entity);
		if (userinfodto.getPassword() != null && !userinfodto.getPassword().isEmpty()) {
			entity.setPassword(encoder.encode(userinfodto.getPassword()));
		} else {
			entity.setPassword(oldPassword);
		}
		return modelMapper.map(repository.save(entity), UserInfoDto.class);
	}

	@PostConstruct
	public String adminDefault(){
		UserInfo admin = new UserInfo();
		admin.setId(1);
		admin.setName("admin");
		admin.setRoles("ROLE_ADMIN");
		admin.setPassword(encoder.encode("adminadmin"));
		repository.save(admin);
		return "admin added successfully";
	}

	@PostConstruct
	public String assistantDefault(){
		UserInfo assistant = new UserInfo();
		assistant.setId(2);
		assistant.setName("assistant1");
		assistant.setPassword(encoder.encode("assistant1"));
		assistant.setRoles("ROLE_ASSISTANT");
		repository.save(assistant);
		return "assistant added successfully";
	}


	public void deleteFormateur(Long id) {
		repository.deleteById(id.intValue());
	}

	public List<UserInfoDto> getAllFormateurs() {
      List<UserInfo> formateurs = repository.findByRoles("ROLE_FORMAT");
		return formateurs.stream()
				.map(user -> modelMapper.map(user, UserInfoDto.class))
				.collect(Collectors.toList());
	}
	public UserInfoDto getFormateurById(int id) {
        Optional<UserInfo> formateurOptional = repository.findById(id);

        if (formateurOptional.isPresent()) {
            UserInfo formateur = formateurOptional.get();
            return modelMapper.map(formateur, UserInfoDto.class);
        } else {
            throw new RuntimeException("Formateur not found with id: " + id);
        }
    }


	public UserInfoDto updateFormateur(UserInfoDto formateurDto, int id) {
		Optional<UserInfo> optionalFormateur = repository.findById(id);
		UserInfo formateurEntity = optionalFormateur.orElseThrow(() -> new RuntimeException("Formateur not found with id: " + id));

		String oldPassword = formateurEntity.getPassword();

		modelMapper.map(formateurDto, formateurEntity);

		if (formateurDto.getPassword() != null && !formateurDto.getPassword().isEmpty()) {
			formateurEntity.setPassword(encoder.encode(formateurDto.getPassword()));
		} else {
			formateurEntity.setPassword(oldPassword);
		}

		return modelMapper.map(repository.save(formateurEntity), UserInfoDto.class);
	}

	public UserInfoDto findUserByName(String username) {
		UserInfo user = repository.findByName(username).orElseThrow(() -> new UsernameNotFoundException("User not found " + username));
		return modelMapper.map(user, UserInfoDto.class);
	}
}
