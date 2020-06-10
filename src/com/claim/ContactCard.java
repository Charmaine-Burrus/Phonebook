package com.claim;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class ContactCard implements Comparable<ContactCard> {
	
	private Name name;
	private String streetAddress;
	private String city;
	private String state;
	private int zipCode;
	private long phone;
	private String notes;
	private static final String path = "C:\\Users\\Charmaine\\Documents\\Phonebook\\";
	
	//a new object is initialized by separating all the contact info and storing it in the correct instance variable via the parseContactInfo method below
	//WOULD IT BE BETTER TO PARSE IN THE APPLICATION AND CREATE A CONSTRUCTOR WITH ARGUMENTS THAT CORRESPOND TO THE INSTANCE VARIABLES?
	public ContactCard(String contactInfo) {
		parseContactInfo(contactInfo);
	}
	
	public void setName(Name name) {
		this.name = name;
	}
	
	public Name getName() {
		return name;
	}
	
	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}
	
	public String getStreetAddress() {
		return streetAddress;
	}
	
	public void setCity(String city) {
		this.city = city;
	}
	
	public String getCity() {
		return city;
	}
	
	public void setState(String state) {
		this.state = state;
	}
	
	public String getState() {
		return state;
	}
	
	public void setZipCode(int zipCode) {
		this.zipCode = zipCode;
	}
	
	public int getZipCode() {
		return zipCode;
	}
	
	public void setPhone(long phone) {
		this.phone = phone;
	}
	
	public long getPhone() {
		return phone;
	}
	
	//parse from the following format: John Michael West Doe, 574 Pole Ave, St. Peters, MO, 63333, 5628592375" but one of the testcases is missing and only has comma
	public void parseContactInfo(String contactInfo) {
		//split the contactInfo string into several strings based on , delimiter
		String[] items = contactInfo.split(",");
		for (int i=0; i<items.length; i++) {
			switch(i) {
			case 0:
				String name = items[i].trim();
				this.name = new Name(name);
				break;
			case 1:
				String streetAddress = items[i].trim();
				this.streetAddress = streetAddress;
				break;
			case 2:
				String city = items[i].trim();
				this.city = city;
				break;
			case 3:
				String state = items[i].trim();
				this.state = state;
				break;
			case 4:
				String zipCode = items[i].trim();
				this.zipCode = Integer.parseInt(zipCode);
				break;
			case 5:
				String phone = items[i].trim();
				this.phone = Long.parseLong(phone);
			}
		}
	}

	//.compareTo will be alphabetical by first name
	public int compareTo(ContactCard contact2) {
		int result = name.toString().compareTo(contact2.getName().toString());
		return result;
	}
	
	//.equals will determine equality by phone number
	public boolean equals(ContactCard contact2) {
		if (phone == contact2.getPhone()) {
			return true;
		}
		return false;
	}
	
	public String toString() {
		String fullContactCard = "Name: " + name +
								"\tAddress: "+ streetAddress + ", " + city + ", " + state + ", " + zipCode;
		//somehow parse digits of phone into (636)-453-8563  // indexes (012)-345-6789
		String phoneString = Long.toString(phone);
		//could prbly make this better
		String modPhoneString = '(' + phoneString.substring(0, 3) + ')' + '-' + phoneString.substring(3, 6) + '-' + phoneString.substring(6);
		fullContactCard = fullContactCard + " " + "\tTelephone: " + modPhoneString + "\tNotes: " + notes;
		return fullContactCard;
	}	
	
	public String formatData() {
		return name + ", " + streetAddress + ", " + city + ", " + state + ", " + zipCode + ", " + phone + ", " + notes;
	}
	
	public void saveToFile() {
		//creating fileName based on email address is smart because you can make it unique (never have 2 students with same email)
		String fileName = path + name + ".txt";
		//BW takes a FW argument
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
			bw.write(formatData());
			bw.close();  
		}catch (IOException e) {
			System.out.println("Error writing to file");
		}
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	public void addToNotes(String input) {
		this.notes += ("\n" + input);
	}
	
}
