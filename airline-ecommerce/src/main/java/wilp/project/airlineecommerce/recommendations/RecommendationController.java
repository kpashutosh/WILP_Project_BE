package wilp.project.airlineecommerce.recommendations;

import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("airline-ecommerce/recommendations")
public class RecommendationController {

	@Autowired
	private RecommendationServiceImpl recommendationService;

	//Add recommendation
	@PostMapping
	public ResponseEntity<String> createRecommendation(@RequestBody Recommendation recommendation){
		recommendationService.createRecommendation(recommendation);
		return new ResponseEntity<>("success", HttpStatus.CREATED);
	}

	//Get Recommendation by origin and destination city pair
	@GetMapping
	public ResponseEntity<List<Recommendation>> getRecommendationByCityPair(
			@RequestParam String origin, @RequestParam String destination, 
			@RequestParam String date, @RequestParam int seats){
		List<Recommendation> recommendations = recommendationService.
				getRecommendationByCityPair(origin, destination);
		LocalDate searchDate = LocalDate.parse(date);
		LocalDate currentDate = LocalDate.now();
		int noOfDaysBetween = (int) ChronoUnit.DAYS.between(currentDate, searchDate) + 1;
		List<Recommendation> finalRecommendations = new ArrayList<>();
		for(Recommendation recommendation: recommendations) {
			Seats seatForSelectedFlight = recommendationService.getSeatsInFlightById(
					recommendation.getAirlineCode().concat(recommendation.getFlightNo()));
			Method method;
			int seatsLeft = 0;
			try {
				method = Seats.class.getMethod("getDay" + noOfDaysBetween);
				seatsLeft = (int) method.invoke(seatForSelectedFlight);
				if(seatsLeft > seats) {
					recommendation.setDate(date);
					finalRecommendations.add(recommendation);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if(CollectionUtils.isEmpty(finalRecommendations)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No recommendation found! Please check your request");
		}
		return new ResponseEntity<>(finalRecommendations, HttpStatus.OK);
	}

	//Get Recommendation by recommendation Id
	@GetMapping("{id}")
	public ResponseEntity<Recommendation> getRecommendationById(
			@PathVariable("id") Long recommendationId, @RequestParam(required = false) String date){
		Recommendation recommendation = recommendationService.
				getRecommendationById(recommendationId);
		recommendation.setDate(date);
		return new ResponseEntity<>(recommendation, HttpStatus.OK);
	}

	// Delete Recommendation
	@DeleteMapping("{id}")
	public ResponseEntity<String> deleteUser(@PathVariable("id") Long recommendationId){
		recommendationService.deleteRecommendation(recommendationId);
		return new ResponseEntity<>("Recommendation successfully deleted!", HttpStatus.OK);
	}
	
	//Add no of seats left in flight
	@PostMapping("add-seats")
	public ResponseEntity<String> addSeatsToFlight(@RequestBody Seats seats){
		recommendationService.addSeatsToFlight(seats);
		return new ResponseEntity<>("success", HttpStatus.CREATED);
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus()
	public ResponseEntity<String> handleNoSuchElementFoundException(
			Exception exception
			) {
		return ResponseEntity
				.status(HttpStatus.BAD_REQUEST)
				.body("No recommendation found! Please check your request");
	}
}
