

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/bookconfirm")

public class BooksConfirmation extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	  public void init()
	    {
	    }

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		Integer bookid = Integer.parseInt(request.getParameter("bookid"));
		boolean isBookAvailable = false;
		PrintWriter out = response.getWriter();
		out.println("<html><head><title>Book Selection</title></head>");
		out.println("<body bgcolor='cyan'>");
		System.out.println("book confirmation logic");
		QueryRepository qr = new QueryRepository();
		
		qr.setBookid(bookid);
		isBookAvailable = qr.isBookAvailable(bookid);
		System.out.println(isBookAvailable);
		if (isBookAvailable == true)
		{
			HttpSession sp = request.getSession();
			Integer sid = (Integer) sp.getAttribute("sid");
			boolean isMaxBooksBorrowed = qr.isMaxBooksBorrowed(sid);
			
			if (isMaxBooksBorrowed == false)
			{
				try {
					int noOfRowsAffected = qr.confirmBooking(sid);
					if (noOfRowsAffected > 0)
					{
						System.out.println("book status updated");
			        	out.println("<h1 style = 'color:green;text-align:center;'>Your book has been successfully reserved.</h1>");
			            out.println("<h1 style = 'color:green;text-align:center;'>Please collect the same at the Library</h1>");
			        	out.println("<td><a style='text-align:center;' href='./BookSearch.html'>Search again</a></td>");
					}
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
			}
			else
			{
				out.println("<h1 style = 'color:red;text-align:center;'>We are sorry. You have already reserved maximum number of books.</h1>");
	        	out.println("<td><a style='text-align:center;' href='./BookSearch.html'>Search again</a></td>");
			}
		}
		else
		{
			out.println("<h1 style = 'color:red;text-align:center;'>The Book is already borrowed.Try a different book</h1>");
        	out.println("<td><a style='text-align:center;' href='./BookSearch.html'>Search again</a></td>");
		}
		out.println("</body>");
		out.println("</html>");
		out.close();
		
	}

}


