package ensias.ma.gl.secondyear.twentyfour.econutri.web.rest.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ensias.ma.gl.secondyear.twentyfour.econutri.model.Offer;
import ensias.ma.gl.secondyear.twentyfour.econutri.request.OfferCreationRequest;
import ensias.ma.gl.secondyear.twentyfour.econutri.service.OfferService;


@RestController
@RequestMapping("/offers")
public class OfferController {
    
    private OfferService offerService;

    public OfferController(OfferService offerService) {
        this.offerService = offerService;
    }


    @PostMapping
    public ResponseEntity<?> publishOffer(@RequestBody OfferCreationRequest offerCreationRequest) throws Exception {
        Offer offer = this.offerService.createOffer(null, offerCreationRequest);
        return new ResponseEntity<>(offer, HttpStatus.CREATED);
    }
}
