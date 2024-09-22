package ensias.ma.gl.secondyear.twentyfour.econutri.exception.problem;

import java.util.ArrayList;
import java.util.List;

import ensias.ma.gl.secondyear.twentyfour.econutri.exception.property.OfferProperty;


public class OfferProblem {

    private List<OfferProperty> erroneousProperties = new ArrayList<>();

    private String description;


    public OfferProblem() { }

    private OfferProblem(OfferProperty offerProperty, String description) {
       this.erroneousProperties.add(offerProperty);
       this.description = description; 
    }

    public OfferProblem(List<OfferProperty> properties, String description) {
        this.erroneousProperties = properties;
        this.description = description;
    }

    public static final OfferProblem singlePropertyProblem(OfferProperty property, String description) {
        return new OfferProblem(property, description);
    }


    public List<OfferProperty> getErroneousProperties() {
        return this.erroneousProperties;
    }

    public void setErroneousProperties(List<OfferProperty> erroneousProperties) {
        this.erroneousProperties = erroneousProperties;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
  
}
