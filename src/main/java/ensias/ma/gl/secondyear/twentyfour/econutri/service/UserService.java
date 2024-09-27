package ensias.ma.gl.secondyear.twentyfour.econutri.service;

import java.util.Optional;
import java.util.Map;
import java.util.HashMap;

import org.springframework.stereotype.Component;
import org.springframework.security.crypto.password.PasswordEncoder;

import ensias.ma.gl.secondyear.twentyfour.econutri.exception.EmailNotFoundException;
import ensias.ma.gl.secondyear.twentyfour.econutri.exception.EmailTakenException;
import ensias.ma.gl.secondyear.twentyfour.econutri.exception.IncorrectPasswordException;
import ensias.ma.gl.secondyear.twentyfour.econutri.exception.UnsupportedRoleException;
import ensias.ma.gl.secondyear.twentyfour.econutri.exception.UserCreationException;
import ensias.ma.gl.secondyear.twentyfour.econutri.factory.RoleBasedUserFactory;
import ensias.ma.gl.secondyear.twentyfour.econutri.model.User;
import ensias.ma.gl.secondyear.twentyfour.econutri.repository.UserRepository;
import ensias.ma.gl.secondyear.twentyfour.econutri.request.UserCreationRequest;


@Component
public class UserService {
    
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private RoleBasedUserFactory roleBasedUserFactory;

    
    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       RoleBasedUserFactory roleBasedUserFactory) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleBasedUserFactory = roleBasedUserFactory;

    }


    
    public User createUser(UserCreationRequest userRequest) throws UserCreationException {
        User user = this.validateUserCreationRequest(userRequest);   

        user.setEmail(userRequest.getEmail());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));

        return this.userRepository.save(user);
    }

    // The password of newUser should be unencoded
    public User createUser(User newUser) throws EmailTakenException{
        try {
            this.findUserByEmail(newUser.getEmail());
            throw new EmailTakenException(newUser.getEmail());

        } catch(EmailNotFoundException exception) {
            newUser.setId(null);
            newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
            return this.userRepository.save(newUser);
        }
    }
    

    public User findUserByEmail(String email) throws EmailNotFoundException {
        Optional<User> optionalUser = this.userRepository.findByEmail(email);
        if(optionalUser.isEmpty()) {
            throw new EmailNotFoundException(email);
        }

        return optionalUser.get(); 
    }


    public User findUserByEmailAndPassword(String email, String password) 
        throws EmailNotFoundException, IncorrectPasswordException {

        Optional<User> optionalUser = this.userRepository.findByEmail(email);
        if(optionalUser.isEmpty()) {
            throw new EmailNotFoundException(email);
        }

        User user = optionalUser.get();
        if(!passwordEncoder.matches(password, user.getPassword())) {
            throw new IncorrectPasswordException(password);
        } 

        return user;
    }


    // TODOs: 1.role based request validation & populating the validated created user
    //       2. use the repository instead of member functions to minimise the try-catch dance
    private User validateUserCreationRequest(UserCreationRequest userRequest) throws UserCreationException {
        String email = userRequest.getEmail();
        String role = userRequest.getRole();
        Map<String ,String> errors = new HashMap<>();

        User user = null;
        try {
            this.findUserByEmail(email);
            errors.put("email", "Email taken");
        } catch(EmailNotFoundException ex) { }

        try {
            user = this.roleBasedUserFactory.createUser(role);
        } catch(UnsupportedRoleException ex) {
            errors.put("role", "There is no role with the name " + role);
        }
        
        if(!errors.isEmpty()) {
            throw new UserCreationException(errors);
        }
        
        return user;
    }
}
