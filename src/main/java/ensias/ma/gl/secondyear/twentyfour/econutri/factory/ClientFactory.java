package ensias.ma.gl.secondyear.twentyfour.econutri.factory;

import ensias.ma.gl.secondyear.twentyfour.econutri.model.Client;
import ensias.ma.gl.secondyear.twentyfour.econutri.model.User;

public class ClientFactory extends UserFactory {
    
    public User createUser() {
        return new Client();
    }
}
