package com.subramanians.dto;

public class Table {
	private String restaurantName;
	private int totalNumberOfTable;
	private int tableCapacity;
	private Boolean isAvailable;
	
	public Table(String restaurantName,int totalNumberOfTable,int tableCapacity,boolean available)
	{
		setRestaurantName(restaurantName);
		setTotalNumberOfTable(totalNumberOfTable);
		setTableCapacity(tableCapacity);
		setIsAvailable(available);
	}
	public Boolean getIsAvailable() {
		return isAvailable;
	}
	public void setIsAvailable(Boolean isAvailable) {
		this.isAvailable = isAvailable;
	}
	public String getRestaurantName() {
		return restaurantName;
	}
	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}
	public int getTotalNumberOfTable() {
		return totalNumberOfTable;
	}
	public void setTotalNumberOfTable(int tableNo) {
		this.totalNumberOfTable = tableNo;
	}
	public int getTableCapacity() {
		return tableCapacity;
	}
	public void setTableCapacity(int tableCapacity) {
		this.tableCapacity = tableCapacity;
	}
}
