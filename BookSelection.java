

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import com.ibm.db2.jcc.am.Connection;

@WebServlet(
		name = "BookSelect", 
		urlPatterns = { "/bookselect" }, 
		initParams = { 
				@WebInitParam(name = "url", value = "*******"), 
				@WebInitParam(name = "username", value = "*****"), 
				@WebInitParam(name = "password", value = "*****")
		})
public class BookSelection extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Connection  connection = null;
	
	  public void init()
	    {
	    	ServletConfig config = getServletConfig();
			String url = config.getInitParameter("url");
			String user = config.getInitParameter("username");
			String password = config.getInitParameter("password");

			try {
				Class.forName("com.ibm.db2.jcc.DB2Driver");
				connection = DriverManager.getConnection(url, user, password);
			} catch (SQLException e) {
				e.printStackTrace();
			
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
	    }
	
	@Override 
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String bookname = request.getParameter("bookname");
		PreparedStatement pstmt = null;
		String sqlSelectQuery = "select BOOK_ID, BOOK_NAME, BOOK_STATUS from BOOKS where BOOK_NAME LIKE ?";
		PrintWriter out = response.getWriter();
		out.println("<html><head><title>Book Selection</title></head>");
		out.println("<body  bgcolor='cyan'>");
		
		try {
			if (connection != null)
				pstmt = connection.prepareStatement(sqlSelectQuery);
				System.out.println("connection success 1");
			if (pstmt != null) {
				pstmt.setString(1, "%" + bookname + "%");
				System.out.println("connection success 2");
				try {
				ResultSet resultSet = pstmt.executeQuery();
				System.out.println("connection success 3");
				
				if(resultSet.next())
				{
					    out.println("<table border='1' align='center'>");
					    out.println("<tr><th>Name</th><th>value</th></tr>");
						out.println("<tr>");
						out.println("<th>BOOK ID </th>");
						out.println("<td>" + resultSet.getInt(1) + "</td>");
						out.println("</tr>");
						out.println("<tr>");
						out.println("<th>BOOK NAME </th>");
						out.println("<td>" + resultSet.getString(2) + "</td>");
						out.println("</tr>");
						if (resultSet.getString(3).equalsIgnoreCase("N"))
						{
							out.println("<tr>");
							out.println("<th>BOOK AVAILABLE TO BORROW</th>");
						//	out.println("<td><input type='submit' value='BOOK' /></td>");
							out.println("<td><a style='text-align:center;' href='./BookSearch.html'>Search again</a> </td>");
							out.println("</tr>");
							out.println("</table>");
							
						}
						else
						{	
							out.println("<tr>");
							out.println("<th>OOPS, The book is already borrowed</th>");
							out.println("<td><a style='text-align:center;' href='./BookSearch.html'>Search again</a></td>");
							out.println("</tr>");
							out.println("</table>");
						}
				}
				else
				{
					out.println("<h1 style='color:red;text-align:center;'>Book Unavailable. Try searching again.</h1>");
					out.println("<a style='text-align:center;' href='./BookSearch.html'>Search again</a>");
				}
				out.println("</body>");
				out.println("</html>");
				out.close();
                }
				
				catch (SQLException se) {
						se.printStackTrace();
				}
			}

		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
	}

}
