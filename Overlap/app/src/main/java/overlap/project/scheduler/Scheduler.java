package overlap.project.scheduler;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import overlap.project.User;

/**
 * Scheduler manages the scheduling of events.
 *
 */
public class Scheduler {

	private ArrayList<User> users;

	/**
	 * Creates a new scheduler based off the given User
	 *
	 * @param users an ArrayList of Users to be considered for the scheduling. User at index 0 must be the creator of the event
	 */
	public Scheduler(ArrayList<User> users){
		this.users = users;
	}

	/**
	 *	Schedules an event based off the User included in the scheduler and over the specified days and duration
	 *
	 * @param validDays an ArrayList of Strings containing the chosen days to schedule an event
	 * @param duration a long containing the number of minutes the scheduled event should be\
	 *
	 * @return A DateRange for the duration of the scheduled event.
	 */
	public DateRange schedule(ArrayList<DateRange> validDays, long duration){

		// Apply and filter out restrictions

		ArrayList<DateRange> unavailable = new ArrayList<>();

		// Consider all user's calendars
		for (User user : users){
			unavailable.addAll(genDateRangesFrom(user, validDays.get(0).getStart(), validDays.get(validDays.size()-1).getEnd()));
		}

		// Remove from valid dates
		for (DateRange dateRange : unavailable){
			validDays = dateRange.removeFrom(validDays);
		}

		// todo remove preferences of all User.
		// Do this when grabbing the user's input

		// Break range into 'slots' capable of containing the event
		ArrayList<DateRange> possibleTimes = new ArrayList<>();

		for (DateRange dateRange : validDays){
			long timeSlot = dateRange.getEnd().getTime()-dateRange.getStart().getTime();
			if (timeSlot < duration)
				continue;

			// Find all the periods that the event could fit in the slot, at 5 minute intervals
			for (int time = 0; time + duration < timeSlot; time+=5){
				// Create a new date range between the beginning of the time to the end of its duration
				DateRange newPossibility = new DateRange(new Date(dateRange.getStart().getTime() + time), new Date(dateRange.getStart().getTime() + time+duration));
				possibleTimes.add(newPossibility);
			}
		}

		// If no possible events found report error or give alternative
		if (possibleTimes.size() == 0) {
			//todo Report error!!
			System.out.println("No times possible!!");
			return null;
		}

		// todo for now, just choose the time slot in the centre of the possibilities
		return possibleTimes.get(possibleTimes.size()/2);
	}

	// Create an ArrayList of DateRanges from a user's ics file.
	private ArrayList<DateRange> genDateRangesFrom(User user, Date earliest, Date latest){
		ArrayList<DateRange> events = new ArrayList<>();
		//todo ignore until all those after the earliest date, and those later than the latest date

		// Regex parse ics file from user to look for DTSTART and DTEND
		Pattern p = Pattern.compile("(DTSTART|DTEND):(.*Z)*\\n");
		Matcher m = p.matcher(user.getIcs());

		String start = "", end = "";

		// Find all instances of start and end for events
		while (m.find()){
			// todo remove magic numbers
			if (m.group(1).equals("DTSTART")){
				start = m.group(2).substring(8);
			} else {
				// If this is the end, we can now add the event to the list
				end = m.group(2).substring(6);

				DateRange newEvent = null;

				// Create the new event
				try {
					newEvent = new DateRange(start, end);
				} catch (ParseException e) {
					e.printStackTrace();
				}

				events.add(newEvent);
			}
		}

		return events;
	}
}