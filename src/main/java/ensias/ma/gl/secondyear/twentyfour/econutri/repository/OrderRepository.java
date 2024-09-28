package ensias.ma.gl.secondyear.twentyfour.econutri.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ensias.ma.gl.secondyear.twentyfour.econutri.model.Order;


public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByOffer_Publisher_Id(Long publisherId);
    List<Order> findByOrderer_Id(Long ordererId);
}
