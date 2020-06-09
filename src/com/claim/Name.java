package com.claim;

public class Name {
	
	private String firstName;
	private String middleNames;
	private String lastName;
	
	public Name(String fullName) {
		parseName(fullName);
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setMiddleNames(String middleNames) {
		this.middleNames = middleNames;
	}
	
	public String getMiddleNames() {
		return middleNames;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void parseName(String fullName) {
		String[] items = fullName.split(" ");
		int numberOfItems = items.length;
		firstName = items[0];
		lastName = items[numberOfItems-1];
		middleNames = "";
		for (int i=1; i<numberOfItems-1; i++) {
			middleNames = middleNames + items[i] + " ";
		}
	}
	
	public String toString() {
		String fullName = firstName + " ";
		//this is working because parse name makes middle "".. otherwise need to check if it's null
		fullName = fullName + middleNames;
		return fullName += lastName;
	}

}
