import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FineCalculator
{
	public static Integer daysDifferenceCalculator(String d1, String d2) throws ParseException
	{
		SimpleDateFormat obj = new SimpleDateFormat("yyyy-MM-dd");   
		Integer fine_amount = 0;
		Date date1 = obj.parse(d1);
		Date date2 = obj.parse(d2);
		
		long time_diff = date1.getTime() - date2.getTime();
		
		int days_diff =  (int) ((time_diff / (1000*60*60*24)) % 365);  
		
		if (days_diff > 0) 
		{
			fine_amount = 10 * days_diff;
		}
		
		return fine_amount;
		
		
	}

}
