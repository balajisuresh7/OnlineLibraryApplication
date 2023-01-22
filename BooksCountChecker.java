import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BooksCountChecker
{
	public static boolean maxNoOfBooksBorrowed(Connection connection,int sid)
	{
		int noOfBooks = 0;
		String sqlSelectQuery = "select count(*) from BOOKS where STUDENT_ID = ?";
		PreparedStatement pstmt = null;
		System.out.println(connection);
		try {
			if (connection != null)
			pstmt = connection.prepareStatement(sqlSelectQuery);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			pstmt.setInt(1, sid);
			ResultSet resultSet = pstmt.executeQuery();
			if (resultSet.next())
			{
				 noOfBooks = resultSet.getInt(1) ;
				 System.out.println(noOfBooks);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		if (noOfBooks >= 3)
		    return true;
		else
			return false;
	}
}
