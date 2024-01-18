package com.subramanians.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.subramanians.dao.Repository;
import com.subramanians.dto.Customer;

/**
 * Servlet implementation class RetrieveCurrentUser
 */
@WebServlet("/RetrieveCurrentUser")
public class RetrieveCurrentUser extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Repository repo=Repository.getInstance();
		Customer current=repo.getCurrentCustomer();
		if(current!=null)
		{
			response.setContentType("application/json");
			JSONObject convertToJSON = convertToJSON(current);
			PrintWriter out=response.getWriter();
			response.setStatus(HttpServletResponse.SC_OK);
			out.write(convertToJSON.toJSONString());
			return;
		}
	}

	@SuppressWarnings("unchecked")
	private JSONObject convertToJSON(Customer current) {
		JSONObject data=new JSONObject();
		
		data.put("name", current.getName());
		data.put("username", current.getUsername());
		data.put("id",String.valueOf(current.getId()));
		
		return data;
	}

}
