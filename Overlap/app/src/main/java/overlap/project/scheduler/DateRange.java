package overlap.project.scheduler;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * DateRange contains the start and end of a range of time, specifying year, month, day, hour,
 * minute and second.
 */
public class DateRange implements Parcelable{

	// Start and end of the date range as a string in iCalendar format
	private String start;
	private String end;

	// Start and end of the date range as Date objects
	private Date startDate;
	private Date endDate;

	// The pattern by which to convert iCalendar times to Date objects
	private final String DATE_PATTERN = "yyyyMMdd'T'HHmmss'Z'";

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

	/**
	 * Creates a new DateRange from the given start and end.
	 *
	 * @param start Date containing the beginning of range
	 * @param end Date containing the end of range
	 *
	 * @throws ParseException if the format given by the parameters is not valid iCalendar format
	 */
	public DateRange(Date start, Date end){
		this.startDate = start;
		this.endDate = end;
	}

	// Attempts to parse the iCalendar strings to produce Date objects, returning whether it was successful
	private void convertToDates() throws ParseException {
		// Define the date format used by the iCalander standard (no timezone)
		SimpleDateFormat icsFormat = new SimpleDateFormat(DATE_PATTERN, Locale.CANADA);

		// Convert the strings to dates.
		startDate = icsFormat.parse(start);
		endDate = icsFormat.parse(end);
	}

	/**
	 * @param toCheck the Date to check if it's contained within the date range
	 * @return true if the passed Date is contained within the invoking DateRange.
	 */
	public boolean contains(Date toCheck){
		return toCheck.after(startDate) && toCheck.before(endDate);
	}

	/**
	 * @param toCheck the Date to check if the day is the same
	 * @return true if the passed Date has the same day as the invoking DateRange.
	 */
	public boolean sameDay(Date toCheck){
		// todo make this not bad
		return startDate.getDate() == toCheck.getDate() && startDate.getMonth() == startDate.getMonth();
	}

	/**
	 * @param toCheck the DateRange to check if it's contained within the date range
	 * @return true if the passed DateRange is contained within the invoking DateRange.
	 */
	public boolean contains(DateRange toCheck){
		return toCheck.getStart().after(startDate) && toCheck.getEnd().before(endDate);
	}

	/**
	 * Gives an int depending on whether or not the invoking DateRange intersects with the passed DateRange
	 *
	 * @param toCheck the DateRange to check if it's in the invoking daterange
	 * @return 0 if it does not intersect, 1 if the start intersects, 2 if the end intersects,
	 * 				3 if the passed DateRange completely contains the invoking DateRange
	 */
	public int intersects(DateRange toCheck){
		if (toCheck.getStart().after(startDate) && toCheck.getStart().before(endDate))
			return 1;
		else if (toCheck.getEnd().after(startDate) && toCheck.getEnd().before(endDate))
			return 2;
		else if (toCheck.getStart().before(startDate) && toCheck.getEnd().after(endDate))
			return 3;
		return 0;
	}

	/**
	 * Returns a copy of the passed list of date ranges with the
	 * invoking DateRange removed from the specified date ranges.
	 *
	 * Splits a date range into two pieces if possible.
	 *
	 * @param dates
	 */
	public ArrayList<DateRange> removeFrom(ArrayList<DateRange> dates){
		ArrayList<DateRange> newDates = new ArrayList<>(dates);

		for (DateRange dateRange : dates){
			// If the date range given does not contain this date range, try the next one
			if (dateRange.contains(this)){
				// Break the range up from the beginning of the range to the beginning of the removed range
				DateRange firstHalf = new DateRange(dateRange.getStart(), getStart());
				// Include the end of the removed range to the end of the original range
				DateRange secondHalf = new DateRange(getEnd(), dateRange.getEnd());

				// Find where the original range was and remove it, inserting the two new ranges in its place
				int ind = newDates.indexOf(dateRange);
				newDates.remove(ind);
				// Insert latter half first since add shifts to the right.
				newDates.add(ind, secondHalf);
				newDates.add(ind, firstHalf);

			}
			else {
				int state = dateRange.intersects(this);

				DateRange modifiedRange;

				// Set the new dateRange depending on the state of the intersection
				if (state == 1)
					modifiedRange = new DateRange(dateRange.getStart(), getStart());
				else if (state == 2)
					modifiedRange = new DateRange(getEnd(), dateRange.getEnd());
				else if (state == 3) {
					// Completely remove this time if it is encompassed by the dateRange is within this
					newDates.remove(dateRange);
					continue;
				}
				else
					continue;

				// Find where the original range was and remove it, inserting the new range in its place
				int ind = newDates.indexOf(dateRange);
				newDates.remove(ind);
				newDates.add(ind, modifiedRange);
			}
		}

		return newDates;
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

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.start);
		dest.writeString(this.end);
		dest.writeSerializable(this.startDate);
		dest.writeSerializable(this.endDate);
	}

	public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
		public DateRange createFromParcel(Parcel in) {
			return new DateRange(in);
		}

		public DateRange[] newArray(int size) {
			return new DateRange[size];
		}
	};

	// Parcelling part
	public DateRange(Parcel in){
		this.start = in.readString();
		this.end = in.readString();


		this.startDate = (Date) in.readSerializable();
		this.endDate = (Date) in.readSerializable();
	}
}