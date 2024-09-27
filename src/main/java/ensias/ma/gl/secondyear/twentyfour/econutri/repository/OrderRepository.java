package ensias.ma.gl.secondyear.twentyfour.econutri.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ensias.ma.gl.secondyear.twentyfour.econutri.model.Order;


public interface OrderRepository extends JpaRepository<Order, Long>{
    
}
