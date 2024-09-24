package ensias.ma.gl.secondyear.twentyfour.econutri.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;


@Entity
@Table(name = "merchants")
public class Merchant extends User {

    private String ice;

    public String getIce() {
        return this.ice;
    }

    public void setIce(String ice) {
        this.ice = ice;
    }

}
