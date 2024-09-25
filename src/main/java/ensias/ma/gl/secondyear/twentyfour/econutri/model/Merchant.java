package ensias.ma.gl.secondyear.twentyfour.econutri.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import ensias.ma.gl.secondyear.twentyfour.econutri.visitor.UserVisitor;


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

    public void accept(UserVisitor userVisitor) {
        userVisitor.visitMerchant(this);
    }

}
