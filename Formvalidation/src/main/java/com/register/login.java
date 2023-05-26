package com.register;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mysql.cj.xdevapi.PreparableStatement;


@WebServlet("/login")
public class login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		String email = request.getParameter("username");
		String password = request.getParameter("password");
		HttpSession session=request.getSession();
		RequestDispatcher dispatcher = null;
		
		if(email == null || email.equals("")) {
			request.setAttribute("status","invalidEmail");
			  dispatcher=request.getRequestDispatcher("login.jsp");
			  dispatcher.forward(request, response);
		}
		if(password == null || password.equals("")) {
			request.setAttribute("status","invalidPassword");
			  dispatcher=request.getRequestDispatcher("login.jsp");
			  dispatcher.forward(request, response);
		}
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/formvalidation?useSSL=false","root","Pb7411403782");
			PreparedStatement pst=con.prepareStatement("select * from users where email=? and password=?");
			pst.setString(1, email);
			pst.setString(2, password);
			  ResultSet rs=pst.executeQuery();
			  if(rs.next()) {
				  session.setAttribute("name",rs.getString("name"));
				  dispatcher=request.getRequestDispatcher("index.jsp");
			  }
			  else {
				  request.setAttribute("status", "failed");
				  dispatcher=request.getRequestDispatcher("login.jsp");
			  }
			  dispatcher.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
