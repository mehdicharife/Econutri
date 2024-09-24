package ensias.ma.gl.secondyear.twentyfour.econutri.loader;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import ensias.ma.gl.secondyear.twentyfour.econutri.exception.EmailTakenException;
import ensias.ma.gl.secondyear.twentyfour.econutri.model.Admin;
import ensias.ma.gl.secondyear.twentyfour.econutri.service.UserService;


@Component
public final class AdminLoader {
    
    private UserService userService;


    public AdminLoader(@Value("${adminEmail}") String adminEmail,
                       @Value("${adminPassword}") String adminPassword,
                       UserService userService) {

        this.userService = userService;
        loadAdmin(adminEmail, adminPassword);
    }


    // To consider: check if an admin exists instead of checking whether
    // a user with the email <email> exists since the admin can potentially 
    // change their email
    private void loadAdmin(String email, String password) {
        try {
            Admin admin = new Admin();
            admin.setEmail(email);
            admin.setPassword(password);

            this.userService.createUser(admin);

        } catch(EmailTakenException exception) {
            // nothing to do; admin already loaded
        }
    }


}
