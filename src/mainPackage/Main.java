package mainPackage;
import lists.GuestsList;
import java.util.Scanner;
import java.io.IOException;
public class Main {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		boolean running = true;
		GuestsList event = new GuestsList();
		
		try {
			event.restoreLists();
		} catch (IOException e) {
			System.out.println("IOException thrown when trying to restore lists.");
		}
		
//		System.out.print("Bun venit! Introduceti numarul de locuri disponibile: ");
//		event.setMaxParticipants(sc.nextInt());
		
		while (running) {
			
			System.out.println("Asteapta comanda: (help - Afiseaza lista de comenzi)");
			String command = sc.next();
			
			switch(command) {
				case "help":
					event.printMainMenu();
					break;
				
				case "add": 
					System.out.println("Se adauga o noua persoana...");
					System.out.print("Introduceti numele de familie: ");
					String lastName = sc.next();
					System.out.print("Introduceti prenumele: ");
					String firstName = sc.next();			
					System.out.print("Introduceti email: ");
					String email = sc.next();
					System.out.print("Introduceti numar de telefon (format „+40733386463“): ");
					String telephoneNo = sc.next();
					
					event.add(lastName, firstName, email, telephoneNo);
					break;
					
				case "check":
					event.check(sc);
					break;
					
				case "guests":
					event.guests();
					break;
					
				case "waitlist":
					event.waitlist();
					break;
					
				case "guests_no":
					System.out.println("Numarul de participanti: " + event.guestsNo());
					break;
					
				case "waitlist_no":
					System.out.println("Dimensiunea listei de asteptare: " + event.waitlistNo());
					break;
				
				case "subscribe_no":
					System.out.println("Numarul total de persoane: " + event.subscribeNo());
					break;
				
				case "available":
					System.out.println("Numarul de locuri ramase: " + event.available());
					break;
					
				case "remove":
					event.remove(sc);
					break;
				
				case "update":
					event.update(sc);
					break;
					
				case "search":
					event.search(sc);
					break;
					
				case "quit":
					System.out.println("Aplicatia se inchide...");
					running = false;
					break;
					
				default:
					System.out.println("Comanda introdusa nu este valida.\nIncercati inca o data.");
			}
		}
		
		sc.close();
		
		try {
			event.saveLists();
		} catch (IOException e) {
			System.out.println("IOException thrown when trying to save lists.");
		}
	}

}
