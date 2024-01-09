package univ.iwa.service;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails; 
import org.springframework.security.core.userdetails.UserDetailsService; 
import org.springframework.security.core.userdetails.UsernameNotFoundException; 
import org.springframework.security.crypto.password.PasswordEncoder; 
import org.springframework.stereotype.Service;

import univ.iwa.model.Formation;
import univ.iwa.model.UserInfo;
import univ.iwa.model.UserInfoDetails;
import univ.iwa.repository.FormationRepository;
import univ.iwa.repository.UserInfoRepository;

import java.util.Date;
import java.util.Optional;

@Service
public class UserInfoService implements UserDetailsService { 
	@Autowired UserInfoRepository repository;
	@Autowired
	FormationRepository formationRepo;
	@Autowired PasswordEncoder encoder; 
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { 
		Optional<UserInfo> userDetail = repository.findByName(username); 

		return userDetail.map(UserInfoDetails::new) 
				.orElseThrow(() -> new UsernameNotFoundException("User not found " + username)); 
	} 
	public String addUser(UserInfo format) {
		format.setName(format.getName());
		format.setPassword(encoder.encode(format.getPassword()));
		format.setEmail(format.getEmail());
		format.setRoles("ROLE_FORMAT");
		repository.save(format);
		return "User Added Successfully"; 
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

} 
