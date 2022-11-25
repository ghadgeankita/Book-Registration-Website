package com.servlet.register;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/editScreen")
public class EditScreenServlet extends HttpServlet {
private static final long serialVersionUID = 1L;
	
	private static final String QUERY  = "SELECT BookName, BookEdition, BookPrice FROM BookData WHERE ID=?";
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		//get print writer
		PrintWriter pw =res.getWriter();
	
		//set content type
		res.setContentType("text/html");
		
		//get the id of record
		int id = Integer.parseInt(req.getParameter("id"));
		
		//load JDBC Drier
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		//Generate the connection
		try (Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/ankita","root","1234");
				PreparedStatement ps=con.prepareStatement(QUERY);){
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			rs.next();
			
			pw.println("<form action='editurl?id="+id+"' method='post'>");
				pw.println("<table align='center'>");
					pw.println("<tr>");
						pw.println("<td>Book Name</td>");
						pw.println("<td><input type='text' name='bookName' value='"+rs.getString(1)+"'></td>");
					pw.println("</tr>");
					
					pw.println("<tr>");
						pw.println("<td>Book Edition</td>");
						pw.println("<td><input type='text' name='bookEdition' value='"+rs.getString(2)+"'></td>");
					pw.println("</tr>");
					
					pw.println("<tr>");
						pw.println("<td>Book Price</td>");
						pw.println("<td><input type='text' name='bookPrice' value='"+rs.getFloat(3)+"'></td>");
					pw.println("</tr>");
					pw.println("<tr>");
					pw.println("<td><input type='submit' value='Edit'</td>");
					pw.println("<td><input type='reset' value='Cancel'</td>");
					pw.println("</tr>");
				pw.println("</table>");
			pw.println("</form>");
			
			pw.println("<br>");
			pw.println("<a href='bookList'>Book List</a>");
		}catch (SQLException e) {
			pw.println(e.getMessage());
			e.printStackTrace();
		}catch(Exception e) {
			pw.println(e.getMessage());
			e.printStackTrace();
		}
		
		pw.println("<a href='home.html'>Home</a>");
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doGet(req, res);
	}
}
