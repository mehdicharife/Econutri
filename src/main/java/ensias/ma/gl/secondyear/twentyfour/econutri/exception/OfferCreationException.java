package ensias.ma.gl.secondyear.twentyfour.econutri.exception;

import java.util.List;
import java.util.stream.Collectors;

import ensias.ma.gl.secondyear.twentyfour.econutri.exception.problem.OfferProblem;

public class OfferCreationException extends BadPropertiesException {
    
    public OfferCreationException(List<OfferProblem> offerProblems) {
        super(
            offerProblems.stream()
            .collect(Collectors.toMap(
                offerProblem -> offerProblem.getErroneousProperties().stream()
                    .map(property -> property.name())
                    .collect(Collectors.joining(","))
                ,

                OfferProblem::getDescription
            ))
        );
    }
}
