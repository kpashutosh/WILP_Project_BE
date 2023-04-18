package wilp.project.airlineecommerce.Confirmation;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "Confirmation")
public class Confirmation {

	@Id
	private String pnr;
    @Column(nullable = false)
    private String cartId;
    @Column(nullable = false)
    private Date dateCreated;
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false)
    private boolean isPaymentSuccessful;
	@Transient
    private PaymentDetails paymentDetails;
	
	public String getPnr() {
		return pnr;
	}
	public void setPnr(String pnr) {
		this.pnr = pnr;
	}
	public String getCartId() {
		return cartId;
	}
	public void setCartId(String cartId) {
		this.cartId = cartId;
	}
	public Date getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	public PaymentDetails getPaymentDetails() {
		return paymentDetails;
	}
	public void setPaymentDetails(PaymentDetails paymentDetails) {
		this.paymentDetails = paymentDetails;
	}
    public boolean isPaymentSuccessful() {
		return isPaymentSuccessful;
	}
	public void setPaymentSuccessful(boolean isPaymentSuccessful) {
		this.isPaymentSuccessful = isPaymentSuccessful;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}    
    
}
