package ensias.ma.gl.secondyear.twentyfour.econutri.service;

import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.security.crypto.password.PasswordEncoder;

import ensias.ma.gl.secondyear.twentyfour.econutri.exception.EmailNotFoundException;
import ensias.ma.gl.secondyear.twentyfour.econutri.exception.EmailTakenException;
import ensias.ma.gl.secondyear.twentyfour.econutri.exception.IncorrectPasswordException;
import ensias.ma.gl.secondyear.twentyfour.econutri.model.User;
import ensias.ma.gl.secondyear.twentyfour.econutri.repository.UserRepository;


@Component
public class UserService {
    
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    // The password of newUser should be unencoded
    public User createUser(User newUser) throws EmailTakenException{
        try {
            User user =  this.findUserByEmail(newUser.getEmail());
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
}
