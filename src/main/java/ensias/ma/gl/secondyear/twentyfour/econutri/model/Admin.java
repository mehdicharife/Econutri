package ensias.ma.gl.secondyear.twentyfour.econutri.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import ensias.ma.gl.secondyear.twentyfour.econutri.visitor.UserVisitor;


@Entity
@Table(name = "admins")
public class Admin extends User{
    
    private String secondaryPassword;

    
    public Admin() {

    }

    public Admin(String secondaryPassword) {
        this.secondaryPassword = secondaryPassword;
    }


    public String getSecondaryPassword() {
        return this.secondaryPassword;
    }

    public void setSecondaryPassword(String secondaryPassword) {
        this.secondaryPassword = secondaryPassword;
    }


    public void accept(UserVisitor userVisitor) {
        userVisitor.visitAdmin(this);
    }

}
