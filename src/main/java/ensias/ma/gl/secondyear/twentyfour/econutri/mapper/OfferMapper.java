package ensias.ma.gl.secondyear.twentyfour.econutri.mapper;


import org.springframework.stereotype.Component;

import ensias.ma.gl.secondyear.twentyfour.econutri.model.Offer;
import ensias.ma.gl.secondyear.twentyfour.econutri.response.OfferResponse;


@Component
public class OfferMapper {

    public OfferResponse toDto(Offer offer) {
        OfferResponse dto = new OfferResponse();

        dto.setId(offer.getId());
        dto.setProduct(offer.getProduct());
        dto.setExpirationDate(offer.getExpirationDate());
        dto.setUnitPrice(offer.getUnitPrice());
        dto.setAvailableQuantity(offer.getAvailableQuantity());
        dto.setDescription(offer.getDescription());
        dto.setPublisherId(offer.getPublisher().getId());


        return dto;
    }
    
}
