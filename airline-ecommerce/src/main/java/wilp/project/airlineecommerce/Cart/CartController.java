package wilp.project.airlineecommerce.Cart;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("airline-ecommerce/cart")
public class CartController {
	
	@Autowired
	private CartServiceImpl cartService;
	
	//Create Cart
	@PostMapping
	public ResponseEntity<String> createCart(@RequestBody Cart cart){
		cartService.createCart(cart);
		return new ResponseEntity<>("Cart Id: " +cart.getCartId(), HttpStatus.CREATED);
	}
	
	//Get Recommendation by recommendation Id
	@GetMapping("{cartId}")
	public ResponseEntity<Cart> getCartById(@PathVariable("cartId") String cartId){
		Cart cart = cartService.getCartById(cartId);
		return new ResponseEntity<>(cart, HttpStatus.OK);
	}
	
	@PostMapping("{cartId}/passengerDetails")
	public ResponseEntity<String> addPassenger(
			@PathVariable("cartId") String cartId, @RequestBody List<Traveller> travellers){
		if(travellers == null || travellers.size() != cartService.getCartById(cartId).getTravellerCount()) {
			return new ResponseEntity<>("Passenger count not correct", HttpStatus.BAD_REQUEST);
		}
		else if(!cartService.getPassengersFromCart(cartId).isEmpty()) {
			return new ResponseEntity<>("Passenger already added", HttpStatus.METHOD_NOT_ALLOWED);
		}
		cartService.addPassengers(travellers, cartId);
		return new ResponseEntity<>("Passenger details added successsfully", HttpStatus.CREATED);
	}
	
	@ExceptionHandler(Exception.class)
	@ResponseStatus()
	public ResponseEntity<String> handleNoSuchElementFoundException(
			Exception exception
			) {
		return ResponseEntity
				.status(HttpStatus.BAD_REQUEST)
				.body("Unable to create/process cart. Please check your request and try again");
	}
}
