

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

public class ResgisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


    public void init()
    {

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
		
		int rowCount = qr.registerNewUser();
		


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
