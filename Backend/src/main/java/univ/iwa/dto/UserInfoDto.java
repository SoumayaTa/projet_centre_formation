package univ.iwa.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import univ.iwa.model.UserInfo;
@Builder
@Data @AllArgsConstructor @NoArgsConstructor
public class UserInfoDto {
    private int id;
    private String name;
    private String email;
    private String password;
    private String roles;

    public static UserInfoDto toDto(UserInfo user){
        return UserInfoDto.builder()
            .id(user.getId())
            .name(user.getName())
            .email(user.getEmail())
            .password(user.getPassword())
            .roles(user.getRoles())
            .build();
    }
}
