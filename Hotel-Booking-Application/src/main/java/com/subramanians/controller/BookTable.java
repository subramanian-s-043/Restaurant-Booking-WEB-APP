package com.subramanians.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.time.LocalDate;
import java.time.LocalTime;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.subramanians.dao.Repository;

/**
 * Servlet implementation class BookTable
 */
@WebServlet("/BookTable")
public class BookTable extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Repository repo = Repository.getInstance();
		StringBuilder requestBody=new StringBuilder();
        int user_id=0;
        try (InputStream inputStream = request.getInputStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) 
        {
               String line;
               while ((line = reader.readLine()) != null) {
                   requestBody.append(line);
               }
        }
  		 Cookie[] cookies = request.getCookies();
	        if (cookies != null) {
	            for (Cookie cookie : cookies) {
	                if ("userData".equals(cookie.getName())) {
	                	String cookieData = URLDecoder.decode(cookie.getValue(),"UTF-8");
	                	JSONParser parser=new JSONParser();
	                	try {
							Object parsedObject=parser.parse(cookieData);
							JSONObject cookieJson = (JSONObject) parsedObject;
							user_id = Integer.valueOf(String.valueOf(cookieJson.get("userId")));
						} catch (ParseException e) {
							e.printStackTrace();
						}
	                	
	                }
	            }
	        }
        String restaurantName="";
        String name="";
        String email="";
        int numberOfPeople=0;
        LocalDate date=null;
        LocalTime time=null;
        try {
        	JSONParser parser=new JSONParser();
        	Object retrievedObj;
			retrievedObj = parser.parse(requestBody.toString());
        	JSONObject retirevedData=(JSONObject) retrievedObj;
        	restaurantName=(String) retirevedData.get("restaurantName");
        	name=(String) retirevedData.get("name");
        	email=(String) retirevedData.get("email");
        	numberOfPeople=Integer.valueOf((String)retirevedData.get("numberOfPeople"));
        	date=LocalDate.parse((String) retirevedData.get("chosenDate"));
        	time=LocalTime.parse((String) retirevedData.get("chosenTime"));
        }catch(ParseException e) {
        	
        }
        if(repo.book(name,restaurantName,numberOfPeople,date,time,user_id))
        {
        	response.setStatus(HttpServletResponse.SC_OK);
        	return;
        }
	}

}
