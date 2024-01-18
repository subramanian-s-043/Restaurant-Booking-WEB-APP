<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="javax.servlet.*" %>
    <%
	HttpSession isactive=request.getSession(false);
	if (isactive == null || isactive.getAttribute("isLoggedIn")==null) 
	{
    	response.sendRedirect("login.jsp");
	}
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Restaurant Table Booking</title>
    <script src="https://cdn.jsdelivr.net/npm/fullcalendar@6.1.10/index.global.min.js"></script>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f4f4f4;
        }

        #container {
            display: flex;
            height: 100hv;
        }

        #sidebar {
            width: 250px;
            background-color: #2c3e50;
            color: #ecf0f1;
            padding-top: 20px;
            padding-bottom: 20px;
            display: flex;
            flex-direction: column;
            align-items: center;
        }

        #sidebar h2 {
            margin-bottom: 20px;
            font-size: 1.5em;
        }
        h2{
            margin: 0;
            padding: 0;
        }
        #sidebar ul {
            margin: 0;
            list-style: none;
            padding: 0;
        }

        #sidebar li {
            margin-bottom: 10px;
        }

        #sidebar a {
            text-decoration: none;
            color: #ecf0f1;
            font-size: 1.2em;
            transition: color 0.3s ease;
        }

        #sidebar a:hover {
            color: #3498db;
        }

        #content {
            flex: 1;
            padding: 20px;
        }

        #navbar {
            background-color: #3498db;
            color: #ecf0f1;
            padding: 10px;
            text-align: right;
        }

        #navbar a {
            color: #ecf0f1;
            text-decoration: none;
            margin-left: 20px;
            font-size: 1.2em;
            transition: color 0.3s ease;
        }
        #sidebar button {
        	background-color: #2c3e50;
    		border: none;
            text-decoration: none;
            color: #ecf0f1;
            font-size: 1.2em;
            transition: color 0.3s ease;
        }
        
        #sidebar button:hover {
        cursor: pointer;
        color: #3498db;
        }
        
        #navbar a:hover {
            color: #2c3e50;
        }

        #dashboard {
            background-color: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
		#calendar {
  		max-width: 950px;
  		height: 100hv;
  		margin: 40px auto;
		}
        h1 {
            color: #333;
            margin-bottom: 20px;
        }

        p {
            line-height: 1.6;
            color: #555;
        }
        #sidebarElements{
            display: flex ;
            flex-direction: column;
            column-gap: 15px;
        }
    </style>
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
            <h1>Welcome to the Dashboard</h1>
        <div id="calendar"></div>
        </div>
    </div>
</div>

</body>
<script>
document.addEventListener('DOMContentLoaded', function() {
	var currentDate=new Date();
    var calendarEl = document.getElementById('calendar');
    var calendar = new FullCalendar.Calendar(calendarEl, {
      height: 500,

      selectable: true,
      select: function(start) {
    	  var chosenDate=new Date(start.startStr);
    	  if(chosenDate.getDate() <= currentDate.getDate() + 10){
    		  	alert("You Chosen: " + start.startStr);
    	}else{
    		alert("Booking Allowed Only For next 10 Days");
    	}
      },
      buttonText:
      {
      today: 'Today',
      month: 'Month',
      week: 'Week',
      day: 'Day',
      list: 'List'
      }
    });
    calendar.render();
  });
async function logout(event){
	event.preventDefault();
	const url="http://localhost:3000/test/Logout"
	const response=await fetch(url,{
		method:"POST"
	})
	if(response.status===200){
		alert("Logout-Successfull");
		window.location.href="login.jsp";
	}
}
</script>
</html>

<!--       validRange: function(currentDate) {
    	    // Generate a new date for manipulating in the next step
    	    var startDate = new Date(currentDate.valueOf());
    	    var endDate = new Date(currentDate.valueOf());

    	    // Adjust the start & end dates, respectively
    	    startDate.setDate(startDate.getDate()); // One day in the past
    	    endDate.setDate(endDate.getDate() + 10); // Two days into the future

    	    return { start: startDate, end: endDate };
    	  }, -->