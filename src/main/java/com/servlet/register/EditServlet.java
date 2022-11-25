package com.servlet.register;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/editurl")
public class EditServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private static final String QUERY  = "UPDATE BookData set BookName=?, BookEdition=?, BookPrice=? WHERE ID=?";
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		//get print writer
		PrintWriter pw =res.getWriter();
	
		//set content type
		res.setContentType("text/html");
		
		//get the id of record
		int id = Integer.parseInt(req.getParameter("id"));
		
		//get the edited data which we want to delete
		String bookName = req.getParameter("bookName");
		String bookEdition = req.getParameter("bookEdition");
		float bookPrice = Float.parseFloat(req.getParameter("bookPrice"));
		
		//load JDBC Drier
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		//Generate the connection
		try (Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/ankita","root","1234");
				PreparedStatement ps=con.prepareStatement(QUERY);){
			ps.setString(1, bookName);
			ps.setString(2, bookEdition);
			ps.setFloat(3, bookPrice);
			ps.setInt(4,  id);
			
			int count = ps.executeUpdate();
			
			if(count==1) {
				pw.println("<h2>Record is Edited Succcessfully!</h2>");
			}else {
				pw.println("<h2>Record is not Edited Succcessfully!</h2>");
			}
		}catch (SQLException e) {
			pw.println(e.getMessage());
			e.printStackTrace();
		}catch(Exception e) {
			pw.println(e.getMessage());
			e.printStackTrace();
		}
		
		pw.println("<a href='home.html'>Home</a>");
		pw.println("<br>");
		pw.println("<a href='bookList'>Book List</a>");
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doGet(req, res);
	}
}
