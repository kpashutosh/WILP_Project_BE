package wilp.project.airlineecommerce.Cart;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Traveller")
public class Traveller {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paxId;
    @Column(nullable = false)
    @JsonIgnore
    private String cartId;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
	@Column(nullable = false)
    private char gender;
    
	public Long getPaxId() {
		return paxId;
	}
	public void setPaxId(Long paxId) {
		this.paxId = paxId;
	}
	public String getCartId() {
		return cartId;
	}
	public void setCartId(String cartId) {
		this.cartId = cartId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public char getGender() {
		return gender;
	}
	public void setGender(char gender) {
		this.gender = gender;
	}
}
