package ensias.ma.gl.secondyear.twentyfour.econutri.mapper;


import org.springframework.stereotype.Component;

import ensias.ma.gl.secondyear.twentyfour.econutri.model.Offer;
import ensias.ma.gl.secondyear.twentyfour.econutri.response.OfferResponse;


@Component
public class OfferResponseMapper {


    public OfferResponse toDto(Offer offer) {
        OfferResponse dto = new OfferResponse();

        dto.setId(offer.getId());
        dto.setProductId(offer.getProduct().getId());
        dto.setPublisherId(offer.getPublisher().getId());
        dto.setExpirationDate(offer.getExpirationDate());
        dto.setAvailableQuantity(offer.getAvailableQuantity());
        dto.setDescription(offer.getDescription());

        return dto;
    }
    
}
