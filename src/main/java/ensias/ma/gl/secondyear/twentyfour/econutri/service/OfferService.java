package ensias.ma.gl.secondyear.twentyfour.econutri.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Component;

import ensias.ma.gl.secondyear.twentyfour.econutri.exception.NonMerchantUserException;
import ensias.ma.gl.secondyear.twentyfour.econutri.exception.OfferCreationException;
import ensias.ma.gl.secondyear.twentyfour.econutri.exception.OfferIdNotFoundException;
import ensias.ma.gl.secondyear.twentyfour.econutri.exception.ProductIdNotFoundException;
import ensias.ma.gl.secondyear.twentyfour.econutri.exception.ProductNameNotFoundException;
import ensias.ma.gl.secondyear.twentyfour.econutri.exception.problem.OfferProblem;
import ensias.ma.gl.secondyear.twentyfour.econutri.exception.property.OfferProperty;
import ensias.ma.gl.secondyear.twentyfour.econutri.model.Merchant;
import ensias.ma.gl.secondyear.twentyfour.econutri.model.Offer;
import ensias.ma.gl.secondyear.twentyfour.econutri.model.Product;
import ensias.ma.gl.secondyear.twentyfour.econutri.model.User;
import ensias.ma.gl.secondyear.twentyfour.econutri.repository.OfferRepository;
import ensias.ma.gl.secondyear.twentyfour.econutri.request.OfferCreationRequest;
import ensias.ma.gl.secondyear.twentyfour.econutri.visitor.UserRoleGetter;



@Component
public class OfferService {
    
    private OfferRepository offerRepository;

    private ProductService productService;

    private UserRoleGetter userRoleGetter;


    public OfferService(OfferRepository offerRepository,
                        ProductService productService,
                        UserRoleGetter userRoleGetter) {

        this.offerRepository = offerRepository;
        this.productService = productService;
        this.userRoleGetter = userRoleGetter;
    }



    @Transactional
    public Offer createOffer(User requester, OfferCreationRequest request) 
        throws OfferCreationException, NonMerchantUserException    {

        if(!"MERCHANT".equals(userRoleGetter.getUserRole(requester))) {
            throw new NonMerchantUserException(requester);
        }
        
        Product product = validateOfferCreationRequest(request);

        Offer offer = new Offer();
        offer.setProduct(product);
        offer.setPublisher((Merchant) requester);
        offer.setUnitPrice(request.getUnitPrice());
        offer.setExpirationDate(request.getExpirationDate());
        offer.setAvailableQuantity(request.getAvailableQuantity());
        offer.setDescription(request.getDescription());
        
        return this.offerRepository.save(offer);
    }


    public List<Offer> getAllOffers() {
        return this.offerRepository.findAll();
    }

    public Offer getOfferById(Long offerId) throws OfferIdNotFoundException {
        Optional<Offer> optionalOffer = this.offerRepository.findById(offerId);
        if(optionalOffer.isEmpty()) {
            throw new OfferIdNotFoundException(offerId);
        }

        return optionalOffer.get();
    }


    private Product validateOfferCreationRequest(OfferCreationRequest request) throws OfferCreationException {
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
        Product product = null;
        if(productId == null && productName == null) {

            OfferProblem problem = new OfferProblem(
                List.of(OfferProperty.productId, OfferProperty.productName),
                "Either the product name or identifier must be provided."
            );
            offerProblems.add(problem);
        } else if(productId != null) {
            try {
                product = this.productService.getProductById(productId);
            } catch(ProductIdNotFoundException exception) {
                OfferProblem productIdProblem = OfferProblem.singlePropertyProblem(
                    OfferProperty.productId, 
                    exception.getFieldsErrors().get(OfferProperty.productId.name())
                );
                offerProblems.add(productIdProblem);
            }
        } else if(productName != null) {
            try {
                product = productService.getProductByName(productName);
                OfferProblem problem = OfferProblem.singlePropertyProblem(OfferProperty.productName, "The product name " + productName + " already exists");
                offerProblems.add(problem);
            } catch(ProductNameNotFoundException exception) { 
                product = new Product(productName, productName);
             }
        }

        
        if(!offerProblems.isEmpty()) {
            throw new OfferCreationException(offerProblems);
        }

        return product;

    }


}
