package com.claim;

import java.util.Scanner;

public class PhonebookApplication {
	
	public static void main(String[] args) {
			
			//A new Phonebook object is created each time this application is run
			Phonebook phoneBook = new Phonebook();
			
			System.out.println("Enter 0 to experience the phonebook");
			Scanner input = new Scanner(System.in);
			//use .getMenuInput to ensure that user enters an int and return that int
			int menuChoice = getMenuInput(input);
			
			//if user entered a number besides 0, they are given another opportunity to enter 0 to continue to phonebook menu
			if (menuChoice != 0) {
				System.out.println("Are you suree?? Enter 0 if you'd like to hang around. Enter any other number to exit.");
				menuChoice = getMenuInput(input);
			}
			
			//once the user enters 0, they have the opportunity to view the menu and use the phonebook.. they have the option to keep returning to this point or hit another int to exit
			while(menuChoice == 0) {
				
				//prints the main menu
				displayMenu();
				//gets user's choice
				menuChoice = getMenuInput(input);
				
				//switch based on menu choice
				switch(menuChoice) {
				
				case 1: 
					//Add New Contact Card(s) - this allows you to add multiple contacts at the same time via console or file
					displayInputSubMenu();
					int inputSubMenuChoice = getMenuInput(input);

					switch(inputSubMenuChoice) {
					case 1:
						//Enter Manually - user is prompted to enter lines which will be read in as a String
						System.out.println("Enter new contacts in the following format: John Michael West Doe, 574 Pole Ave, St. Peters, MO, 63333, 5628592375\nYou can enter as many contacts as you'd like - each on a separate line");
						//number of line we've read
						int i=0;
						while(input.hasNextLine()) {
							String contactInfo = input.nextLine();
							i++;
							//this allows the user to hit enter to end data flow
							if (contactInfo.isEmpty()) {
								break;
							}
							else if (! isCorrectLength(contactInfo)) {
								System.out.println("\nContact Info for line #" + i + " was not entered in the correct format. The number of commas is wrong.\nNo contact card created for that line.");
							}
							else {
								//a ContactCard object is created and stored in the contacts array of the phoneBook object (info from String is parsed by splitting at commas and trimming off white space)
								phoneBook.createContactCard(contactInfo);
							}
							System.out.println("Hit Enter to continue.");
						}
						break;
					case 2:
						//Inport a file from Docs folder - user is prompted to enter the name of the file which will be read in
						System.out.println("Enter the name of the file");
						String fileName = input.nextLine();
						phoneBook.readMultipleContactCardsFromFile(fileName);
						break;
					default: 
						System.out.println("You entered an invalid number");
						break;
					}
					break;
					
				case 2:
					//View All Contact Cards
					//Printing the phoneBook object by using its .toString() method which orders the objects in its' contacts array alphabetically by first name with each ContactCard
					//on a new line.. this looks clean when printed
					System.out.println("Here are all of your contacts:\n" + phoneBook + "Glad you have friends!");
					break;
				
				case 3: 
					//Update a Card
					//prompting user for phone number to search by
					System.out.println("Enter the phone number of the ContactCard you'd like to update.\nUse the following format: 5553334444");
					long phoneNumber = input.nextLong();
					//this gets past the nextLine, so when I call .nextLine below it will be on the right line
					input.nextLine();
					//if this contactCard exists, 
					if (confirmCard(phoneNumber, phoneBook)) {
						//display update choices
						displayUpdateSubMenu();
						//gets user's choice
						int subMenuChoice = getMenuInput(input);
						switch(subMenuChoice) {
						case 1:
							System.out.println("Enter the updated first name");
							//COULD INCLUDE A TRY&CATCH THESE IN CASE THEY ENTER MORE THAN ONE WORD
							phoneBook.updateCCFirstName(phoneNumber, input.nextLine());
							break;
							//IS IT GOOD TO DO LIKE I DID ABOVE AND HAVE EVERYTHING GO THROUGH PHONEBOOK OR SHOULD I HAVE PUT THAT STUFF OUT HERE IN THE APPLICATION?
						case 2: 
							System.out.println("Enter the updated last name");
							phoneBook.updateCCLastName(phoneNumber, input.nextLine());
							break;
						case 3: 
							System.out.println("Enter the updated Middle Name(s)");
							phoneBook.updateCCMiddleName(phoneNumber, input.nextLine());
							break;
						case 4: 
							System.out.println("Enter the new phone number in the following format: 5553334444");
							//need to check that there is not already a contact with this number
							Long newNumber = Long.parseLong(input.nextLine());
							//if another contactCard for this number already exists, 
							if (confirmCard(phoneNumber, phoneBook)) {
								System.out.println("There is already a ContactCard for this phone number. Please update that ContactCard instead.");
							}
							else {
								phoneBook.updateCCPhoneNumber(phoneNumber, newNumber);
							}
							break;
						case 5:
							System.out.println("Enter the new address in the following format: 114 Market St, St Louis, MO, 63403");
							String[] items = input.nextLine().split(",");
							if (items.length != 4) {
								System.out.println("Address was not entered in the correct format. The number of commas is wrong.\nContact card not updated.");
							}
							else {
								phoneBook.updateCCAddress(phoneNumber, items);
							}
							break;
						default: 
							System.out.println("You entered an invalid number");
							break;
						}
					}
					break;
				
				case 4:
					//Delete a Card
					//prompting user for phone number to search by
					System.out.println("Enter a phone number to delete in the following format: 5553334444");
					phoneNumber = input.nextLong();
					//this gets past the nextLine, so when I call .nextLine below it will be on the right line
					input.nextLine();
					//check that this contactCard exists, 
					if (confirmCard(phoneNumber, phoneBook)) {
						//if it does exist, we delete it from the contacts array of phoneBook (the array also shrinks by one size)
						phoneBook.deleteContactCard(phoneNumber);
					}
					break;
					
				case 5: 
					//Search for a specific card
					//displays the sub menu and gets a subMenuChoice from user - WE COULD USE A TRY/CATCH HERE IN CASE THEY DON'T ENTER AN INT
					displaySearchSubMenu(); 
					int subMenuChoice = getMenuInput(input);
					
					//switch based on subMenuChoice
					//for each search choice, the phoneBook searches through its' contacts array for any ContactCards meeting the search criteria. All ContactCards that meet the criteria are added to a new array
					//this array is printed out as a String with each ContactCard on it's own line
					switch (subMenuChoice) {
					case 1:
						System.out.println("Enter a first name");
						//COULD INCLUDE A TRY&CATCH HERE IN CASE THEY ENTER MORE THAN ONE WORD
						String firstName = input.next();
						System.out.println(phoneBook.getContactsWithFirstName(firstName));
						break;
					case 2:
						System.out.println("Enter a last name");
						//COULD INCLUDE A TRY&CATCH HERE IN CASE THEY ENTER MORE THAN ONE WORD
						String lastName = input.next();
						System.out.println(phoneBook.getContactsWithLastName(lastName));
						break;
					case 3:
						System.out.println("Enter a full name");
						String fullName = input.nextLine();
						System.out.println(phoneBook.getContactsWithFullName(fullName));
						break;
					case 4:
						System.out.println("Enter a phone number to search for in the following format: 5553334444");
						phoneNumber = input.nextLong();
						if (phoneBook.getContactCard(phoneNumber) == null) {
							System.out.println("No contacts found with this info");
						}
						else {
							System.out.println(phoneBook.getContactCard(phoneNumber));
						}
						break;
					case 5: 
						System.out.println("Enter a city");
						String city = input.nextLine();
						System.out.println(phoneBook.getContactsWithCity(city));
						break;
					case 6: 
						System.out.println("Enter a state abbreviation (ex. MO)");
						String state = input.nextLine();
						System.out.println(phoneBook.getContactsWithState(state));
						break;
					default: 
						System.out.println("You entered an invalid number");
						break;
					}
					break;
					
				case 6:
					//Add Notes
					//prompting user for phone number to search by
					System.out.println("Enter the phone number of the ContactCard.\nUse the following format: 5553334444");
					long phoneNum = input.nextLong();
					//this gets past the nextLine, so when I call .nextLine below it will be on the right line
					input.nextLine();
					//if this contactCard exists, 
					if (confirmCard(phoneNum, phoneBook)) {
						//display update choices
						displayNotesSubMenu();
						int notesMenuChoice = getMenuInput(input);
						switch(notesMenuChoice) {
						case 1:
							//view notes
							if (phoneBook.getContactCard(phoneNum).getNotes() == null) {
								System.out.println("This contact doesn't have any notes yet.");
							}
							else {
								System.out.println(phoneBook.getContactCard(phoneNum).getNotes());
							}
							break;
						case 2: 
							//Add to notes
							//FOR SOME REASON, HAVE TO HIT ENTER 3 TIMES AFTER TYPING NOTES
							System.out.println("Enter your notes here");
							String notes = "";
							while (input.hasNextLine()) {
								String noteAdd = input.nextLine();
								if (noteAdd.isEmpty()) {
									break;
								}
								else {
								notes = notes +"\n" + noteAdd;
								}
							}
							if (phoneBook.getContactCard(phoneNum).getNotes() == null) {
								phoneBook.getContactCard(phoneNum).setNotes(notes);
							}
							else {
								String origNotes = phoneBook.getContactCard(phoneNum).getNotes();
								phoneBook.getContactCard(phoneNum).setNotes(origNotes + "\n" + notes);
							}
							System.out.println("These notes have been added.");
							break;
						case 3:
							//Erase or write over the notes
							System.out.println("Enter the new notes here - these will overwrite the old notes. \nIf you want to simply erase the notes, enter a blank line");
							String notez = "";
							while (input.hasNextLine()) {
								String noteAdd1 = input.nextLine();
								if (noteAdd1.isEmpty()) {
									break;
								}
								else {
								notez += ("\n" + noteAdd1);
								}
							}
							phoneBook.getContactCard(phoneNum).setNotes(notez);
							System.out.println("These notes have been added.");
							break;
						default: 
							System.out.println("You entered an invalid number");
							break;
						}		
					}
					break;
					
				
				case 7:
					//Save to file
					displaySaveSubMenu();
					int saveMenuInput = getMenuInput(input);
					switch(saveMenuInput) {
					case 1:
						//save a single ContactCard
						System.out.println("Enter the phone number of the contact you'd like to save");
						long phoneNum1 = input.nextLong();
						input.nextLine();
						ContactCard ccToSave = phoneBook.getContactCard(phoneNum1);
						ccToSave.saveToFile();
						break;
					case 2:
						//save entire phonebook
						System.out.println("What would you like to name this file?");
						String fileName = input.nextLine();
						phoneBook.saveAllContactsToFile(fileName);
						break;
					default: 
						System.out.println("You entered an invalid number");
						break;
					}
					break;
					
				case 8:
					//Exit
					//this does NOT exit directly. Instead it goes to the end of the while loop where they are given a last chance to select main menu or exit
					System.out.println("Are you suree??");
					break;
					
				default: 
					//If an int other than 1-6 is selected this also goes to the end of the while loop where they are given a last chance to select main menu or exit
					System.out.println("You can't even work this simple machine??");
					break;
				}
				
				//after we have exited whichever switch the user was in, they are given the chance to continue back to the main menu, or exit the application
				System.out.println("\nHit 0 to continue to main menu. Hit any other number to exit.");
				menuChoice = getMenuInput(input);
				
			}
			
			//if the user chooses not to continue to main menu by hitting 0, the scanner is closed and we exit main thus getting rid of the phoneBook (nothing is stored)
			input.close();
			System.out.println("\nYou have exited the Phonebook!");
			ASCIIArtGenerator artGen = new ASCIIArtGenerator();
			try {
				artGen.printTextArt("Bye!", ASCIIArtGenerator.ART_SIZE_MEDIUM);
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	
	public static void displayMenu() {
		System.out.println("Please enter a number for your selection\nMenu Options:" +
				"\n1: Add New Contact Card(s)" + 
				"\n2: View All Contact Cards" +
				"\n3: Update a Card" + 
				"\n4: Delete a Card" + 
				"\n5: Search for a Specific Card" +
				"\n6: Manage Notes for a Card" +
				"\n7: Save Contact(s) to your Documents" +
				"\n8: Exit");
	}
	
	public static void displayInputSubMenu() {
		System.out.println("Please enter a number for your selection:" +
				"\n1: Enter manually" + 
				"\n2: Import a file from your Documents folder" );
	}
	
	public static void displayNotesSubMenu() {
		System.out.println("Please enter a number for your selection:" +
				"\n1: View the notes for this card" + 
				"\n2: Add to the notes for this card" +
				"\n3: Delete or replace the notes for this card");
	}
	
	public static void displaySearchSubMenu() {
		System.out.println("Please enter a number for your selection:" +
				"\n1: Search by First Name" + 
				"\n2: Search by Last Name" +
				"\n3: Search by Full Name" + 
				"\n4: Search by Phone Number" + 
				"\n5: Search by City" + 
				"\n6: Search by State");
	}
	
	public static void displayUpdateSubMenu() {
		System.out.println("Please enter a number for your selection:" +
				"\n1: Update First Name" + 
				"\n2: Update Last Name" +
				"\n3: Update Middle Name(s)" +
				"\n4: Update Phone Number" + 
				"\n5: Update Address");
	}
	
	public static void displaySaveSubMenu() {
		System.out.println("Please enter a number for your selection:" +
				"\n1: Save a single ContactCard as file" + 
				"\n2: Save entire Phonebook as file" );
	}
	
	public static boolean isCorrectLength(String contactInfo) {
		//split the contactInfo string into several strings based on , delimiter
		String[] items = contactInfo.split(",");
		//see if it has the right number of sections
		if (items.length == 6) {
			return true;
		}
		else {
			
		}return false;
	}
	
	public static boolean confirmCard(long phoneNumber, Phonebook phoneBook) {
		//if a ContactCard for this phone number does not already exist in the phoneBook's contacts array, we cannot delete it
		if (phoneBook.getContactCard(phoneNumber) == null) {
			System.out.println("No ContactCard was found for this phone number. No action was taken.");
			return false;
		}
		else {
			return true;
		}
	}
	
	public static int getMenuInput(Scanner input) {
		//checking to make sure the user entered an int.. it keeps the user in a loop until they do so
		if (!input.hasNextInt()) {
			//skip that input
			input.next();
			System.out.println("Only integers allowed. Try again.");
		}
		//store's user's choice
		int menuChoice = input.nextInt();
		//this gets past the nextLine, so when I call .nextLine anywhere below it will be on the right line
		input.nextLine();
		return menuChoice;
	}

}
