package wilp.project.airlineecommerce.Cancellation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("airline-ecommerce/cancellation")
public class CancellationController {
	
	@Autowired
	private CancellationServiceImpl cancellationService;
	
	//Cancel PNR
	@PostMapping
	public ResponseEntity<String> cancelPNR(@RequestParam String pnr, @RequestParam String lastName){
		cancellationService.cancelPNR(pnr, lastName);
		return new ResponseEntity<>("PNR cancelled successfully" , HttpStatus.ACCEPTED);
	}
	
	@ExceptionHandler(Exception.class)
	@ResponseStatus()
	public ResponseEntity<String> handleNoSuchElementFoundException(
			Exception exception
			) {
		return ResponseEntity
				.status(HttpStatus.BAD_REQUEST)
				.body("PNR does not exist or invalid request");
	}

}
