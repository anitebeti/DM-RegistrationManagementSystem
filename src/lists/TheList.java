package lists;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.Serializable;

public class TheList implements Serializable{
	
	private ArrayList<Guest> submitted = new ArrayList<Guest>();
	private Guest guest;
	
	private final static long serialVersionUID = 1L;
	
//get Guest
	
	public Guest getGuest(int index) {
		return this.submitted.get(index);
	}
	
//cauta si returneaza index	
	
	protected int findIndex(String lastName, String firstName, String email, String phoneNumber) {
		
		try {																						//IndexOutOfBoundsException
			for (int i = 0; i < this.submitted.size(); i++) {
				
				this.guest = this.submitted.get(i);
				String fullName = lastName + " " + firstName;
				
				if (this.guest.equalsByName(fullName) || this.guest.equalsByEmail(email) || this.guest.equalsByPhone(phoneNumber)) {
					return i;
				}
			}
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Error: a fost accesata o pozitie inexistenta!");
		} 
		return Integer.MAX_VALUE;
		
	}

//add
	
	public void add (String lastName, String firstName, String email, String phoneNumber) {
		this.guest = new Guest(lastName, firstName, email, phoneNumber);
		this.submitted.add(guest);
	}
	
	public void add (Guest guest) {
		try {																						//NullPointerException
			this.submitted.add(guest);
			System.out.println("[" + guest.getLastName() + " " + guest.getFirstName() + "] " 
					+ "Felicitari! Locul tau la eveniment este confirmat. Te asteptam!");
		} catch (NullPointerException e) {
			System.out.println("Error: persoana pe care incerci sa o introduci nu exista!");
		}
		
	}
	
//afisare lista
	
	public void guests() {																			//IndexOutOfBoundsException
		try {
			for (int i = 0; i < this.submitted.size(); i++) {
				this.guest = this.submitted.get(i);
				System.out.println((i + 1) + ". " + this.guest.toString());
			}	
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Error: a fost accesata o pozitie inexistenta!");
		} 
		
	}
	
//afisare nr de persoane	
	
	public int guestsNo() {
		return submitted.size();
	}
	
//remove
	
	public void remove(int index) {
		try {																						//IndexOutOfBoundException
			this.submitted.remove(index);
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Error: a fost accesata o pozitie inexistenta!");
		} 
		
	}
	
//update - continuarea meniului pentru update	
	
	public boolean update(int index, int n, Scanner sc) {											//IndexOutOfBoundsException
		try {
			this.guest = this.getGuest(index);
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Error: a fost accesata o pozitie inexistenta!");
		} 
		
		
		switch (n) {
		case 1:
			System.out.println("Introduceti numele de familie: ");
			this.guest.setLastName(sc.next());
			return true;
			
		case 2:
			System.out.println("Introduceti prenumele: ");
			this.guest.setFirstName(sc.next());
			return true;
			
		case 3:
			System.out.println("Introduceti email: ");
			this.guest.setEmail(sc.next());
			return true;
			
		case 4:
			System.out.println("Introduceti numar de telefon: ");
			this.guest.setPhoneNumber(sc.next());
			return true;
		}
		
		return false;
	}
	
//search	
	
	public ArrayList<String> search(String str) {
		str.toLowerCase();
		ArrayList<String> result = new ArrayList<String>();
		
		try {																						//IndexOutOfBoundsException
			for (int i = 0; i < this.submitted.size(); i++) {
				this.guest = this.submitted.get(i);
				
				if (this.guest.getLastName().toLowerCase().contains(str)) {
					result.add("Contact " + (i+1) + ": contine lastName = " + this.guest.getLastName());
				}
				
				if (this.guest.getFirstName().toLowerCase().contains(str)) {
					result.add("Contact " + (i+1) + ": contine firstName = " + this.guest.getFirstName());
					
				} 
				
				if (this.guest.getEmail().toLowerCase().contains(str)) {
					result.add("Contact " + (i+1) + ": contine email = " + this.guest.getEmail());
					
				}
				
				if (this.guest.getPhoneNumber().toLowerCase().contains(str)) {
					result.add("Contact " + (i+1) + ": contine phoneNumber = " + this.guest.getPhoneNumber());
				}
			}
			
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Error: a fost accesata o pozitie inexistenta!");
		} 
		
		
		return result;
	}
	
	
	

}
