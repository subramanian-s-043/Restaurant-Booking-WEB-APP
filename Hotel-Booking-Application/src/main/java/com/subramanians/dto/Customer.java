package com.subramanians.dto;

import java.util.List;

public class Customer {
	private int id;
	private String name;
	private String username;
	private String password;
	private List<Booking> bookingHistory;
	
	public Customer(int id,String name,String username,String password,List<Booking> history)
	{
		this.id=id;
		this.name=name;
		this.username=username;
		this.password=password;
		this.bookingHistory=history;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public List<Booking> getBookingHistory() {
		return bookingHistory;
	}
	public void setBookingHistory(List<Booking> bookingHistory) {
		this.bookingHistory = bookingHistory;
	}
}
