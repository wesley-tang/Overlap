package overlap.project.scheduler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * DateRange contains the start and end of a range of time, specifying year, month, day, hour,
 * minute and second.
 */
public class DateRange {

	// Start and end of the date range as a string in iCalendar format
	private String start;
	private String end;

	// Start and end of the date range as Date objects
	private Date startDate;
	private Date endDate;

	// The pattern by which to convert iCalnedar times to Date objects
	private final String datePattern = "yyyyMd'T'Hms";

	// Check this flag and do not use if false, meaning dates failed to parse and are null or incorrect.
	private boolean correctlyParsed;

	/**
	 * Creates a new DateRange from the given start and end. Must be written in iCalendar format
	 *
	 * @param start String containing the beginning of range in iCalendar format.
	 * @param end String containing the end of range in iCalendar format.
	 */
	public DateRange(String start, String end){
		this.start = start;
		this.end = end;

		// Obtains the start and end dates from the provided string, while tracking if conversion was successful
		correctlyParsed = convertToDates();
	}

	// Attempts to parse the iCalendar strings to produce Date objects, returning whether it was successful
	private boolean convertToDates() {
		// Define the date format used by the iCalander standard (no timezone)
		SimpleDateFormat icsFormat = new SimpleDateFormat(datePattern, Locale.CANADA);

		// Convert the strings to dates.
		try {
			startDate = icsFormat.parse(start);
		} catch (ParseException e) {
			System.out.println("Failed to parse: " + start);
			e.printStackTrace();
			return false;
		}
		try {
			endDate = icsFormat.parse(end);
		} catch (ParseException e) {
			System.out.println("Failed to parse: " + end);
			e.printStackTrace();
			return false;
		}

		return true;
	}

	/**
	 * Get the beginning of the date range
	 * @return startDate the Date object of the start
	 */
	public Date getStart(){
		return startDate;
	}

	/**
	 * Get the ending of the date range.
	 * @returns endDate the Date object of the end
	 */
	public Date getEnd(){
		return endDate;
	}
}
