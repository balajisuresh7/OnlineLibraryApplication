

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ResgisterServlet
 */
@WebServlet("/reg")
//		urlPatterns = { "/reg" }, 
//		initParams = { 
//				@WebInitParam(name = "url", value = "jdbc:db2://DSNTDRDA:446/DSNT"), 
//				@WebInitParam(name = "username", value = "b0s0d1r"), 
//				@WebInitParam(name = "password", value = "WALMART1")
//		})
public class ResgisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
//	private Connection connection = null;

    public void init()
    {
//    	ServletConfig config = getServletConfig();
//		String url = config.getInitParameter("url");
//		String user = config.getInitParameter("username");
//		String password = config.getInitParameter("password");
//
//		try {
//			Class.forName("com.ibm.db2.jcc.DB2Driver");
//			connection = DriverManager.getConnection(url, user, password);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		}
    }
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{	
		Integer sid = Integer.parseInt(request.getParameter("sid"));
		String fname = request.getParameter("fname");
		String lname = request.getParameter("lname");
		String scourse = request.getParameter("scourse");
		String sdept = request.getParameter("sdept");
		String syear = request.getParameter("syear");
		String semail = request.getParameter("semail");
		String spwd = request.getParameter("spwd");
		String cpwd = request.getParameter("cpwd");
		
		//New code j20 
		
		QueryRepository qr = new QueryRepository();
		
		qr.setCpwd(cpwd);
		qr.setFname(fname);
		qr.setLname(lname);
		qr.setScourse(scourse);
		qr.setSdept(sdept);
		qr.setSemail(semail);
		qr.setSid(sid);
		qr.setSpwd(spwd);
		qr.setSyear(syear);
		
		if (spwd.equals(cpwd) == false)
		{	
			response.sendError(504, "Passwords doesnt match. Please try again");
		}
//		PreparedStatement pstmt = null;
//		String sqlInsertQuery = "insert into b0s0d1r.student "
//				+ "(STUDENT_ID,STUDENT_FNAME,STUDENT_LNAME,STUDENT_COURSE,STUDENT_DEPT,STUDENT_YEAR,STUDENT_EMAIL,STUDENT_PWD) values(?,?,?,?,?,?,?,?)";
//		
		int rowCount = qr.registerNewUser();
		
//		try {
//			if (connection != null)
//				pstmt = connection.prepareStatement(sqlInsertQuery);
//			if (pstmt != null) {
//				pstmt.setInt(1, sid);
//				pstmt.setString(2,fname);
//				pstmt.setString(3,lname);
//				pstmt.setString(4,scourse);
//				pstmt.setString(5,sdept);
//				pstmt.setString(6,syear);
//				pstmt.setString(7,semail);
//				pstmt.setString(8,spwd);
//
//				rowCount = pstmt.executeUpdate();
//			}
//
//		} catch (SQLException se) {
//			se.printStackTrace();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}

		// 3. Prepare a response and send it to the end user
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		out.println("<html><head><title>OUTPUT</title></head>");
		out.println("<body>");
		if (rowCount == 1)
		{
			out.println("<h1 style='color:green;text-align:center;'>REGISTERED SUCCESFULLY</h1>");
		    out.println("<a style='text-align:center;' href='./login.html'>Click to login</a>");
		}
		else {
			out.println("<h1 style='color:red;text-align:center;'>REGISTRATION FAILED</h1>");
			out.println("<a style='text-align:center;' href='./Registration.html'>register again</a>");
		}

		out.println("</body>");
		out.println("</html>");
		out.close();

		
	}

}
