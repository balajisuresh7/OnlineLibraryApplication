

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class BooksCartDisplay
 */
@WebServlet("/bookdisplay")
public class BooksCartDisplay extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession sp = request.getSession();
		Integer sid = (Integer) sp.getAttribute("sid");
		ResultSet resultSet = null;
		QueryRepository qr = new QueryRepository();
		try {
			resultSet = qr.displayAllTheBooksBorrowed(sid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		response.setContentType("text/html");

		PrintWriter out = response.getWriter();
		out.println("<html><head><title>Output</title></head>");
		out.println("<body bgcolor='cyan' align='center'>");
	//	out.println("<body  bgcolor='cyan'>");
		out.println("<table border='1' align='center'>");
		out.println("<tr><th>Book ID</th><th>Book Name</th><th>Borrow Date</th><th>Due date</th><th>Fine Amount in INR </th></tr>");
		
		
		
		String d1 = java.time.LocalDate.now().toString();
		Integer fineAmount = 0;

		try {
			while (resultSet.next()) {
				
				
				String d2 = resultSet.getDate(4).toString();
				try {
					fineAmount = FineCalculator.daysDifferenceCalculator(d1,d2);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				out.println("<tr>");
				out.println("<td>" + resultSet.getInt(1) + "</td><td>" + resultSet.getString(2) + "</td><td>" + resultSet.getDate(3) +"</td><td>" + resultSet.getDate(4)+"</td><td>" + fineAmount+  "</td>");
				out.println("</tr>");
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		out.println("</table>");
		out.println("<a style='text-align:center;' href='./BookSearch.html'>Go back to book search page </a>");
		out.println("</body>");
		out.println("</html>");
		out.close();

	}

}
