package ensias.ma.gl.secondyear.twentyfour.econutri.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

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

}
