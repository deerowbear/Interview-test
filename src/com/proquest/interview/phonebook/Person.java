package com.proquest.interview.phonebook;

public class Person {
	public String name;
	public String phoneNumber;
	public String address;

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("\nFirst name: ").append(name);
		stringBuilder.append("\nPhone number: ").append(phoneNumber);
		stringBuilder.append("\nAddress: ").append(address);
		return stringBuilder.toString();
	}
}
