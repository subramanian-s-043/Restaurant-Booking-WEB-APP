	<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Table Booking</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(to right, #3494e6, #ec6ead);
            margin: 0;
            padding: 0;
            display: flex;
            align-items: center;
            justify-content: center;
            height: 100vh;
        }

        .container {
            background-color: #fff;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            padding: 20px;
            width: 300px;
        }

        form {
            display: flex;
            flex-direction: column;
        }

        input {
            padding: 8px;
            margin-bottom: 10px;
            border: 1px solid #ccc;
            border-radius: 4px;
        }

        input[type="submit"] {
            background-color: #4caf50;
            color: white;
            cursor: pointer;
        }

        input[type="submit"]:hover {
            background-color: #45a049;
        }

        .error {
            color: red;
            font-size: 14px;
            margin-bottom: 10px;
        }

        .title {
            text-align: center;
            font-size: 30px;
            font-weight: 200;
            margin-bottom: 20px;
        }
        .formTitle {
            text-align: center;
            font-size: 18px;
            margin-bottom: 25px;
        }
        .sigin {
            text-align: center;
            font-size: 12px;
            margin-bottom: 20px;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="title">Welcome to Restaurant Table Booking</div>
        <!-- <div class="formTitle">Sign Form</div> -->
        <form id="loginForm" action="Login" method="post" onsubmit="return validateForm()">
            <label for="username">Username:</label>
            <input type="text" name="username" id="username" required>
            <span id="usernameError" class="error"></span>

            <label for="name">Name:</label>
            <input type="text" name="name" id="name" required>
            <span id="usernameError" class="error"></span>
            
            <label for="name">Mobile Number:</label>
            <input type="text" name="number" id="number" required>
            <span id="usernameError" class="error"></span>
            
            <label for="password">Password:</label>
            <input type="password" name="password" id="password" required>
            <span id="passwordError" class="error"></span>
		<input type="submit" value="Sign-in">
        </form>
        <div class="sigin">
            Already User?..<a href="login.jsp">Login</a>
        </div>
    </div>

    <script>
        function validateForm() {
            var username = document.getElementById('username').value;
            var password = document.getElementById('password').value;
            var isValid = true;

            // Validate Username
            if (username.trim() === "") {
                document.getElementById('usernameError').innerHTML = 'Username is required';
                isValid = false;
            } else {
                document.getElementById('usernameError').innerHTML = '';
            }

            // Validate Password
            if (password.trim() === "") {
                document.getElementById('passwordError').innerHTML = 'Password is required';
                isValid = false;
            } else {
                document.getElementById('passwordError').innerHTML = '';
            }

            return isValid;
        }
    </script>
</body>
</html>