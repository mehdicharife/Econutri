package ensias.ma.gl.secondyear.twentyfour.econutri.web.rest.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ensias.ma.gl.secondyear.twentyfour.econutri.model.User;

@RestController
@RequestMapping("/users")
public class UserController {

    @GetMapping("/current")
    public ResponseEntity<?> getCurrentUser(Authentication auth) {
        User user = (User) auth.getPrincipal();
        var userDto = Map.of("id", user.getId(),
                             "email", user.getEmail()
        );
        return ResponseEntity.ok(userDto);
    } 
    
}
