package ensias.ma.gl.secondyear.twentyfour.econutri.factory;

import org.springframework.stereotype.Component;

import ensias.ma.gl.secondyear.twentyfour.econutri.model.Merchant;


@Component
public class MerchantFactory extends UserFactory {
    
    public Merchant createUser() {
        return new Merchant();
    }
}
