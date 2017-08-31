package nixyteam;

import java.io.Serializable;

public class UserBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4334366756912769470L;
	private int userID;
	private String email;
	private String firstName;
	private String lastName;
	private String address;
	private String userCity;
	private String userState;
	private String zipCode;
	private String userCountry;
	private String phone;
	private boolean administrator;
	private boolean disabled;
	public int getUserID() {
		return userID;
	}
	public String getEmail() {
		return email;
	}
	public String getFirstName() {
		return firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public String getAddress() {
		return address;
	}
	public String getUserCity() {
		return userCity;
	}
	public String getUserState() {
		return userState;
	}
	public String getZipCode() {
		return zipCode;
	}
	public String getUserCountry() {
		return userCountry;
	}
	public String getPhone() {
		return phone;
	}
	public boolean isAdministrator() {
		return administrator;
	}
	public boolean isDisabled() {
		return disabled;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public void setUserCity(String userCity) {
		this.userCity = userCity;
	}
	public void setUserState(String userState) {
		this.userState = userState;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public void setUserCountry(String userCountry) {
		this.userCountry = userCountry;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public void setAdministrator(boolean administrator) {
		this.administrator = administrator;
	}
	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}
}
