package wilp.project.airlineecommerce.Cart;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "Cart")
public class Cart {

	@Id
    private String cartId;
    @Column(nullable = false)
    private Long recommendationId;
    @Column(nullable = false)
    private int travellerCount;
    @Column(nullable = false)
    private String phoneNumber;
    @Column(nullable = false)
    private String emailId;
    @Column(nullable = false)
    private String date;
    @Column(nullable = false)
    private String totalAmount;
	@Transient
    private List<Traveller> travellers;
    
	public List<Traveller> getTravellers() {
		return travellers;
	}
	public void setTravellers(List<Traveller> travellers) {
		this.travellers = travellers;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getCartId() {
		return cartId;
	}
	public void setCartId(String cartId) {
		this.cartId = cartId;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public Long getRecommendationId() {
		return recommendationId;
	}
	public void setRecommendationId(Long recommendationId) {
		this.recommendationId = recommendationId;
	}
	public int getTravellerCount() {
		return travellerCount;
	}
	public void setTravellerCount(int travellerCount) {
		this.travellerCount = travellerCount;
	}
    public String getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}
}
