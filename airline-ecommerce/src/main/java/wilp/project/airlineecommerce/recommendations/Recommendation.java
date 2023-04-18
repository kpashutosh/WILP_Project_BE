package wilp.project.airlineecommerce.recommendations;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "flights")
public class Recommendation {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recommendationId;
    @Column(nullable = false)
    private String origin;
    @Column(nullable = false)
    private String destination;
    @Column(nullable = false)
    private String departureTime;
    @Column(nullable = false)
    private String arrivalTime;
    @Column(nullable = false)
    private String flightNo;
    @Column(nullable = false)
    private String airlineCode;
    @Column(nullable = false)
    private String amountPerPax;
	@Transient
    private String date;
    
	public Long getRecommendationId() {
		return recommendationId;
	}
	public void setRecommendationId(Long recommendationId) {
		this.recommendationId = recommendationId;
	}
	public String getDepartureTime() {
		return departureTime;
	}
	public void setDepartureTime(String departureTime) {
		this.departureTime = departureTime;
	}
	public String getArrivalTime() {
		return arrivalTime;
	}
	public void setArrivalTime(String arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getAirlineCode() {
		return airlineCode;
	}
	public void setAirlineCode(String airlineCode) {
		this.airlineCode = airlineCode;
	}
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public String getFlightNo() {
		return flightNo;
	}
	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}
	
    public String getAmountPerPax() {
		return amountPerPax;
	}
	public void setAmountPerPax(String amountPerPax) {
		this.amountPerPax = amountPerPax;
	}

}
