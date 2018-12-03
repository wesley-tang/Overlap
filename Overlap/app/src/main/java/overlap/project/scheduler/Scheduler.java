package overlap.project.scheduler;

import java.text.ParseException;
import java.util.ArrayList;

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

		String start = "";
		String end = "";

		
		// Apply and filter out restrictions
		// Obtain all data ranges capable of cotnaining an event
		// Break range into 'slots' capable of contaiing the event and choose one



		DateRange scheduledEvent = null;
		try {
			scheduledEvent = new DateRange(start, end);
		} catch (ParseException e) {
			//TODO  Alert user with notification
			System.out.println("Could not create schdeule because parse failed!");
			e.printStackTrace();
		}

		return scheduledEvent;
	}


}
