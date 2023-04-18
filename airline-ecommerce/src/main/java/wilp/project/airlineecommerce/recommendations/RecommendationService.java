package wilp.project.airlineecommerce.recommendations;

import java.util.List;

public interface RecommendationService {

	Recommendation createRecommendation(Recommendation recommendation);

	Recommendation getRecommendationById(Long recommendationId);
    
    List<Recommendation> getRecommendationByCityPair(String origin, String destination);

    void deleteRecommendation(Long recommendationId);
    
    Seats addSeatsToFlight(Seats seats);

	Seats getSeatsInFlightById(String flightNo);
}
