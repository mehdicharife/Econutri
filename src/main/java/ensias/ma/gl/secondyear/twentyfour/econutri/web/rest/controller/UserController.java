package ensias.ma.gl.secondyear.twentyfour.econutri.web.rest.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ensias.ma.gl.secondyear.twentyfour.econutri.mapper.UserMapper;
import ensias.ma.gl.secondyear.twentyfour.econutri.model.User;
import ensias.ma.gl.secondyear.twentyfour.econutri.request.UserCreationRequest;
import ensias.ma.gl.secondyear.twentyfour.econutri.response.UserResponse;
import ensias.ma.gl.secondyear.twentyfour.econutri.service.UserService;


@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    private UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping("/current")
    public ResponseEntity<?> getCurrentUser(Authentication auth) {
        User user = (User) auth.getPrincipal();
        var userDto = Map.of("id", user.getId(),
                             "email", user.getEmail()
        );
        return ResponseEntity.ok(userDto);
    } 

    
    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UserCreationRequest userRequest) throws Exception {
        User newUser = this.userService.createUser(userRequest);
        UserResponse dto = this.userMapper.toDto(newUser);
        return new ResponseEntity<> (dto, HttpStatus.CREATED);
    }


}
