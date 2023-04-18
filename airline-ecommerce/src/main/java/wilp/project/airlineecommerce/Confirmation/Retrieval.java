package wilp.project.airlineecommerce.Confirmation;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import wilp.project.airlineecommerce.Cart.Traveller;

public class Retrieval {
	
	@JsonProperty("pnr")
	private String pnr;
	@JsonProperty("dateCreated")
	private Date dateCreated;
	@JsonProperty("isPaymentDone")
	private boolean isPaymentDone;
	@JsonProperty("flightDetails")
	private FlightDetails flightDetails;
	@JsonProperty("bookingAmount")
	private String bookingAmount;
	@JsonProperty("travellerCount")
	private int travellerCount;
	@JsonProperty("passengerDetails")
	private List<Traveller> passengerDetails;
	@JsonProperty("paymentDetails")
	private PaymentDetails paymentDetails;
	
	@JsonIgnore
	private boolean pnrExists;
	
	public boolean isPnrExists() {
		return pnrExists;
	}
	public void setPnrExists(boolean pnrExists) {
		this.pnrExists = pnrExists;
	}
	public String getPnr() {
		return pnr;
	}
	public void setPnr(String pnr) {
		this.pnr = pnr;
	}
	public Date getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Date date) {
		this.dateCreated = date;
	}
	public boolean isPaymentDone() {
		return isPaymentDone;
	}
	public void setPaymentDone(boolean isPaymentDone) {
		this.isPaymentDone = isPaymentDone;
	}
	public FlightDetails getFlightDetails() {
		return flightDetails;
	}
	public void setFlightDetails(FlightDetails flightDetails) {
		this.flightDetails = flightDetails;
	}
	public String getBookingAmount() {
		return bookingAmount;
	}
	public void setBookingAmount(String bookingAmount) {
		this.bookingAmount = bookingAmount;
	}
	public int getTravellerCount() {
		return travellerCount;
	}
	public void setTravellerCount(int travellerCount) {
		this.travellerCount = travellerCount;
	}
	public List<Traveller> getPassengerDetails() {
		return passengerDetails;
	}
	public void setPassengerDetails(List<Traveller> passengerDetails) {
		this.passengerDetails = passengerDetails;
	}
	public PaymentDetails getPaymentDetails() {
		return paymentDetails;
	}
	public void setPaymentDetails(PaymentDetails paymentDetails) {
		this.paymentDetails = paymentDetails;
	}
	
}
