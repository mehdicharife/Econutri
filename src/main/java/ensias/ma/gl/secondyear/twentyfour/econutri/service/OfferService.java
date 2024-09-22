package ensias.ma.gl.secondyear.twentyfour.econutri.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Component;

import ensias.ma.gl.secondyear.twentyfour.econutri.exception.OfferCreationException;
import ensias.ma.gl.secondyear.twentyfour.econutri.exception.ProductIdNotFoundException;
import ensias.ma.gl.secondyear.twentyfour.econutri.exception.ProductNameAlreadyExistsException;
import ensias.ma.gl.secondyear.twentyfour.econutri.exception.ProductNameNotFoundException;
import ensias.ma.gl.secondyear.twentyfour.econutri.exception.problem.OfferProblem;
import ensias.ma.gl.secondyear.twentyfour.econutri.exception.property.OfferProperty;
import ensias.ma.gl.secondyear.twentyfour.econutri.model.Merchant;
import ensias.ma.gl.secondyear.twentyfour.econutri.model.Offer;
import ensias.ma.gl.secondyear.twentyfour.econutri.model.Product;
import ensias.ma.gl.secondyear.twentyfour.econutri.model.User;
import ensias.ma.gl.secondyear.twentyfour.econutri.repository.OfferRepository;
import ensias.ma.gl.secondyear.twentyfour.econutri.request.OfferCreationRequest;



@Component
public class OfferService {
    
    private OfferRepository offerRepository;

    private ProductService productService;

    public OfferService(OfferRepository offerRepository, ProductService productService) {
        this.offerRepository = offerRepository;
        this.productService = productService;
    }

    
    @Transactional
    public Offer createOffer(User requester, OfferCreationRequest request) 
        throws OfferCreationException, 
               ProductNameAlreadyExistsException,
               ProductIdNotFoundException          
    {

        validateOfferCreationRequest(request);


        Product product;
        if(request.getProductId() != null) {
            product = this.productService.getProductById(request.getProductId());
        } else {
            product = this.productService.createProduct(request.getProductName(), request.getProductImage());
        }

        Offer offer = new Offer();
        offer.setProduct(product);
        offer.setUnitPrice(request.getUnitPrice());
        offer.setExpirationDate(request.getExpirationDate());
        offer.setAvailableQuantity(request.getAvailableQuantity());
        offer.setDescription(request.getDescription());
        offer.setPublisher((Merchant) requester);

        return this.offerRepository.save(offer);
    }



    private void validateOfferCreationRequest(OfferCreationRequest request) throws OfferCreationException {
        List<OfferProblem> offerProblems = new ArrayList<>();

        Date expirationDate = request.getExpirationDate();
        if(expirationDate == null) {
            offerProblems.add(OfferProblem.singlePropertyProblem(OfferProperty.expirationDate, "The expiration date is mandatory"));
        } else if(expirationDate.before(new Date())) {
            offerProblems.add(OfferProblem.singlePropertyProblem(OfferProperty.expirationDate, "The expiration date " + expirationDate + " has already passed"));
        }

        Long availableQuantity = request.getAvailableQuantity();
        if(availableQuantity == null) {
            offerProblems.add(OfferProblem.singlePropertyProblem(OfferProperty.expirationDate, "The available product quantity is required for offer publication"));
        } else if(availableQuantity <= 0L) {
            offerProblems.add(OfferProblem.singlePropertyProblem(OfferProperty.availableQuantity, "The available quantity should not be less than or equal to zero"));
        }

        Double unitPrice = request.getUnitPrice();
        if(unitPrice == null) {
            offerProblems.add(OfferProblem.singlePropertyProblem(OfferProperty.unitPrice, "The product unit price must be specified in order to publish the offer"));
        } else if(unitPrice < 0.0) {
            offerProblems.add(OfferProblem.singlePropertyProblem(OfferProperty.unitPrice, "The unit price must be bigger than or equal to zero."));
        }


        Long productId = request.getProductId();
        String productName = request.getProductName();
        if(productId == null && productName == null) {
            OfferProblem problem = new OfferProblem(
                List.of(OfferProperty.productId, OfferProperty.productName),
                "Either the product name or identifier must be provided."
            );
            offerProblems.add(problem);
        } else if(productId != null) {
            try {
                this.productService.getProductById(productId);
            } catch(ProductIdNotFoundException exception) {
                OfferProblem productIdProblem = OfferProblem.singlePropertyProblem(
                    OfferProperty.productId, 
                    exception.getFieldsErrors().get(OfferProperty.productId.name())
                );
                offerProblems.add(productIdProblem);
            }
        } else if(productName != null) {
            try {
                productService.getProductByName(productName);
                OfferProblem problem = OfferProblem.singlePropertyProblem(OfferProperty.productName, "The product name " + productName + " already exists");
                offerProblems.add(problem);
            } catch(ProductNameNotFoundException exception) { System.out.println(""); }
        }

        
        if(!offerProblems.isEmpty()) {
            throw new OfferCreationException(offerProblems);
        }

    }


}
