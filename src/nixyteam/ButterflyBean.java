package nixyteam;
import java.io.Serializable;

public class ButterflyBean extends UserBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3893377565531412403L;
	private int sightID;
	private int tagID;
	private int userID;
	private String date;
	private String butterflyLocation;
	private String butterflyState;
	private String butterflyCountry;
	private double latitude;
	private double longitude;
	private String species;
	public int getSightID() {
		return sightID;
	}
	public int getTagID() {
		return tagID;
	}
	public int getUserID() {
		return userID;
	}
	public String getDate() {
		return date;
	}
	public String getButterflyLocation() {
		return butterflyLocation;
	}
	public String getButterflyState() {
		return butterflyState;
	}
	public String getButterflyCountry() {
		return butterflyCountry;
	}
	public double getLatitude() {
		return latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public String getSpecies() {
		return species;
	}
	public void setSightID(int sightID) {
		this.sightID = sightID;
	}
	public void setTagID(int tagID) {
		this.tagID = tagID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public void setButterflyLocation(String butterflyLocation) {
		this.butterflyLocation = butterflyLocation;
	}
	public void setButterflyState(String butterflyState) {
		this.butterflyState = butterflyState;
	}
	public void setButterflyCountry(String butterflyCountry) {
		this.butterflyCountry = butterflyCountry;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public void setSpecies(String species) {
		this.species = species;
	}
}
