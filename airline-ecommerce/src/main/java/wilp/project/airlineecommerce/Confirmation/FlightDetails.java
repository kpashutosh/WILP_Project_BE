package wilp.project.airlineecommerce.Confirmation;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FlightDetails {
	
	@JsonProperty("origin")
    private String origin;
	@JsonProperty("destination")
    private String destination;
	@JsonProperty("departureTime")
    private String departureTime;
	@JsonProperty("arrivalTime")
    private String arrivalTime;
	@JsonProperty("flightNo")
    private String flightNo;
	@JsonProperty("airlineCode")
    private String airlineCode;
	@JsonProperty("date")
    private String date;
	
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
	public String getFlightNo() {
		return flightNo;
	}
	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}
	public String getAirlineCode() {
		return airlineCode;
	}
	public void setAirlineCode(String airlineCode) {
		this.airlineCode = airlineCode;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}

}
