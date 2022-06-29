package lists;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;


public class GuestsList implements Serializable{

	private TheList submitted = new TheList();
	private TheList waitlist = new TheList();
	private int maxParticipants = 2;
	
//indexList - sa retina indecsi in cele 2 liste (submitted si waitlist)
	
	private ArrayList<Integer> indexList = new ArrayList<Integer>(2);
	
	private final static long serialVersionUID = 2L;
	
//save si restore dintr-un singur fisier - serializare	
	public void saveLists() throws IOException{
		try (ObjectOutputStream binaryFileOut = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream("savedLists\\lists.dat")))) {
			binaryFileOut.writeObject(this.submitted);
			binaryFileOut.writeObject(this.waitlist);
		}
	}
	
	public void restoreLists() throws IOException {
		try (ObjectInputStream binaryFileIn = new ObjectInputStream(new BufferedInputStream(new FileInputStream("savedLists\\lists.dat")))) {
			this.submitted = (TheList) binaryFileIn.readObject();
			this.waitlist = (TheList) binaryFileIn.readObject();
		} catch (ClassNotFoundException e) {
			System.out.println("Class not found: " + e.getMessage());
		}
	}
		
//getter si setter pt maxParticipants
	
	public int getMaxParticipants() {
		return this.maxParticipants;
	}
	
	public void setMaxParticipants(int maxParticipants) {
		this.maxParticipants = maxParticipants;
	}
	
//help
	
	public void printMainMenu() {
		System.out.println("help - Afiseaza aceasta lista de comenzi\n"
				+ "add - Adauga o noua persoana (inscriere)\n"
				+ "check - Verifica daca o persoana este inscrisa la eveniment\n"
				+ "remove - Sterge o persoana existenta din lista\n"
				+ "update - Actualizeaza detaliile unei persoane\n"
				+ "guests - Lista de persoane care participa la eveniment\n"
				+ "waitlist - Persoanele din lista de asteptare\n"
				+ "available - Numarul de locuri libere\n"
				+ "guests_no - Numarul de persoane care participa la eveniment\n"
				+ "waitlist_no - Numarul de persoane din lista de asteptare\n"
				+ "subscribe_no - Numarul total de persoane inscrise\n"
				+ "search - Cauta toti invitatii conform sirului de caractere introdus\n"
				+ "quit - Inchide aplicatia");
	}
	
	
//add	
	
	public int add(String lastName, String firstName, String email, String phoneNumber) {
		
		if (this.available() <= 0) {
			this.waitlist.add(lastName, firstName, email, phoneNumber);
			
			System.out.println("[" + lastName + " " + firstName + "] "
					+ "Te-ai inscris cu succes in lista de asteptare si ai primit numarul de ordine "
					+ this.waitlistNo() + ". Te vom notifica daca un loc devine disponibil.");			
			
			return this.waitlistNo();
		}
		
		if (this.submitted.guestsNo() == 0) {
			this.submitted.add(lastName, firstName, email, phoneNumber);
			
			System.out.println("[" + lastName + " " + firstName + "] " 
					+ "Felicitari! Locul tau la eveniment este confirmat. Te asteptam!");
			return 0;
		}
		
		try {																										//IndexOutOfBoundsException
			if (this.submitted.findIndex(lastName, firstName, email, phoneNumber) != Integer.MAX_VALUE) {
				System.out.println("Persoana se afla deja in lista de participanti.");
				return -1;
			}
			
			if (this.waitlist.findIndex(lastName, firstName, email, phoneNumber) != Integer.MAX_VALUE) {
				System.out.println("Persoana se afla deja in lista de asteptare.");
				return -1;
			}
			
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Error: a fost accesata o pozitie inexistenta!");
		}
		
		
		
		if (this.submitted.guestsNo() == this.maxParticipants) {
			this.waitlist.add(lastName, firstName, email, phoneNumber);
			
			System.out.println("[" + lastName + " " + firstName + "] "
					+ "Te-ai inscris cu succes in lista de asteptare si ai primit numarul de ordine "
					+ this.waitlistNo() + ". Te vom notifica daca un loc devine disponibil.");
			
			return this.waitlistNo();
		}
		
		this.submitted.add(lastName, firstName, email, phoneNumber);
		
		System.out.println("[" + lastName + " " + firstName + "] " 
				+ "Felicitari! Locul tau la eveniment este confirmat. Te asteptam!");
		return 0;
	}
	
//check
	
	public boolean check(Scanner sc) {
		System.out.print("Introdu numele de familie: ");
		String lastName = sc.next();
		
		System.out.print("Introduceti prenumele: ");
		String firstName = sc.next();
		
		if (this.submitted.findIndex(lastName, firstName, null, null) != Integer.MAX_VALUE) {
			System.out.println("Persoana se afla in lista de participanti.");
			return true;
			
		} else if (this.waitlist.findIndex(lastName, firstName, null, null) != Integer.MAX_VALUE) {
			System.out.println("Persoana se afla in lista de asteptare.");
			return true;
		}
		
		System.out.println("Persoana nu este inscrisa la eveniment.");
		return false;
	}
	
//afisare liste 	
	
	public void guests() {
		if (this.guestsNo() == 0) {
			System.out.println("Niciun participant inscris...");
		}
		this.submitted.guests();
	}
	
	public void waitlist() {
		if (this.waitlistNo() == 0) {
			System.out.println("Lista de asteptare este goala...");
		}
		this.waitlist.guests();
	}
	
//afisare nr de persoane 
	
	public int guestsNo() {
		return this.submitted.guestsNo();
	}
	
	public int waitlistNo() {
		return this.waitlist.guestsNo();
	}
	
	public int subscribeNo() {
		return this.guestsNo() + this.waitlistNo();
	}
	
	public int available() {
		return this.maxParticipants - this.guestsNo();
	}
	
//meniu de autentificare, retine indecsii in ArrayList-ul indexList
	
	private void chooseMenu(Scanner sc) {

		int index1;
		int index2;
		
		if (this.indexList.size() == 0) {
			this.indexList.add(Integer.MAX_VALUE);
			this.indexList.add(Integer.MAX_VALUE);
		}
		
		System.out.println("Alege modul de autentificare, tastand: "
				+ "\n\t\"1\" - Nume si prenume "
				+ "\n\t\"2\" - Email "
				+ "\n\t\"3\" - Numar de telefon (format \"+40733386463\")");
		
		boolean isNumber = true;
		while (isNumber) {
			try {																							//InputMismatchException
				switch (sc.nextInt()) {
				
					case 1:
						isNumber = false;
						System.out.print("Introduceti numele de familie: ");
						String lastName = sc.next();
						
						System.out.println("Introduceti prenumele: ");
						String firstName = sc.next();
						
						index1 = this.submitted.findIndex(lastName, firstName, null, null);
						index2 = this.waitlist.findIndex(lastName, firstName, null, null);
						
						this.indexList.set(0, index1);										
						this.indexList.set(1, index2);
						
						break;
					
					case 2:
						isNumber = false;
						System.out.print("Introduceti email: ");
						String email = sc.next();
						
						index1 = this.submitted.findIndex(null, null, email, null);
						index2 = this.waitlist.findIndex(null, null, email, null);
						
						this.indexList.set(0, index1);
						this.indexList.set(1, index2);
						
						break;
						
					case 3:
						isNumber = false;
						System.out.print("Introduceti numar de telefon: ");
						String phoneNumber = sc.next();
						
						index1 = this.submitted.findIndex(null, null, null, phoneNumber);
						index2 = this.waitlist.findIndex(null, null, null, phoneNumber);
						
						this.indexList.set(0, index1);
						this.indexList.set(1, index2);
						
						break;
				}
				
			} catch (InputMismatchException e) {
				sc.nextLine();
				System.out.println("Error: Nu ai introdus un numar. Mai incearca");
			}
		}
		
	}

//remove - foloseste meniu de autentificare
	
	public boolean remove(Scanner sc) {
		
		System.out.println("Se sterge o persoana existenta din lista...");
		
		this.chooseMenu(sc);
		
		try {																							//IndexOutOfBoundsException
			if (this.indexList.get(0) != Integer.MAX_VALUE) {
				this.submitted.remove(this.indexList.get(0)); 
				
				if (this.waitlistNo() != 0) {
					this.submitted.add(this.waitlist.getGuest(0));
					this.waitlist.remove(0);
				}
				
				System.out.println("Stergerea persoanei s-a realizat cu succes.");
				return true;
			} 

			if (this.indexList.get(1) != Integer.MAX_VALUE) {
				this.waitlist.remove(this.indexList.get(1));
				
				System.out.println("Stergerea persoanei s-a realizat cu succes.");
				return true;
			}
			
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Error: a fost accesata o pozitie inexistenta");
		}
		
		System.out.println("Eroare: persoana nu a fost inscrisa la eveniment");
		return false;
	}

//update - foloseste meniu de autentificare
	
	public boolean update(Scanner sc) {
		
		System.out.println("Se actualizeaza detaliile unei persoane...");
		
		this.chooseMenu(sc);
		
		System.out.println("Alege campul de actualizat, tastand:"
				+ "\n\t\"1\" - Nume"
				+ "\n\t\"2\" - Prenume"
				+ "\n\t\"3\" - Email"
				+ "\n\t\"4\" - Numar de telefon (format \"+40733386463\")");
		
		boolean isNumber = true;
		while (isNumber) {
			try {																					//InputMismatchException
				int n = sc.nextInt();
				isNumber = false;
				
				try {																				//IndexOutOfBoundsException
					
					if (this.indexList.get(0) != Integer.MAX_VALUE) {
						this.submitted.update(this.indexList.get(0), n, sc);
						
						System.out.println("Actualizarea s-a realizat cu succes.");
						return true;
					} else if (this.indexList.get(1) != Integer.MAX_VALUE) {
						this.waitlist.update(this.indexList.get(1), n, sc);
						
						System.out.println("Actualizarea s-a realizat cu succes.");
						return true;
					}
					
				} catch (IndexOutOfBoundsException e) {
					System.out.println("Error: a fost accesata o pozitie inexistenta");
				}
				
			} catch (InputMismatchException e) {
				
				sc.nextLine();
				System.out.println("Error: Nu ai introdus un numar. Mai incearca");
			} 
		}
		System.out.println("Actualizarea nu a putut fi realizata.");
		return false;
	}
	
//search	
	
	public void search(Scanner sc) {
		System.out.println("Cauta in liste contacte care contin: ");
		String str = sc.next();
		
		if (this.submitted.search(str).size() == 0) {
			System.out.println("In urma cautarii nu au fost gasite persoane in lista de participanti.");
		} else {
			System.out.println("In lista de participanti au fost gasite urmatoarele contacte:\n");
			this.printArr(this.submitted.search(str));
		}
		
		if (this.waitlist.search(str).size() == 0) {
			System.out.println("In urma cautarii nu au fost gasite persoane in lista de asteptare.");
		} else {
			System.out.println("In lista de asteptare au fost gasite urmatoarele contacte:\n");
			this.printArr(this.waitlist.search(str));
		}
		
	}
	
	private void printArr(ArrayList<String> arrList) {
		for (int i = 0; i < arrList.size(); i++) {
			System.out.println(arrList.get(i));
		}
		System.out.println();
	}
	
	
	
		
}
	

	
	


