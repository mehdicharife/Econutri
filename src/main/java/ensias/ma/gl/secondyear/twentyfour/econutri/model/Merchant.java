package ensias.ma.gl.secondyear.twentyfour.econutri.model;

import jakarta.persistence.Entity;



@Entity
public class Merchant extends User {

    private String ice;

    public String getIce() {
        return this.ice;
    }

    public void setIce(String ice) {
        this.ice = ice;
    }

}
