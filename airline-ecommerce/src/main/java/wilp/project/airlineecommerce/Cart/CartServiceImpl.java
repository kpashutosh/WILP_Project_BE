package wilp.project.airlineecommerce.Cart;

import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import lombok.AllArgsConstructor;
import wilp.project.airlineecommerce.recommendations.Recommendation;
import wilp.project.airlineecommerce.recommendations.RecommendationServiceImpl;
import wilp.project.airlineecommerce.recommendations.SeatRepository;
import wilp.project.airlineecommerce.recommendations.Seats;

@Service
@AllArgsConstructor
public class CartServiceImpl implements CartService{
	
	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private RecommendationServiceImpl recommendationService;
	
	@Autowired
	private TravellerRepository travellerRepository;
	
	@Autowired
	private SeatRepository seatRepository;

	@Override
	public Cart createCart(Cart cart) {
		String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";
		Pattern pattern = Pattern.compile(emailRegex);
		if(cart != null && cart.getRecommendationId() != null &&
				cart.getTravellerCount() > 0 && cart.getPhoneNumber().length() > 9 &&
				pattern.matcher(cart.getEmailId()).matches()) {
			LocalDate searchDate = LocalDate.parse(cart.getDate());
			LocalDate currentDate = LocalDate.now();
			int noOfDaysBetween = (int) ChronoUnit.DAYS.between(currentDate, searchDate) + 1;
			Recommendation r = recommendationService.getRecommendationById(cart.getRecommendationId());
			String flight = r.getAirlineCode().concat(r.getFlightNo());
			Seats seatForSelectedFlight = recommendationService.getSeatsInFlightById(flight);
			int travellersInCart = cart.getTravellerCount();
			Method method;
			int seatsLeft = 0;
			try {
				method = Seats.class.getMethod("getDay" + noOfDaysBetween);
				seatsLeft = (int) method.invoke(seatForSelectedFlight);
				if(seatsLeft >= travellersInCart) {
					//Update seats left for a flight for a date
					seatRepository.deleteById(flight);
					method = Seats.class.getMethod("setDay" + noOfDaysBetween, int.class);
					method.invoke(seatForSelectedFlight, seatsLeft - travellersInCart);
					seatRepository.save(seatForSelectedFlight);
					int totalAmount = travellersInCart * Integer.parseInt(r.getAmountPerPax());
					cart.setTotalAmount(String.valueOf(totalAmount));
					String cartId = RandomStringUtils.random(64, true, true);
					cart.setCartId(cartId);
					return cartRepository.save(cart);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Please check your request");
	}

	@Override
	public List<Traveller> addPassengers(List<Traveller> travellers, String cartId) {
		if(cartId != null && cartRepository.findById(cartId) != null) {
			for(Traveller traveller: travellers) {
				traveller.setCartId(cartId);
				travellerRepository.save(traveller);
			}
			return travellers;
		}
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Please check your request");
	}
	
	@Override
	public List<Traveller> getPassengersFromCart(String cartId) {
		return travellerRepository.findTravellersInCart(cartId);
	}

	@Override
	public Cart getCartById(String cartId) {
		Optional<Cart> optionalCart = cartRepository.findById(cartId);
        Cart cart =  optionalCart.isPresent() ? optionalCart.get() : null;
        cart.setTravellers(getPassengersFromCart(cartId));
		return cart;
	}

}
