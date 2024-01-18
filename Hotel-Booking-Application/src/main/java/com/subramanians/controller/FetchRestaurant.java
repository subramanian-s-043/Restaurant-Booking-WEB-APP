package com.subramanians.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.subramanians.dao.Repository;

/**
 * Servlet implementation class FetchRestaurant
 */
@WebServlet("/FetchRestaurant")
public class FetchRestaurant extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	Repository repo=Repository.getInstance();
        response.setContentType("application/json");
        StringBuilder requestBody = new StringBuilder();
        try (InputStream inputStream = request.getInputStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                requestBody.append(line);
            }
        }
        JSONParser parser=new JSONParser();
        String restaurantName="";
        LocalDate selectedDate=null;
        try {
        	Object obj=parser.parse(requestBody.toString());
        	JSONObject retrieved=(JSONObject) obj;
        	restaurantName=(String)retrieved.get("selectedRestaurant");
        	selectedDate=LocalDate.parse((String)retrieved.get("selectedTime"));
        }catch(Exception e) {
        	
        }
        if(restaurantName=="" || selectedDate==null)
        {
        	response.setContentType("text/plain");
        	PrintWriter out=response.getWriter();
        	out.print("Internal Server Error!");
        	return;
        }else {
        	List<LocalTime> availableTime=new ArrayList<>();
        	LocalTime startTime = LocalTime.of(9, 0);
            LocalTime endTime = LocalTime.of(22, 0);
            int intervalMinutes = 30;
            LocalTime currentTime;
            if(selectedDate.isEqual(LocalDate.now()))
            {
            	if(LocalTime.now().getMinute() >= 30)
            	{
            		int temp=LocalTime.now().getHour()+1;
            		currentTime=LocalTime.of(temp,0);
            	}else {
            		currentTime=LocalTime.of(LocalTime.now().getHour(),30);
            	}
            }else {
            	currentTime=startTime;
            }
            while (currentTime.isBefore(endTime) || currentTime.equals(endTime)) {
                availableTime.add(currentTime);
                currentTime = currentTime.plusMinutes(intervalMinutes);
                for(LocalTime t:repo.retrieveAvailability(restaurantName, selectedDate))
                {
                	availableTime.remove(t);
                }
            }
            JSONArray convertToJSON = generateJsonData(availableTime);
            PrintWriter out=response.getWriter();
            out.write(convertToJSON.toJSONString());
            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }
	}

	@SuppressWarnings("unchecked")
	private JSONArray generateJsonData(List<LocalTime> availableTime) {
        JSONArray jsonData = new JSONArray();
        JSONObject data = new JSONObject();
        for (int i = 0; i < availableTime.size(); i++) {
            if(isBreakfastTime(availableTime.get(i)))
            {
            	data.put("foodTiming", "Break-fast");
            	data.put("time",String.valueOf(availableTime.get(i)));
                jsonData.add(data);
                data=new JSONObject();
            }else if(isLunchTime(availableTime.get(i)))
            {
            	data.put("foodTiming", "Lunch");
            	data.put("time",String.valueOf(availableTime.get(i)));
                jsonData.add(data);
                data=new JSONObject();
            }else if(isDinnerTime(availableTime.get(i)))
            {
            	data.put("foodTiming", "Dinner");
            	data.put("time", String.valueOf(availableTime.get(i)));
                jsonData.add(data);
                data=new JSONObject();
            }
        }
        
		return jsonData;
	}

	private boolean isDinnerTime(LocalTime t) {
		if(t.isAfter(LocalTime.of(18, 30)) && t.isBefore(LocalTime.of(22, 0)))
		{
			return true;
		}
		return false;
	}

	private boolean isLunchTime(LocalTime t) {
		if(t.isAfter(LocalTime.of(11, 30)) && t.isBefore(LocalTime.of(16, 0)))
		{
			return true;
		}
		return false;
	}

	private boolean isBreakfastTime(LocalTime t) {
		if(t.isAfter(LocalTime.of(8, 0)) && t.isBefore(LocalTime.of(11, 0)))
		{
			return true;
		}
		return false;
	}

}
