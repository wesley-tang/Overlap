package overlap.project.scheduler;

import java.util.ArrayList;
import java.util.Date;

/**
 * Scheduler manages the scheduling of events.
 *
 */
public class Scheduler {

	private ArrayList<User> users = new ArrayList<>();

	/**
	 * Creates a new scheduler based off the given users
	 *
	 * @param users an ArrayList of Users to be considered for the scheduling.
	 */
	public Scheduler(ArrayList<User> users){
		this.users = users;
	}

	/**
	 *
	 *
	 * @param validDays an ArrayList of Strings containing the
	 */
	public DateRange schedule(ArrayList<DateRange> validDays){

		// Apply and filter out restrictions

		ArrayList<DateRange> unavailable = new ArrayList<>();

		// Consider all user's calendars
		for (User user : users){
			unavailable.addAll(genDateRangesFrom(user, validDays.get(0).getStart(), validDays.get(validDays.size()-1).getEnd()));
		}

		// Remove from valid dates
		for (DateRange dateRange : unavailable){
			dateRange.removeFrom(validDays);
		}

		//todo remove preferences of all users.

		//todo Break range into 'slots' capable of containing the event and choose one


		DateRange scheduledEvent = null;


		return scheduledEvent;
	}

	private ArrayList<DateRange> genDateRangesFrom(User user, Date earliest, Date latest){
		ArrayList<DateRange> events = new ArrayList<>();

		// todo regex parse ics file from user to look for DTSTART and DTEND
		//ignore until all those after the earliest date, and those later than the latest date

		return events;
	}

}
