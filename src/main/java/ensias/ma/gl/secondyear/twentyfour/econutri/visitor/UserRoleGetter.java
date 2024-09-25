package ensias.ma.gl.secondyear.twentyfour.econutri.visitor;

import ensias.ma.gl.secondyear.twentyfour.econutri.model.User;
import ensias.ma.gl.secondyear.twentyfour.econutri.model.Merchant;
import ensias.ma.gl.secondyear.twentyfour.econutri.model.Admin;

import org.springframework.stereotype.Component;


@Component
public final class UserRoleGetter implements UserVisitor {
    
    private ThreadLocal<String> cachedRole;

    public String getUserRole(User user) {
        user.accept(this);
        return cachedRole.get();
    }

    public void visitMerchant(Merchant merchant) {
        this.cachedRole.set("MERCHANT");
    }

    public void visitAdmin(Admin admin) {
        this.cachedRole.set("ADMIN");
    }
}
