

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
import javax.servlet.http.HttpSession;

@WebServlet("/log")

public class LogininfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public void init()
    {

    }
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		Integer sid = Integer.parseInt(request.getParameter("sid"));
		String password = request.getParameter("spwd");
		HttpSession sp = request.getSession();
		sp.setAttribute("sid", sid);
		
		boolean LoginSuccess = false;
		System.out.println("sid-A" + sid);
		System.out.println(sid);
		System.out.println(password);
		
		QueryRepository qr = new QueryRepository();
		qr.setSid(sid);
		qr.setSpwd(password);
		
		LoginSuccess = qr.loginUser();
		

		PrintWriter out = response.getWriter();
	
		if (LoginSuccess == true)
		{
			out.println("<h1 style='color:green;text-align:center;'>Login Successful</h1>");
			response.sendRedirect("/LibraryApplication/BookSearch.html");
	//		RequestDispatcher rd = request.getRequestDispatcher("/BookSearch.html");
	//		rd.forward(request, response);
		}
		else
			{
				out.println("<h1 style='color:red;text-align:center;'>Username or password is incorrect</h1>");
				out.println("<a style='text-align:center;' href='./login.html'>Login again</a>");
			}
			out.println("</body>");
			out.println("</html>");
			out.close();
		
		
	}

}
