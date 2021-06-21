package com.proquest.interview.phonebook;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.proquest.interview.util.DatabaseUtil;

public class PhoneBookImpl implements PhoneBook {
	public List<Person> people = new ArrayList<>();

	@Override
	public Person findPerson(String firstName, String lastName) {
		for(Person person : people) {
			if(null != firstName && null != lastName) {
				if(person.name.contains(firstName) && person.name.contains(lastName)) {
					return person;
				}
			}
			if(null != firstName && null == lastName) {
				if(person.name.contains(firstName)) {
					return person;
				}
			}
			if(null != lastName) {
				if(person.name.contains(lastName)) {
					return person;
				}
			}
		}
		return null;
	}

	@Override
	public void addPerson(Person newPerson) {
		if(null == newPerson) return;
		try {
			Connection cn = DatabaseUtil.getConnection();
			Statement stmt = cn.createStatement();
			stmt.execute("INSERT INTO PHONEBOOK (NAME, PHONENUMBER, ADDRESS) VALUES('" + newPerson.name + "','" + newPerson.phoneNumber + "', '" + newPerson.address + "')");
			cn.commit();
			cn.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public Person findPersonByName(Person newPerson) {
		if(null == newPerson) return null;
		Person person = new Person();
		try {
			Connection connection = DatabaseUtil.getConnection();
			Statement stmt = connection.createStatement();
			String query = "select * from PHONEBOOK where name = '" + newPerson.name + "'";
			ResultSet result = stmt.executeQuery(query);
			while(result.next()){
				person.name = result.getString("name");
				person.address = result.getString("address");
				person.phoneNumber = result.getString("phonenumber");
			}
			connection.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return person;
	}

	
	public static void main(String[] args) {
		DatabaseUtil.initDB();  //You should not remove this line, it creates the in-memory database

		//Create a phonebook to store objects
		PhoneBookImpl phoneBook = new PhoneBookImpl();

		/* TODO: create person objects and put them in the PhoneBook and database
		 * John Smith, (248) 123-4567, 1234 Sand Hill Dr, Royal Oak, MI
		 * Cynthia Smith, (824) 128-8758, 875 Main St, Ann Arbor, MI
		*/
		Person person1 = new Person();
		person1.address = "1234 Sand Hill Dr, Royal Oak, MI";
		person1.phoneNumber = "(248) 123-4567";
		person1.name = "John Smith";
		phoneBook.people.add(person1);

		Person person2 = new Person();
		person2.address = "875 Main St, Ann Arbor, MI";
		person2.phoneNumber = "(824) 128-8758";
		person2.name = "Cynthia Smith";
		phoneBook.people.add(person2);


		// TODO: print the phone book out to System.out
		for(Person person : phoneBook.people) {
			System.out.println(person.toString());
		}

		// TODO: find Cynthia Smith and print out just her entry
		Person foundPerson = phoneBook.findPerson("Cynthia", null);
		System.out.println("\nFound Cynthia: " + foundPerson.toString());

		// TODO: insert the new person objects into the database
		phoneBook.addPerson(person1);
		phoneBook.addPerson(person2);


		System.out.println(phoneBook.findPersonByName(person1).toString());
		System.out.println(phoneBook.findPersonByName(person2).toString());


	}
}
