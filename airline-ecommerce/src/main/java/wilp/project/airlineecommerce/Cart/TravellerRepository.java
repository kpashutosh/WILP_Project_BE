package wilp.project.airlineecommerce.Cart;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TravellerRepository extends JpaRepository<Traveller, Long>{
	
	@Query(value = "select * from airline_database.traveller where cart_id = ?1", nativeQuery = true)
	List<Traveller> findTravellersInCart(String cartId);

}
