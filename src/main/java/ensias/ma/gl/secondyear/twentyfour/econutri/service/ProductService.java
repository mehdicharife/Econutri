package ensias.ma.gl.secondyear.twentyfour.econutri.service;

import java.util.Optional;

import org.springframework.stereotype.Component;

import ensias.ma.gl.secondyear.twentyfour.econutri.exception.ProductIdNotFoundException;
import ensias.ma.gl.secondyear.twentyfour.econutri.exception.ProductNameAlreadyExistsException;
import ensias.ma.gl.secondyear.twentyfour.econutri.exception.ProductNameNotFoundException;
import ensias.ma.gl.secondyear.twentyfour.econutri.model.Product;
import ensias.ma.gl.secondyear.twentyfour.econutri.repository.ProductRepository;

@Component
public class ProductService {

    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product getProductById(Long productId) throws ProductIdNotFoundException{
        Optional<Product> optionalProduct = this.productRepository.findById(productId);
        if(optionalProduct.isEmpty()) {
            throw new ProductIdNotFoundException(productId);
        }
        return optionalProduct.get();
    }

    public Product getProductByName(String productName) throws ProductNameNotFoundException {
        Optional<Product> optionalProduct = this.productRepository.findByName(productName);
        if(optionalProduct.isEmpty()) {
            throw new ProductNameNotFoundException(productName);
        }
        return optionalProduct.get();
    }


    public Product createProduct(String productName, String productImage) throws ProductNameAlreadyExistsException{
        Optional<Product> optionalProduct = this.productRepository.findByName(productName);

        if(optionalProduct.isPresent()) {
            throw new ProductNameAlreadyExistsException(productName);
        }
        
        return this.productRepository.save(new Product(productName, productImage));
    }
    
}
