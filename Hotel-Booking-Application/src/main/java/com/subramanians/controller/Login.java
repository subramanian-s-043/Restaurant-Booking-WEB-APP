package com.subramanians.controller;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URLEncoder;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.subramanians.dao.Repository;
import com.subramanians.dto.Customer;



@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
    @SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
    	Repository repo=Repository.getInstance();
        response.setContentType("text/plain");
        StringBuilder requestBody = new StringBuilder();
        try (InputStream inputStream = request.getInputStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                requestBody.append(line);
            }
        }
        JSONParser parser=new JSONParser();
        String username="";
        String password="";
       
        try {
            Object obj = parser.parse(requestBody.toString());
            JSONObject jsonObject = (JSONObject) obj;
            username=(String) jsonObject.get("username");
            password=(String) jsonObject.get("password");
            
        }catch (Exception e) {
        	
		}
        if(repo.isValidUser(username, password))
        {
        	response.setStatus(HttpServletResponse.SC_OK);
            HttpSession session = request.getSession(true);
            session.setAttribute("username", username);
            session.setAttribute("isLoggedIn", true);
            Customer curr=repo.getCurrentCustomer();
            JSONObject cookieData=new JSONObject();
            cookieData.put("username", curr.getName());
            cookieData.put("userId", curr.getId());
            Cookie cookie = new Cookie("userData", URLEncoder.encode(cookieData.toString(), "UTF-8"));
            cookie.setPath("/");
            cookie.setMaxAge(-1);
            response.addCookie(cookie);
        }else if(username.equals("") || password.equals(""))
        {
        	response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        	PrintWriter out= response.getWriter();
        	out.print("JSON Parser Error!");
        }
        else {
        	response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        	PrintWriter out= response.getWriter();
        	out.print("Invalid Username/Password");
        }
    }
}
