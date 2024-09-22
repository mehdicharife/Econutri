package ensias.ma.gl.secondyear.twentyfour.econutri.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ensias.ma.gl.secondyear.twentyfour.econutri.model.Product;


public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByName(String name);
}
