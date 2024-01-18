async function bookRestaurant(event) {
	event.preventDefault();
	var selectedRestaurant = document.getElementById('restaurant').value;
	var selectedTime = document.getElementById('date').value;
	console.log(selectedTime);
	var showAvailableDiv = document.getElementById('showAvailable');
	var existing = document.getElementById("foodTiming");
	if (existing != null) {
		showAvailableDiv.removeChild(existing);
	}
	if (selectedRestaurant == "" || selectedTime == "") {
		alert("Select the restaurant and Date");
	} else {
		var loader = document.createElement('div');
		loader.id = 'loader';
		showAvailableDiv.appendChild(loader);
		// Simulate an asynchronous request to the server (you should replace this with your actual AJAX request
		const url = "/test/FetchRestaurant";
		const response = await fetch(url, {
			method: "POST",
			headers: {
				"Content-Type": "application/json",
			},
			body: JSON.stringify({ "selectedRestaurant": selectedRestaurant, "selectedTime": selectedTime }),
		});
		setTimeout(async () => {
			if (response.status === 200) {
				const data = await response.json();
				addTable(data);
				showAvailableDiv.removeChild(loader);
			} else {
				showAvailableDiv.removeChild(loader);
				alert(await response.text());
			}
		}, 2000);
		// Add your logic to display the fetched results in the div
		//showAvailableDiv.innerHTML = '<p>Results from the server</p>';
	}
}

function addTable(availableTime) {
	var showAvailableDiv = document.getElementById('showAvailable');
	var existing = document.getElementById("foodTiming");
	if (existing != null) {
		showAvailableDiv.removeChild(existing);
	}

	var table = document.createElement('table');
	table.setAttribute("id", "foodTiming");

	var thead = table.createTHead();
	var headerRow = thead.insertRow(0);
	var checkboxHeader = headerRow.insertCell(0);
	var foodTimingHeader = headerRow.insertCell(1);
	var timeHeader = headerRow.insertCell(2);

	checkboxHeader.textContent = 'Select';
	foodTimingHeader.textContent = 'Food Timing';
	timeHeader.textContent = 'Time';

	var availabilityData = availableTime;

	var tbody = table.createTBody();
	for (var i = 0; i < availabilityData.length; i++) {
		var row = tbody.insertRow(i);

		var foodTimingCell = row.insertCell(0);
		foodTimingCell.textContent = availabilityData[i].foodTiming;

		var timeCell = row.insertCell(1);
		timeCell.textContent = availabilityData[i].time;

		var bookCell = row.insertCell(2);
		var bookButton = document.createElement('button');
		bookButton.className = "bookButton";
		bookButton.id = availabilityData[i].time;
		bookButton.textContent = 'Book';
		bookButton.value = availabilityData[i].time;
		bookButton.onclick = function() {
			handleBooking(this);
		};
		bookCell.appendChild(bookButton);
	}

	showAvailableDiv.appendChild(table);
}

function handleBooking(button) {
	var tableRow = button.parentNode.parentNode;
	var foodTiming = tableRow.cells[0].textContent;
	var time = tableRow.cells[1].textContent;
	var bookingFor = document.getElementById("bookingFor");
	var popup = document.getElementById("innerForm");
	if (bookingFor == null) {
		var nameLabel = document.getElementById('nameLabel');
		var nameInput = document.getElementById('name');

		var emailLabel = document.getElementById('emailLabel');
		var emailInput = document.getElementById('email');

		var submitButton = document.getElementById('bookRestaurant');

		popup.removeChild(nameLabel);
		popup.removeChild(nameInput);
		popup.removeChild(emailLabel);
		popup.removeChild(emailInput);
		popup.removeChild(submitButton);

		var label = document.createElement("label");
		label.setAttribute("for", "bookingFor");
		label.setAttribute("id", "labelBookingFor");
		label.textContent = "Booking For:";

		// Create select element
		var select = document.createElement("select");
		select.setAttribute("id", "bookingFor");

		// Create option elements and add them to the select
		var optionSelf = document.createElement("option");
		optionSelf.value = "Self";
		optionSelf.textContent = "Self";
		select.appendChild(optionSelf);

		var optionOthers = document.createElement("option");
		optionOthers.value = "Others";
		optionOthers.textContent = "Others";
		select.appendChild(optionOthers);

		// Create button element
		var button = document.createElement("button");
		button.setAttribute("id", "continueButton");
		button.setAttribute("type", "button");
		button.onclick = function() {
			booking(event);
		}
		button.textContent = "Continue";

		popup.append(label, select, button);
	}
	document.getElementById("popupForm").style.display = "block";
	document.getElementById("overlay").style.display = "block";
	document.getElementById("chosenTime").value = time;
	document.getElementById("food-timing").value = foodTiming;
	document.getElementById("restaurantName").value = document.getElementById('restaurant').value;
}

function booking(event) {
	event.preventDefault();
	var selected = document.getElementById("bookingFor").value;
	var bookingFor = document.getElementById("bookingFor");
	var label = document.getElementById("labelBookingFor");
	var popup = document.getElementById("innerForm");
	var continueButton = document.getElementById("continueButton");
	popup.removeChild(bookingFor);
	popup.removeChild(label);
	popup.removeChild(continueButton);
	if (selected == "Self") {

	} else {
		var nameLabel = document.createElement("label");
		nameLabel.setAttribute("for", "name");
		nameLabel.setAttribute("id", "nameLabel");
		nameLabel.textContent = "Name:";
		popup.appendChild(nameLabel);

		var nameInput = document.createElement("input");
		nameInput.setAttribute("type", "text");
		nameInput.setAttribute("id", "name");
		nameInput.setAttribute("name", "name");
		nameInput.setAttribute("required", "required");
		popup.appendChild(nameInput);

		// Create and append the Email input field
		var emailLabel = document.createElement("label");
		emailLabel.setAttribute("for", "email");
		emailLabel.setAttribute("id", "emailLabel");
		emailLabel.textContent = "Email:";
		popup.appendChild(emailLabel);

		var emailInput = document.createElement("input");
		emailInput.setAttribute("type", "email");
		emailInput.setAttribute("id", "email");
		emailInput.setAttribute("name", "email");
		emailInput.setAttribute("required", "required");
		popup.appendChild(emailInput);

		// Create and append the Submit button
		var submitButton = document.createElement("button");
		submitButton.setAttribute("type", "submit");
		submitButton.setAttribute("id", "bookRestaurant");
		submitButton.textContent = "Submit";
        submitButton.onclick=function (){
            submitBookingDetails(event);
        };
		popup.appendChild(submitButton);
	}
}

function hidePopup() {
	document.getElementById("popupForm").style.display = "none";
	document.getElementById("overlay").style.display = "none";
}

function submitBookingDetails(event) {
    event.preventDefault();
	// Retrieve values from the pop-up form
	var popupRestaurant = document.getElementById('popupRestaurant').value;
	var popupDate = document.getElementById('popupDate').value;
	var name = document.getElementById('name').value;
	var mobile = document.getElementById('mobile').value;

	// Perform actions with the captured values (e.g., send to the server)
	console.log('Restaurant Name:', popupRestaurant);
	console.log('Date:', popupDate);
	console.log('Name:', name);
	console.log('Mobile Number:', mobile);

	// Close the pop-up form
	document.getElementById('pop-up').style.display = 'none';
}

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