package com.subramanians.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.subramanians.dao.Repository;
import com.subramanians.dto.Customer;

/**
 * Servlet implementation class RetrieveCurrentUser
 */
@WebServlet("/RetrieveCurrentUser")
public class RetrieveCurrentUser extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Cookie[] cookies = request.getCookies();
		Customer current=null;
		for(Cookie cookie: cookies)
		{
			if("userData".equals(cookie.getName()))
			{
                String encodedJsonValue = cookie.getValue();
                String decodedJsonValue = URLDecoder.decode(encodedJsonValue, "UTF-8");
                response.setStatus(HttpServletResponse.SC_OK);
                PrintWriter out=response.getWriter();
                out.write(decodedJsonValue);
                return;
			}
		}
//		if(current!=null)
//		{
//			response.setContentType("application/json");
//			JSONObject convertToJSON = convertToJSON(current);
//			PrintWriter out=response.getWriter();
//			response.setStatus(HttpServletResponse.SC_OK);
//			out.write(convertToJSON.toJSONString());
//			return;
//		}
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
