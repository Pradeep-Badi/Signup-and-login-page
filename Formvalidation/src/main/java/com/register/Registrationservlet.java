package com.register;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.cj.jdbc.Driver;

/**
 * Servlet implementation class Registrationservlet
 */
@WebServlet("/register")
public class Registrationservlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

       

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	String name = request.getParameter("name");
	String email = request.getParameter("email");
	String password = request.getParameter("password");
	String Repassword=request.getParameter("re_pass");
	String phno = request.getParameter("contact");
	String Check=request.getParameter("agree-term");
	
	
	RequestDispatcher dispatcher=null;
	Connection con=null;
	
	if(name == null || name.equals("")) {
		request.setAttribute("status","invalidName");
		  dispatcher=request.getRequestDispatcher("registration.jsp");
		  dispatcher.forward(request, response);
	}
	if(email == null || email.equals("")) {
		request.setAttribute("status","invalidEmail");
		  dispatcher=request.getRequestDispatcher("registration.jsp");
		  dispatcher.forward(request, response);
	}
	if(password == null || password.equals("")) {
		request.setAttribute("status","invalidPassword");
		  dispatcher=request.getRequestDispatcher("registration.jsp");
		  dispatcher.forward(request, response);
	}
	else if(!password.equals(Repassword)) {
		request.setAttribute("status","invalidconfirmPassword");
		  dispatcher=request.getRequestDispatcher("registration.jsp");
		  dispatcher.forward(request, response);
		
	}
	if(phno == null || phno.equals("")) {
		request.setAttribute("status"," ");
		  dispatcher=request.getRequestDispatcher("registration.jsp");
		  dispatcher.forward(request, response);
	}
	else if(phno.length() >10 || phno.length() <10) {
		request.setAttribute("status","Enter valid phno");
		  dispatcher=request.getRequestDispatcher("registration.jsp");
		  dispatcher.forward(request, response);
	}
	if(Check == null) {
		request.setAttribute("status","confirm");
		  dispatcher=request.getRequestDispatcher("registration.jsp");
		  dispatcher.forward(request, response);
	}
	
	try {
		Class.forName("com.mysql.cj.jdbc.Driver");
		 con=DriverManager.getConnection("jdbc:mysql://localhost:3306/formvalidation?useSSL=false","root","Pb7411403782");
		PreparedStatement pst=con.prepareStatement("insert into users(name,password,email,phno) values(?,?,?,?) ");
		pst.setString(1, name);
		pst.setString(2, password);
		pst.setString(3, email);
		pst.setString(4, phno);
		
		int rowCount=pst.executeUpdate();
		dispatcher = request.getRequestDispatcher("registration.jsp");
		
		if(rowCount > 0) {
			request.setAttribute("status", "success");	
		}
		else {
			request.setAttribute("status", "failed");
		}
		dispatcher.forward(request, response);
	
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	finally {
		try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
		
	}

}
