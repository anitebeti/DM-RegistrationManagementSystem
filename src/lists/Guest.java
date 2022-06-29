package lists;
import java.io.Serializable;

public class Guest implements Serializable{
	
	private String lastName;
	private String firstName;
	private String email;
	private String phoneNumber;
	
	private final static long serialVersionUID = 1L;
	
//Constructor
	
	public Guest(String lastName, String firstName, String email, String phoneNumber) {
		this.lastName = lastName;
		this.firstName = firstName;
		this.email = email;
		this.phoneNumber = phoneNumber;
	}
	
//getter, setter
	
	public String getLastName() {
		return this.lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getFirstName() {
		return this.firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPhoneNumber() {
		return this.phoneNumber;
	}
	
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
//Override toString
	
	@Override
	public String toString() {
		return "Nume: " + this.lastName + " " + this.firstName + ", Email: " + this.email + ", Telefon: " + this.phoneNumber;
	}
	
//equals	
	
	public boolean equalsByName(String guestName) {
		String fullName = this.lastName + " " + this.firstName;
		if (fullName.equalsIgnoreCase(guestName)) {
			return true;
		}
		return false;
	}
	
	public boolean equalsByEmail(String guestEmail) {
		if (this.email.equalsIgnoreCase(guestEmail)) {
			return true;
		}
		return false;
	}
	
	public boolean equalsByPhone(String guestPhone) {
		if (this.phoneNumber.equalsIgnoreCase(guestPhone)) {
			return true;
		}
		return false;
	}

}
