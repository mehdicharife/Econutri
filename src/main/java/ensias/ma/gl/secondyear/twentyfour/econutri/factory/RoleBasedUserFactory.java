package ensias.ma.gl.secondyear.twentyfour.econutri.factory;

import java.util.Map;

import org.springframework.stereotype.Component;

import ensias.ma.gl.secondyear.twentyfour.econutri.exception.UnsupportedRoleException;
import ensias.ma.gl.secondyear.twentyfour.econutri.model.User;


@Component
public class RoleBasedUserFactory {


    private Map<String, UserFactory> roleFactoryMap = Map.of(
        "MERCHANT", new MerchantFactory(),
        "ADMIN", new AdminFactory(),
        "CLIENT", new ClientFactory()
    );

    public User createUser(String role) throws UnsupportedRoleException {
        UserFactory userFactory = roleFactoryMap.get(role);
        if(userFactory == null) {
            throw new UnsupportedRoleException(role);
        }

        return userFactory.createUser();
    }
    
}
