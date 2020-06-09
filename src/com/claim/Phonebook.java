package com.claim;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class Phonebook {

	private ContactCard[] contacts;
	private static final String path = "C:\\Users\\Charmaine\\Documents\\Phonebook\\";
	
	public Phonebook() {
		contacts = new ContactCard[0];
	}
	
	public void createContactCard(String contactInfo) {
		//create ContactCard object using the contactInfo passed
		ContactCard newContact = new ContactCard(contactInfo);
		//look through all current contacts
		for (int i=0; i<contacts.length; i++) {
			//see if a contact with this phone number already exists - if so, inform the user and exit this method
			if (contacts[i].equals(newContact)) {
				System.out.println("\nA contact card for this phone number already exists. Please use the update option instead.");
				return;
			}
		}				
		//once all ContactCards in the contacts array have been checked, we can be sure that this new ContactCard is unique
		//we create an updatedContacts array which will replace contacts
		ContactCard[] updatedContacts = new ContactCard[contacts.length + 1];
		//we add all the existing ContactCards from contacts to updatedContacts
		for (int i=0; i<contacts.length; i++) {
			updatedContacts[i] = contacts[i];
		}
		//we add the new ContactCard into the final space of the new array
		updatedContacts[updatedContacts.length -1] = newContact;
		//we replace the contacts array of this Phonebook with the updated one
		contacts = updatedContacts;
		//inform the user of our success
		System.out.println("\nA contact card has been created. Congrats on your new friend!");
	}
	
	//allows user to set the entire contacts array directly
	public void setContacts(ContactCard[] contacts) {
		this.contacts = contacts;
	}
	
	public ContactCard[] getContacts() {
		return contacts;
	} 
	
	//returns the one ContactCard that has this phone number (there can never be more than one ContactCard w/ same phone number b/c of design of createContactCard() method
	public ContactCard getContactCard(long phoneNumber) {
		for (int i=0; i<contacts.length; i++) {
			if (contacts[i].getPhone() == phoneNumber) {
				return contacts[i];
			}
		}
		//if there is no ContactCard with this phone number, it returns null
		return null;
	}
	
	public Name getCCName(long phoneNumber) {
		ContactCard thisContact = this.getContactCard(phoneNumber);
		return thisContact.getName();
	}
	
	public void updateCCFirstName(long phoneNumber, String firstName) {
		Name thisName = getCCName(phoneNumber);
		thisName.setFirstName(firstName);
		System.out.println("Name change complete!");
	}
	
	public void updateCCLastName(long phoneNumber, String lastName) {
		Name thisName = getCCName(phoneNumber);
		thisName.setLastName(lastName);
		System.out.println("Name change complete!");
	}
	
	public void updateCCMiddleName(long phoneNumber, String middleName) {
		Name thisName = getCCName(phoneNumber);
		thisName.setMiddleNames(middleName + " ");
		System.out.println("Name change complete!");
	}
	
	public void updateCCPhoneNumber(long oldPhoneNumber, long newPhoneNumber) {
		ContactCard thisContact = this.getContactCard(oldPhoneNumber);
		thisContact.setPhone(newPhoneNumber);
		System.out.println("Phone number update complete!");
	}
	
	public void updateCCAddress(long phoneNumber, String[] items) {
		ContactCard thisContact = this.getContactCard(phoneNumber);
		for (int i=0; i<items.length; i++) {
			switch(i) {
			case 0:
				String streetAddress = items[i].trim();
				thisContact.setStreetAddress(streetAddress);
				break;
			case 1:
				String city = items[i].trim();
				thisContact.setCity(city);
				break;
			case 2:
				String state = items[i].trim();
				thisContact.setState(state);
				break;
			case 4:
				int zipCode = Integer.parseInt(items[i].trim());
				thisContact.setZipCode(zipCode);
				break;
			}
		}
		System.out.println("Address update complete!");
	}
	
	public void deleteContactCard(long phoneNumber) {
		//make a new contact array that is one space shorter than the original
		//I am confident of this approach b/c we have already checked in the application to make sure the phoneNumber is in the contact array
		//and no 2 ContactCards can have the same phone number.. so we will definitely be removing 1 ContactCard from the array
		ContactCard[] updatedContacts = new ContactCard[contacts.length - 1];
		//this is the counter for updatedContacts
		int h=0;
		//go through each ContactCard of the original contacts array
		for (int i=0; i<contacts.length; i++) {
			//check if that ContactCard matches the phoneNumber (the one that DOES match the phoneNumber will not be added to updatedContacts - thus, it's deleted)
			if (contacts[i].getPhone() != phoneNumber) {
				//if not, add it to the updated array
				updatedContacts[h] = contacts[i];
				//update h
				h++;
			}
		}
		//replace the old contacts array with the updated one
		contacts = updatedContacts;
		//let user know the update was successful
		System.out.println("Good riddance! This contact has been deleted.");
		return;
	}
	
	//each of these returns an array of contact cards which meet the criteria passed as an argument -- THEY ALL LOOK VERY SIMILAR.. IS THERE A WAY TO SIMPLIFY?
	
	//I think it's best to return as String because I can't print array of ContactCards from app w/o being sloppy
	public String getContactsWithFirstName(String firstName) {
		ContactCard[] ccArray = new ContactCard[0];
		//unfilled index of ccArray
		int h = 0;
		for (int i=0; i<contacts.length; i++) {
			if (contacts[i].getName().getFirstName().equals(firstName)) {
				ccArray = expandArray(ccArray, 1);
				ccArray[h] = contacts[i];
				h++;
			}
		}
		if (ccArray.length == 0) {
			return "No contacts found with this info";
		}
		else {
			return ccArrayToString(ccArray);
		}
	}
	
	public String getContactsWithLastName(String lastName) {
		ContactCard[] ccArray = new ContactCard[0];
		//unfilled index of ccArray
		int h = 0;
		for (int i=0; i<contacts.length; i++) {
			if (contacts[i].getName().getLastName().equals(lastName)) {
				ccArray = expandArray(ccArray, 1);
				ccArray[h] = contacts[i];
				h++;
			}
		}
		if (ccArray.length == 0) {
			return "No contacts found with this info";
		}
		else {
			return ccArrayToString(ccArray);
		}
	}
	
	public String getContactsWithFullName(String fullName) {
		ContactCard[] ccArray = new ContactCard[0];
		//unfilled index of ccArray
		int h = 0;
		for (int i=0; i<contacts.length; i++) {
			if (contacts[i].getName().toString().equals(fullName)) {
				ccArray = expandArray(ccArray, 1);
				ccArray[h] = contacts[i];
				h++;
			}
		}
		if (ccArray.length == 0) {
			return "No contacts found with this info";
		}
		else {
			return ccArrayToString(ccArray);
		}
	}
	
	public String getContactsWithCity(String city) {
		ContactCard[] ccArray = new ContactCard[0];
		//unfilled index of ccArray
		int h = 0;
		for (int i=0; i<contacts.length; i++) {
			if (contacts[i].getCity().equals(city)) {
				ccArray = expandArray(ccArray, 1);
				ccArray[h] = contacts[i];
				h++;
			}
		}
		if (ccArray.length == 0) {
			return "No contacts found with this info";
		}
		else {
			return ccArrayToString(ccArray);
		}
	}
	
	public String getContactsWithState(String state) {
		ContactCard[] ccArray = new ContactCard[0];
		//unfilled index of ccArray
		int h = 0;
		for (int i=0; i<contacts.length; i++) {
			if (contacts[i].getState().equals(state)) {
				ccArray = expandArray(ccArray, 1);
				ccArray[h] = contacts[i];
				h++;
			}
		}
		if (ccArray.length == 0) {
			return "No contacts found with this info";
		}
		else {
			return ccArrayToString(ccArray);
		}
	}
	
	//this is used in each of the getContactsWithWhatever() methods to make sure the array is the correct size to hold the relevant ContactCards
	public ContactCard[] expandArray(ContactCard[] source, int additionalSpots) {
		//creates new array of Contact Cards (the one that will be returned) and makes it 1 space larger than the original array passed in the arguments
		ContactCard[] target = new ContactCard[source.length + additionalSpots];
		//goes through each index of the original array
		for (int i=0; i<source.length; i++) {
			//adds the value from the original array to the corresponding index of the new array
			target[i] = source[i];  //can anything be accessed here??
		}
		//returns the new array (there will be one empty spot at the end)
		return target;
	}
	
	//provides info for all ContactCards of any Contact Card array with info for each contact card on it's own line
	public String ccArrayToString(ContactCard[] ccArray) {
		//sorts the ContactCard array by using the .compareTo method of the ContactCard object - sorts in ascending order alphabetically by full name
		Arrays.sort(ccArray);
		//creates an empty string
		String contactString = "";
		//adds each ContactCard to the string using the ContactCard objects toString method (which prints all of its instance variables)
		for (int i=0; i<ccArray.length; i++) {
			contactString = contactString + ccArray[i] + "\n";
		}
		return contactString;
	}
	
	public String formatCCArray(ContactCard[] ccArray) {
		//sorts the ContactCard array by using the .compareTo method of the ContactCard object - sorts in ascending order alphabetically by full name
		Arrays.sort(ccArray);
		//creates an empty string
		String contactString = "";
		//adds each ContactCard to the string using the ContactCard objects toString method (which prints all of its instance variables)
		for (int i=0; i<ccArray.length; i++) {
			contactString = contactString + ccArray[i].formatData() + "\n";
		}
		return contactString;
	} 
	
	//uses the method directly above to provide info for all of the ContactCards in the contacts array of this phoneBook
	//contacts are not stored alphabetically but are always printed alphabetically
	public String toString() {
		return ccArrayToString(contacts);
	}
	
	public String formatData() {
		return formatCCArray(contacts);
	}
	
	public void readMultipleContactCardsFromFile(String fileName) {
		String filePath = path + fileName + ".txt";
		try {
			//this scanner is taking in a new file
			Scanner scanner = new Scanner(new File(filePath));
			while(scanner.hasNextLine()) {
				String contactInfo = scanner.nextLine();
				//above line will look like john, doe, address, etc
				this.createContactCard(contactInfo);
			}
		}catch(FileNotFoundException e) {
			System.out.println("Error reading from file");
		}
	}
	
	public void saveAllContactsToFile(String fileName) {
		String filePath = path + fileName + ".txt";
		//BW takes a FW argument
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(filePath));
			bw.write(formatData());
			bw.close();  
		}catch (IOException e) {
			System.out.println("Error writing to file");
		}
	}
	
}
