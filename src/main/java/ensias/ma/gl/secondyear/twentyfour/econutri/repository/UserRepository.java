package ensias.ma.gl.secondyear.twentyfour.econutri.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ensias.ma.gl.secondyear.twentyfour.econutri.model.User;


public interface UserRepository extends JpaRepository<User, Long>{
    Optional<User> findByEmail(String email);
}
