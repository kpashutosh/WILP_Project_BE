package wilp.project.airlineecommerce.recommendations;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RecommendationRepository extends JpaRepository<Recommendation, Long> {
	
	@Query(value = "select * from airline_database.flights where origin = ?1 and destination = ?2", nativeQuery = true)
	List<Recommendation> findRecommendation(String origin, String destination);
	
	@Query(value = "select count(flightNo) from airline_database.flights where airlineCode = ?1 and flightNo = ?2", nativeQuery = true)
	int findRecommendationByFlight(String airlineCode, String flightNo);
	
}