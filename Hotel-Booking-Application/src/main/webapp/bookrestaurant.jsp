<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="javax.servlet.*" %>
<%@ page import="java.util.*" %>
<%@ page import="com.subramanians.dao.*" %>
<%@ page import="com.subramanians.dto.*" %>
<% HttpSession isactive=request.getSession(false); if (isactive==null ||
isactive.getAttribute("isLoggedIn")==null || Repository.getInstance().getCurrentCustomer()==null) { response.sendRedirect("login.jsp"); } %>
<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<title>Book Restaurant</title>
<link rel="stylesheet" type="text/css" href="./css/bookRestaurant.css" />
</head>

<body>
<div id="container">
    <div id="sidebar">
        <div id="sidebarElements">
            <h2>Scheduler Dashboard</h2>
            <ul>
                <li><a href="index.jsp">Dashboard</a></li>
                <li><a href="bookrestaurant.jsp">Book a Restaurant</a></li>
                <li><a href="#">View Prev. Bookings</a></li>
                <li><a href="#">Profile</a></li>
                <li><button onclick="logout(event)">Logout</button></li>
            </ul>
        </div>
    </div>

    <div id="content">

        <div id="dashboard">
            <h1>Book Restaurant</h1>

            <form id="bookingForm">
                <label for="restaurant">Select Restaurant:</label>
                <select id="restaurant" name="restaurant" required>
                    <option selected>--Select Restaurant---</option>
                    <% List<Table> restaurants =
                        Repository.getInstance().retrieveResutarant();
                        for(int i=0;i<restaurants.size();i++) { %>
                            <option value="<%= restaurants.get(i).getRestaurantName() %>">
                                <%= restaurants.get(i).getRestaurantName() %>
                            </option>
                            <%} %>

                </select>

                <label for="date">Select Date:</label>
                <input type="date" id="date" name="date"
                    min="<%= java.time.LocalDate.now() %>"
                    max="<%= java.time.LocalDate.now().plusDays(10) %>" required>

                <button type="button" onclick="bookRestaurant(event)">Get
                    Availability</button>
                <p>( You can book for next 10 days only. )</p>
            </form>
            <div id="showAvailable"></div>

            <div class="overlay" id="overlay"></div>

            <div class="popup-container" id="popupForm">
                <h2>Booking-Form</h2>
                <form id="innerForm">
                    <!-- Your form fields go here -->
                    <label for="restaurantName">Restaurant Name:</label>
                    <input type="text" id="restaurantName" name="restaurantName" readonly
                        disabled>
                    <label for="food-timing">Chosen food-time:</label>
                    <input type="text" id="food-timing" name="food-timing" readonly
                        disabled>
                    <label for="chosenTime">Chosen Time:</label>
                    <input type="datetime" id="chosenTime" name="chosenTime" readonly
                        disabled>
                    <label for="bookingFor" id="labelBookingFor">Booking For:</label>
                    <select id="bookingFor">
                        <option value="Self">Self</option>
                        <option value="Others..">Others..</option>
                    </select>
                    <button id="continueButton" type="button" onclick="booking(event)">Continue</button>
                </form>
                <button onclick="hidePopup()">Close</button>
            </div>
        </div>
    </div>
</div>
<script src="./js/bookrestaurant.js"></script>
</body>

</html>