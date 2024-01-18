package com.subramanians.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class Booking {
	private int bookingId;
	private String customerName;
	private String bookedRestaurant;
	private int numberOfPeoples;
	private LocalDate date;
	private LocalTime time;
	public Booking() {
		
	}
	public Booking(int bookingId,String customerName,String bookedRestaurant,int numberOfPeoples,LocalDate date,LocalTime time) {
		this.bookingId=bookingId;
		this.customerName=customerName;
		this.numberOfPeoples=numberOfPeoples;
		this.bookedRestaurant=bookedRestaurant;
		this.date=date;
		this.time=time;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public int getBookingId() {
		return bookingId;
	}
	public void setBookingId(int bookingId) {
		this.bookingId = bookingId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getbookedRestaurant() {
		return bookedRestaurant;
	}
	public void setbookedRestaurant(String bookedRestaurant) {
		this.bookedRestaurant = bookedRestaurant;
	}
	public LocalTime getTime() {
		return time;
	}
	public void setTime(LocalTime time) {
		this.time = time;
	}
	public int getnumberOfPeoples() {
		return numberOfPeoples;
	}
	public void setnumberOfPeoples(int numberOfPeoples) {
		this.numberOfPeoples = numberOfPeoples;
	}
	
}
