package wilp.project.airlineecommerce.Confirmation;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import wilp.project.airlineecommerce.Cancellation.Cancellation;
import wilp.project.airlineecommerce.Cancellation.CancellationRepository;
import wilp.project.airlineecommerce.Cart.Cart;
import wilp.project.airlineecommerce.Cart.CartRepository;
import wilp.project.airlineecommerce.Cart.Traveller;
import wilp.project.airlineecommerce.Cart.TravellerRepository;
import wilp.project.airlineecommerce.recommendations.Recommendation;
import wilp.project.airlineecommerce.recommendations.RecommendationRepository;

@Service
@AllArgsConstructor
public class ConfirmationServiceImpl implements ConfirmationService{
	
	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private ConfirmationRepository confirmationRepository;
	
	@Autowired
	private PaymentRepository paymentRepository;
	
	@Autowired
	private RecommendationRepository recommendationRepository;
	
	@Autowired
	private TravellerRepository travellerRepository;
	
	@Autowired
	private CancellationRepository cancellationRepository;
	
	@Override
	public PaymentAmount getPaymentAmountByCartId(String cartId) {
		Optional<Cart> optionalCart = cartRepository.findById(cartId);
        Cart cart = optionalCart.isPresent() ? optionalCart.get() : null;
        PaymentAmount paymentAmount = new PaymentAmount();
        paymentAmount.setCartId(cartId);
        paymentAmount.setTotalAmount(cart.getTotalAmount());
        return paymentAmount;
	}
	
	@Override
	public Confirmation confirmPayment(Confirmation confirmation) {
	    String pnr = RandomStringUtils.random(6, true, true);
	    List<Confirmation> confirmationList = confirmationRepository.findIfPnrAlreadyExists(pnr, confirmation.getCartId());
	    if(!confirmationList.isEmpty() || confirmationList.size()>0) {
	    	confirmation.setPaymentSuccessful(false);
	    }
	    else {
	    	
	    	String cardLast4Digits = confirmation.getPaymentDetails().getCardNumber();
	    	cardLast4Digits = cardLast4Digits.substring(cardLast4Digits.length()-4, cardLast4Digits.length());
	    	confirmation.setPnr(pnr.toUpperCase());
	    	confirmation.setDateCreated(new Date());
	    	confirmation.setPaymentSuccessful(true);
	    	confirmation.setLastName(confirmationRepository.getLastNameByCart(confirmation.getCartId()));
	    	confirmation.getPaymentDetails().setPnr(pnr.toUpperCase());
	    	confirmation.getPaymentDetails().setCardNumber(cardLast4Digits);
	    	confirmationRepository.save(confirmation);
	    	paymentRepository.save(confirmation.getPaymentDetails());
	    }
		return confirmation;
	}

	@Override
	public Retrieval getPnrDetails(String pnr) {
		Retrieval retrieval = new Retrieval();
		//Retrieve Confirmation details
		Optional<Confirmation> optionalConfirmation = confirmationRepository.findById(pnr);
		Confirmation confirmation = optionalConfirmation.isPresent() ? optionalConfirmation.get() : null;
		
		//Check if PNR is already cancelled
		Optional<Cancellation> optionalCancellation = cancellationRepository.findById(pnr);
		Cancellation cancellation = optionalCancellation.isPresent() ? optionalCancellation.get() : null;
		
		//Retrieve cart details
		if(confirmation == null || cancellation != null) {
			retrieval.setPnrExists(false);
			return retrieval;
		}
		Optional<Cart> optionalCart = cartRepository.findById(confirmation.getCartId());
        Cart cart =  optionalCart.isPresent() ? optionalCart.get() : null;
        
        //Retrieve flight details
        Optional<Recommendation> optionalRecommendation = recommendationRepository.findById(cart.getRecommendationId());
        Recommendation recommendation = optionalRecommendation.isPresent() ? optionalRecommendation.get() : null;
        
        //Retrieve all travellers from cart
        List<Traveller> travellerList = travellerRepository.findTravellersInCart(confirmation.getCartId());
        
        //Retrieve payment information
        Optional<PaymentDetails> optionalPaymentDetails = paymentRepository.findById(pnr);
        PaymentDetails paymentDetails = optionalPaymentDetails.isPresent() ? optionalPaymentDetails.get() : null;
        
		if(confirmation != null && confirmation.getPnr() != null) {
			retrieval.setPnr(confirmation.getPnr());
			retrieval.setDateCreated(confirmation.getDateCreated());
			retrieval.setPaymentDone(confirmation.isPaymentSuccessful());
			retrieval.setFlightDetails(getFlightDetails(cart,recommendation));
			retrieval.setBookingAmount(cart.getTotalAmount());
			retrieval.setTravellerCount(cart.getTravellerCount());
			retrieval.setPassengerDetails(travellerList);
			retrieval.setPaymentDetails(paymentDetails);
			retrieval.setPnrExists(true);
		}
		else {
			retrieval.setPnrExists(false);
		}
		return retrieval;
	}

	private FlightDetails getFlightDetails(Cart cart, Recommendation recommendation) {
		FlightDetails flightDetails = new FlightDetails();
		flightDetails.setOrigin(recommendation.getOrigin());
		flightDetails.setDestination(recommendation.getDestination());
		flightDetails.setDepartureTime(recommendation.getDepartureTime());
		flightDetails.setArrivalTime(recommendation.getArrivalTime());
		flightDetails.setAirlineCode(recommendation.getAirlineCode());
		flightDetails.setFlightNo(recommendation.getFlightNo());
		flightDetails.setDate(cart.getDate());
		return flightDetails;
	}

}
