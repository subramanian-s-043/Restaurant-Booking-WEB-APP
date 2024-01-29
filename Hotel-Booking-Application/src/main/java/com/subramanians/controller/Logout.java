package com.subramanians.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Logout
 */
@WebServlet("/Logout")
public class Logout extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session=request.getSession(false);
        if (session != null) {
   		 Cookie[] cookies = request.getCookies();
	        if (cookies != null) {
	            for (Cookie cookie : cookies) {
	                if ("userData".equals(cookie.getName())) {
	                    Cookie deleteCookie = new Cookie("userData", "");
	                    deleteCookie.setMaxAge(0);
	                    deleteCookie.setPath("/");
	                    response.addCookie(deleteCookie);
	                }
	            }
	        }
            session.invalidate();
            response.sendRedirect("login.jsp");
        }
        response.setStatus(HttpServletResponse.SC_OK);
	}

}
