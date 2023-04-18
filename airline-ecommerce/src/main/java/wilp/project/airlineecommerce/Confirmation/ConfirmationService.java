package wilp.project.airlineecommerce.Confirmation;

public interface ConfirmationService {

	PaymentAmount getPaymentAmountByCartId(String cartId);

	Confirmation confirmPayment(Confirmation confirmation);

	Retrieval getPnrDetails(String pnr);

}
