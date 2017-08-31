package nixyteam;

import java.io.Serializable;

public class LocationBean implements Serializable {
	private static final long serialVersionUID = -5786138853111948272L;
	String date;
	String location;
	String state;
	String country;
	double latitude;
	double longitude;
	public String getDate() {
		return date;
	}
	public String getLocation() {
		return location;
	}
	public String getState() {
		return state;
	}
	public String getCountry() {
		return country;
	}
	public double getLatitude() {
		return latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public void setState(String state) {
		this.state = state;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
}
