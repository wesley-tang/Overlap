package overlap.project.scheduler;

/**
 * Object that aggregates properties of a user.
 */
public class User {
	// ICS file of user
	private String ics;

	// Preferences of user
	private String noDays;

	public User(String ics, String noDays){
		this.ics = ics;
		this.noDays = noDays;
	}

	// GETTERS ~~~~~

	public String getIcs() {
		return ics;
	}

	public String getNoDays() {
		return noDays;
	}
}