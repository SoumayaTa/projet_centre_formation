package univ.iwa.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import univ.iwa.model.UserInfo;
@Builder
@Data @AllArgsConstructor @NoArgsConstructor
public class UserInfoDto {
    private  int id;
    private String name;
    private String email;
    private String password;
    private String mots_cles;
}
