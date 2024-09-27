package ensias.ma.gl.secondyear.twentyfour.econutri.mapper;

import org.springframework.stereotype.Component;

import ensias.ma.gl.secondyear.twentyfour.econutri.model.User;
import ensias.ma.gl.secondyear.twentyfour.econutri.response.UserResponse;
import ensias.ma.gl.secondyear.twentyfour.econutri.visitor.UserRoleGetter;

@Component
public class UserMapper {
    
    private UserRoleGetter userRoleGetter;

    public UserMapper(UserRoleGetter userRoleGetter) {
        this.userRoleGetter = userRoleGetter;
    }

    public UserResponse toDto(User user) {

        UserResponse dto = new UserResponse();
        
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setRole(this.userRoleGetter.getUserRole(user));

        return dto;
    }
}
