package wilp.project.airlineecommerce.recommendations;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RecommendationServiceImpl implements RecommendationService{

	@Autowired
	private RecommendationRepository recommendationRepository;

	@Autowired
	private SeatRepository seatRepository;


	@Override
	public List<Recommendation> getRecommendationByCityPair(String origin, String destination) {
		return recommendationRepository.findRecommendation(origin, destination);
	}

	@Override
	public Recommendation getRecommendationById(Long recommendationId) {
		Optional<Recommendation> optionalRecommendation = recommendationRepository.findById(recommendationId);
		return optionalRecommendation.isPresent() ? optionalRecommendation.get() : null;
	}

	@Override
	public Recommendation createRecommendation(Recommendation recommendation) {
		return recommendationRepository.save(recommendation);
	}

	@Override
	public void deleteRecommendation(Long recommendationId) {
		recommendationRepository.deleteById(recommendationId);
	}

	@Override
	public Seats addSeatsToFlight(Seats seats) {
		return seatRepository.save(seats);
	}

	@Override
	public Seats getSeatsInFlightById(String flightNo) {
		Optional<Seats> optionalSeats = seatRepository.findById(flightNo);
		return optionalSeats.isPresent() ? optionalSeats.get() : null;
	}

	public boolean isRecommendationAlreadyAdded(Recommendation recommendation) {
		if (recommendationRepository.findRecommendationByFlight(
				recommendation.getAirlineCode(), recommendation.getFlightNo()) >0){
			return true;
		}
		return false;

	}

}
