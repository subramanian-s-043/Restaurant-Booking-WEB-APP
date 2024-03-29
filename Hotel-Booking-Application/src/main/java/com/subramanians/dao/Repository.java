package com.subramanians.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.subramanians.dto.Booking;
import com.subramanians.dto.Customer;
import com.subramanians.dto.Table;

public class Repository {
	static Repository repo=null;
	Connection connection=null;
	PreparedStatement statement;
	ResultSet result;
	private Customer currentCustomer;
	List<Table> retrived=new ArrayList<>();
	private List<LocalTime> bookedTimes=new ArrayList<>();
	
	public Repository() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String username="root";
			String password="admin";
			String url="jdbc:mysql://localhost:3306/restaurant_table_booking";
			connection=DriverManager.getConnection(url,username,password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("Error In Connecting With Database!,Please Import the SQL file !!");
		}
	}
	
	public static Repository getInstance()
	{
		if(repo==null)
		{
			repo=new Repository();
		}
		return repo;
	}
	
	public boolean isValidUser(String username, String password) {
		try {
			statement=connection.prepareStatement("Select * from user_details where username=? and password=?");
			statement.setString(1, username);
			statement.setString(2, password);
			result=statement.executeQuery();
			while(result.next())
			{
				int id=result.getInt("customer_id");
				String name=result.getString("customer_name");
				String userName=result.getString("username");
				String pass=result.getString("password");
				List<Booking> temp=getBookingHistory(id);
				setCurrentCustomer(new Customer(id,name,userName,pass,temp));
				System.out.println("Now Loged-in "+currentCustomer.getName());
				return true;
			}
		}catch(SQLException e)
		{
			e.printStackTrace();
			return false;
		}
		return false;
	}
	
	public Customer getCurrentCustomer() {
		return currentCustomer;
	}

	private List<Booking> getBookingHistory(int id) {
		List<Booking> temp=new ArrayList<>();
		try {
			statement=connection.prepareStatement("select * from booked_tables where user_id=?");
			statement.setInt(1, id);
			result=statement.executeQuery();
			while(result.next())
			{
				String name=result.getString("customerName");
				int bookingId=result.getInt("bookingId");
				String hotelName=result.getString("restaurantName");
				int numberOfpepole=result.getInt("numberOfPeople");
				Date tempDate=result.getDate("dateReservedFor");
				Time tempTime=result.getTime("timeReservedFor");
				temp.add(new Booking(bookingId,name,hotelName,numberOfpepole,LocalDate.parse(String.valueOf(tempDate)),LocalTime.parse(String.valueOf(tempTime))));
			}
			return temp;
		} catch (SQLException e) {
			System.out.println("Error In DB!");
		}
		return null;
	}

	public List<Table> retrieveResutarant(){//Change to boolean
		if(!retrived.isEmpty())
		{
			retrived.clear();
		}
			try {
				statement=connection.prepareStatement("select * from restaurant_details where isAvailable=?;");
				statement.setBoolean(1, true);
				result=statement.executeQuery();
				while(result.next())
				{
					String name=result.getString("restaurantName");
					int tables=result.getInt("totalNumberOfTables");
					int capacity=result.getInt("eachTableCapacity");
					boolean isAvailable=result.getBoolean("isAvailable");
					retrived.add(new Table(name,tables,capacity,isAvailable));
				}
			} catch (SQLException e) {
				System.out.println("Error In DB!");
			}
		return retrived;
	}
	
	public void setCurrentCustomer(Customer currentCustomer) {
		this.currentCustomer = currentCustomer;
	}
	
	public List<LocalTime> retrieveAvailability(String restaurantName,LocalDate date) {
		try {
			bookedTimes.clear();
			statement=connection.prepareStatement("select * from booked_tables where restaurantName=? and dateReservedFor=?");
			statement.setString(1, restaurantName);
			statement.setDate(2, Date.valueOf(date));
			result=statement.executeQuery();
			while(result.next())
			{
				Time temp=result.getTime("timeReservedFor");
				bookedTimes.add(LocalTime.parse(String.valueOf(temp)));
			}
		} catch (SQLException e) {
			System.out.println("Error In DB!");
		}
		return bookedTimes;
	}
	
	public boolean book(String name,String restaturantName,int numberOfPeople,LocalDate dateReservedFor,LocalTime timeReservedFor,int user_id) {
		int bookingId=getBookingId();
		Booking c=new Booking(bookingId, name, restaturantName, numberOfPeople, dateReservedFor, timeReservedFor);
		//bookedCustomer.add(c);
		//addToHistory(c);
		try {
			statement=connection.prepareStatement("Insert into booked_tables (bookingId,customerName,restaurantName,dateReservedFor,timeReservedFor,numberOfPeople,user_id)VALUES(?,?,?,?,?,?,?)");
			statement.setInt(1,c.getBookingId());
			statement.setString(2,c.getCustomerName());
			statement.setString(3, c.getbookedRestaurant());
			statement.setDate(4, Date.valueOf(c.getDate()));
			statement.setTime(5, Time.valueOf(c.getTime()));
			statement.setInt(6, c.getnumberOfPeoples());
			statement.setInt(7, user_id);
			int rowsAffected=statement.executeUpdate();
			if(rowsAffected > 0)
			{
				return true;
			}else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error In DB!");
		}
		return false;
	}

	private int getBookingId() {
		int count=1;
		try {
			statement=connection.prepareStatement("Select * from booked_tables");
			result=statement.executeQuery();
			while(result.next())
			{
				count=result.getInt("bookingId");
			}
		} catch(SQLException e) {
			
		}
		return count+1;
	}
	
}
