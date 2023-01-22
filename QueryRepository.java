import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;

//import TestProject.JDBCConnection;

public class QueryRepository
{
	private Integer sid ;
	private String fname ;
	private String lname;
	private String scourse; 
	private String sdept ;
	private String syear ;
	private String semail;
	private String spwd;
	private String cpwd ;
	private String bookname;
	private Integer bookid;
	private String bookstatus;
	private String bookduedate;
	private String bookborrowdate;
	private Connection connection = null;
	private PreparedStatement pstmt = null;
	
	public Integer getSid() {
		return sid;
	}
	public void setSid(Integer sid) {
		this.sid = sid;
	}
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public String getLname() {
		return lname;
	}
	public void setLname(String lname) {
		this.lname = lname;
	}
	public String getScourse() {
		return scourse;
	}
	public void setScourse(String scourse) {
		this.scourse = scourse;
	}
	public String getSdept() {
		return sdept;
	}
	public void setSdept(String sdept) {
		this.sdept = sdept;
	}
	public String getSyear() {
		return syear;
	}
	public void setSyear(String syear) {
		this.syear = syear;
	}
	public String getSemail() {
		return semail;
	}
	public void setSemail(String semail) {
		this.semail = semail;
	}
	public String getSpwd() {
		return spwd;
	}
	public void setSpwd(String spwd) {
		this.spwd = spwd;
	}
	public String getCpwd() {
		return cpwd;
	}
	public void setCpwd(String cpwd) {
		this.cpwd = cpwd;
	}
	public String getBookname() {
		return bookname;
	}
	public void setBookname(String bookname) {
		this.bookname = bookname;
	}
	public Integer getBookid() {
		return bookid;
	}
	public void setBookid(Integer bookid) {
		this.bookid = bookid;
	}
	public String getBookstatus() {
		return bookstatus;
	}
	public void setBookstatus(String bookstatus) {
		this.bookstatus = bookstatus;
	}
	public String getBookduedate() {
		return bookduedate;
	}
	public void setBookduedate(String bookduedate) {
		this.bookduedate = bookduedate;
	}
	public String getBookborrowdate() {
		return bookborrowdate;
	}
	public void setBookborrowdate(String bookborrowdate) {
		this.bookborrowdate = bookborrowdate;
	}
	public Connection getConnection() {
		return connection;
	}
	public void setConnection(Connection connection) {
		this.connection = connection;
	}
	public PreparedStatement getPstmt() {
		return pstmt;
	}
	public void setPstmt(PreparedStatement pstmt) {
		this.pstmt = pstmt;
	}

	
	public int registerNewUser()
	{
		try {
			connection = JDBCConnection.getJdbcConnection();
			System.out.println(connection);
		} catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		} 
		
		String sqlInsertQuery = "insert into b0s0d1r.student "
				+ "(STUDENT_ID,STUDENT_FNAME,STUDENT_LNAME,STUDENT_COURSE,STUDENT_DEPT,STUDENT_YEAR,STUDENT_EMAIL,STUDENT_PWD) values(?,?,?,?,?,?,?,?)";
		System.out.println("control passed this step");
		int rowCount = 0;
		try {
			if (connection != null)
			{
				pstmt = connection.prepareStatement(sqlInsertQuery);
				System.out.println("pstmt " + pstmt);
			}
			if (pstmt != null) {
				pstmt.setInt(1, sid);
				pstmt.setString(2,fname);
				pstmt.setString(3,lname);
				pstmt.setString(4,scourse);
				pstmt.setString(5,sdept);
				pstmt.setString(6,syear);
				pstmt.setString(7,semail);
				pstmt.setString(8,spwd);
				
				rowCount = pstmt.executeUpdate();
				System.out.println("rowCOUNT " + rowCount );
			}

		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rowCount;
		
	}
	
	public boolean loginUser()
	{	
		try {
			connection = JDBCConnection.getJdbcConnection();
			System.out.println(connection);
		} catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		} 
		
		String sqlSelectQuery = "select STUDENT_ID,STUDENT_PWD from student where STUDENT_ID = ? and STUDENT_PWD = ?";
		
		try {
			if (connection != null)
				pstmt = connection.prepareStatement(sqlSelectQuery);
				System.out.println("connection success");
			if (pstmt != null) {
				pstmt.setInt(1, sid);
				pstmt.setString(2, spwd);
				try {
				ResultSet resultSet = pstmt.executeQuery();
				
				if(resultSet.next())
				{
					return true;
				}
				else
				{
					return false;
				}
				
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
		return false;
	}
	
	public boolean isBookAvailable(Integer bookid)
	{
		try {
			connection = JDBCConnection.getJdbcConnection();
			System.out.println(connection);
		} catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		} 
		System.out.println("in isbookavailable");
		String sqlSelectQuery = "select BOOK_ID, BOOK_NAME, BOOK_STATUS from BOOKS where BOOK_ID = ?";
		if (connection != null)
			try {
				pstmt = connection.prepareStatement(sqlSelectQuery);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		if (pstmt != null) {
			try {
				pstmt.setInt(1, bookid);
				System.out.println("bookid " + bookid);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				ResultSet resultSet = pstmt.executeQuery();
				if (resultSet.next())
				{
					bookstatus = resultSet.getString(3);
					bookid = resultSet.getInt(1);
					System.out.println("bookstatus " + bookstatus);
					System.out.println("bookid " + bookid);
					if (bookstatus.equalsIgnoreCase("N"))
						return true;
					else
						return false;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}		
		return false;
	}
	public boolean isMaxBooksBorrowed(Integer sid)
	{

		try {
			connection = JDBCConnection.getJdbcConnection();
			System.out.println(connection);
		} catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		} 
		
		boolean isMaxBooksBorrowed = BooksCountChecker.maxNoOfBooksBorrowed(connection, sid);
		return isMaxBooksBorrowed ;
	}
	public int confirmBooking(int sid) throws SQLException 
	{
		try {
			connection = JDBCConnection.getJdbcConnection();
			System.out.println(connection);
		} catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		} 
		
		Date date = new Date();
		long ltime = date.getTime()+15*24*60*60*1000;
        String sqlUpdateQuery = "Update books set BOOK_STATUS = ?, STUDENT_ID = ?, BORROW_DT = ?, DUE_DT = ? where BOOK_ID = ?";
        
        try {
			pstmt = connection.prepareStatement(sqlUpdateQuery);
		} catch (SQLException e) {
			e.printStackTrace();
		} 
        pstmt.setString(1, "Y");
        pstmt.setInt(2, sid);
        pstmt.setDate(3, new java.sql.Date(date.getTime()));
        pstmt.setDate(4,  new java.sql.Date(ltime));
		pstmt.setInt(5, bookid);
		
        int noOfRows = pstmt.executeUpdate();
        
		return noOfRows;
	}
	public ResultSet displayAllTheBooksBorrowed(Integer sid) throws SQLException
	{
		ResultSet resultSet =null;
		try {
			connection = JDBCConnection.getJdbcConnection();
			System.out.println(connection);
		} catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		} 
		String sqlSelectQuery = "select BOOK_ID, BOOK_NAME, BORROW_DT, DUE_DT from BOOKS where STUDENT_ID= ?";
		if (connection != null)
		{
			try {
				pstmt = connection.prepareStatement(sqlSelectQuery);
				pstmt.setInt(1, sid);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (pstmt!=null)
			resultSet = pstmt.executeQuery();  
		return resultSet;
	}

}
