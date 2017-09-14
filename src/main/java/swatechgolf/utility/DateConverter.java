package swatechgolf.utility;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.stereotype.Component;

@Component
public class DateConverter {
	public DateTime fromString(String dateTimeString) {
		String[] dateParts = dateTimeString.split("\\s+");
		String day = dateParts[1];
		String month = dateParts[2];
		String year = dateParts[3];
		String time = dateParts[4];
		DateTimeFormatter formatter = DateTimeFormat.forPattern("dd-MMM-YYYY HH:mm:ss");
		formatter.parseDateTime(day + "-" + month + "-" + year + " " + time);
		DateTime dateTime = formatter.parseDateTime(day + "-" + month + "-" + year + " " + time);
		return dateTime;
	}

	public String toString(DateTime dateTime) {
		DateTimeFormatter formatter = DateTimeFormat.forPattern("YYYY-MM-dd HH.mm.ss");
		return formatter.print(dateTime);
	}
}
