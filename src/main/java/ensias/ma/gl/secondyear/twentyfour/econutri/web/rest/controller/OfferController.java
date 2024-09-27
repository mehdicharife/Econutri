package ensias.ma.gl.secondyear.twentyfour.econutri.web.rest.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;

import ensias.ma.gl.secondyear.twentyfour.econutri.exception.NonMerchantUserException;
import ensias.ma.gl.secondyear.twentyfour.econutri.exception.OfferIdNotFoundException;
import ensias.ma.gl.secondyear.twentyfour.econutri.mapper.OfferResponseMapper;
import ensias.ma.gl.secondyear.twentyfour.econutri.model.Offer;
import ensias.ma.gl.secondyear.twentyfour.econutri.model.User;
import ensias.ma.gl.secondyear.twentyfour.econutri.request.OfferCreationRequest;
import ensias.ma.gl.secondyear.twentyfour.econutri.response.OfferResponse;
import ensias.ma.gl.secondyear.twentyfour.econutri.service.OfferService;


@RestController
@RequestMapping("/offers")
public class OfferController {
    
    private OfferService offerService;
    
    private OfferResponseMapper offerMapper;

    public OfferController(OfferService offerService, OfferResponseMapper offerMapper) {
        this.offerService = offerService;
        this.offerMapper = offerMapper;
    }


    @PostMapping
    public ResponseEntity<?> publishOffer(@RequestBody OfferCreationRequest offerCreationRequest,
            Authentication authentication) throws Exception {
        
        User user = (User) authentication.getPrincipal();

        try {
            Offer offer = this.offerService.createOffer(user, offerCreationRequest);
            OfferResponse offerDto = this.offerMapper.toDto(offer);
            return new ResponseEntity<>(offerDto, HttpStatus.CREATED);

        } catch(NonMerchantUserException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
        }
    }


    @GetMapping
    public ResponseEntity<List<OfferResponse>> getAllOffers() {

        var offers = this.offerService.getAllOffers()
            .stream()
            .map(offer -> this.offerMapper.toDto(offer))
            .toList();

        return ResponseEntity.ok(offers);
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getOfferById(@PathVariable("id") Long offerId) {
        try {
            Offer offer = this.offerService.getOfferById(offerId);
            return ResponseEntity.ok(this.offerMapper.toDto(offer));
            
        } catch(OfferIdNotFoundException exception) {
            return ResponseEntity.notFound().build();
        }
    }
}
