package ensias.ma.gl.secondyear.twentyfour.econutri.visitor;

import ensias.ma.gl.secondyear.twentyfour.econutri.model.Merchant;
import ensias.ma.gl.secondyear.twentyfour.econutri.model.Admin;
import ensias.ma.gl.secondyear.twentyfour.econutri.model.Client;


public interface UserVisitor {
    
    void visitMerchant(Merchant merchant);

    void visitAdmin(Admin admin);

    void visitClient(Client client);
    
}
