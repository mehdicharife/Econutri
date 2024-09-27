package ensias.ma.gl.secondyear.twentyfour.econutri.factory;

import org.springframework.stereotype.Component;

import ensias.ma.gl.secondyear.twentyfour.econutri.model.Admin;

@Component
public class AdminFactory extends UserFactory {
    
    public Admin createUser() {
        return new Admin();
    }
}
