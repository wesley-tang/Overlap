package overlap.project.scheduler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

	/**
	 * Creates a new DateRange from the given start and end. Must be written in iCalendar format
	 *
	 * @param start String containing the beginning of range in iCalendar format.
	 * @param end String containing the end of range in iCalendar format.
	 *
	 * @throws ParseException if the format given by the parameters is not valid iCalendar format
	 */
	public DateRange(String start, String end) throws ParseException {
		this.start = start;
		this.end = end;

		// Obtains the start and end dates from the provided string
		convertToDates();
	}

	// Attempts to parse the iCalendar strings to produce Date objects, returning whether it was successful
	private void convertToDates() throws ParseException {
		// Define the date format used by the iCalander standard (no timezone)
		SimpleDateFormat icsFormat = new SimpleDateFormat(datePattern, Locale.CANADA);

		// Convert the strings to dates.
		startDate = icsFormat.parse(start);
		endDate = icsFormat.parse(end);
	}

	/**
	 * @param toCheck the Date to check if it's in the date range
	 * @return true if the passed Date is contained within the invoking DateRange.
	 */
	public boolean contains(Date toCheck){
		return toCheck.after(startDate) && toCheck.before(endDate);
	}

	/**
	 * @param toCheck the DateRange to check if it's in the date range
	 * @return true if the passed DateRange is contained within the invoking DateRange.
	 */
	public boolean contains(DateRange toCheck){
		return toCheck.getStart().after(startDate) && toCheck.getEnd().before(endDate);
	}

	/**
	 * Returns a copy of the passed list of date ranges with the
	 * invoking DateRange removed from the specified date ranges..
	 *
	 * @param dates
	 */
	public ArrayList<DateRange> removeFrom(ArrayList<DateRange> dates){
		ArrayList<DateRange> newDates;

		for (DateRange dateRange : dates){
			// If the date range given does not contain this daterange, try the next one
			if (!dateRange.contains(this)){
				continue;
			}
			else {

			}
		}

		return dates;
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
