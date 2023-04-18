package wilp.project.airlineecommerce.Confirmation;

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
import org.springframework.web.server.ResponseStatusException;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("airline-ecommerce/confirmation")
public class ConfirmationController {
	
	@Autowired
	private ConfirmationServiceImpl confirmationService;
	
	//Get Recommendation by recommendation Id
	@GetMapping("payment/{cartId}")
	public ResponseEntity<PaymentAmount> getPaymentAmountByCart(@PathVariable("cartId") String cartId){
		PaymentAmount paymentAmount = confirmationService.getPaymentAmountByCartId(cartId);
		return new ResponseEntity<>(paymentAmount, HttpStatus.OK);
	}
	
	//Confirm Payment
	@PostMapping
	public ResponseEntity<String> confirmPayment(@RequestBody Confirmation confirmation){
		confirmationService.confirmPayment(confirmation);
		if(!confirmation.isPaymentSuccessful()) {
			return new ResponseEntity<>("Payment not successful. Please try again later.", HttpStatus.PAYMENT_REQUIRED);
		}
		return new ResponseEntity<>("Payment confirmed, PNR: " +confirmation.getPnr(), HttpStatus.CREATED);
	}
	
	//Retrieve PNR
	@GetMapping("{pnr}")
	public ResponseEntity<Retrieval> getPnrDetails(@PathVariable("pnr") String pnr){
		Retrieval retrieval = confirmationService.getPnrDetails(pnr);
		if(retrieval !=null && !retrieval.isPnrExists()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No recommendation found! Please check your request");
		}
		return new ResponseEntity<>(retrieval, HttpStatus.OK);
	}
	
	@ExceptionHandler(Exception.class)
	@ResponseStatus()
	public ResponseEntity<String> handleNoSuchElementFoundException(
			Exception exception
			) {
		return ResponseEntity
				.status(HttpStatus.BAD_REQUEST)
				.body("PNR not found! Please check your request");
	}

}
