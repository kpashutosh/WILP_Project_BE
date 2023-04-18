package wilp.project.airlineecommerce.Confirmation;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfirmationRepository extends JpaRepository<Confirmation, String>{
	
	@Query(value = "select * from airline_database.confirmation where pnr = ?1 or cart_id = ?2", nativeQuery = true)
	List<Confirmation> findIfPnrAlreadyExists(String pnr, String cartId);
	
	@Query(value = "select last_name from airline_database.traveller where cart_id = ?1 limit 1", nativeQuery = true)
	String getLastNameByCart(String cartId);

}
