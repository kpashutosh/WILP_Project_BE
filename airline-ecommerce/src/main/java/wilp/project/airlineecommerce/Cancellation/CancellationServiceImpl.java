package wilp.project.airlineecommerce.Cancellation;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import lombok.AllArgsConstructor;
import wilp.project.airlineecommerce.Confirmation.Confirmation;
import wilp.project.airlineecommerce.Confirmation.ConfirmationRepository;

@Service
@AllArgsConstructor
public class CancellationServiceImpl implements CancellationService{
	
	@Autowired
	private ConfirmationRepository confirmationRepository;
	
	@Autowired
	private CancellationRepository cancellationRepository;

	@Override
	public Cancellation cancelPNR(String pnr, String lastName) {
		Optional<Confirmation> optionalConfirmation = confirmationRepository.findById(pnr);
		Confirmation confirmation = optionalConfirmation.isPresent()? optionalConfirmation.get() : null;
		if(lastName.equalsIgnoreCase(confirmation.getLastName())) {
			Optional<Cancellation> optionalCancellation = cancellationRepository.findById(pnr);
			Cancellation cancellation = optionalCancellation.isPresent() ? optionalCancellation.get() : null;
			if(cancellation != null && cancellation.getCancellationDate() != null) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "PNR does not exist or invalid request");
			} else {
				cancellation = new Cancellation();
				cancellation.setCancellationDate(new Date());
				cancellation.setPnr(pnr);
				cancellationRepository.save(cancellation);
			}
			return cancellation;
		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "PNR does not exist or invalid request");
		}
	}

}
