package univ.iwa.model;

import jakarta.persistence.Entity; 
import jakarta.persistence.GeneratedValue; 
import jakarta.persistence.GenerationType; 
import jakarta.persistence.Id; 
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import univ.iwa.dto.EntrepriseDto;
import univ.iwa.dto.UserInfoDto;
@Builder
@Entity
@Data @AllArgsConstructor @NoArgsConstructor
public class UserInfo { 
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private int id; 
	private String name; 
	private String email; 
	private String password;
	private String roles;

	public static UserInfo toEntity(UserInfoDto user) {
		return UserInfo.builder()
				.id(user.getId())
				.name(user.getName())
				.password(user.getPassword())
				.roles(user.getRoles())
				.email(user.getEmail()).build();

	}
} 

