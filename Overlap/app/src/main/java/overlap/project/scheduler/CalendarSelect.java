package overlap.project.scheduler;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import overlap.project.MainActivity;
import overlap.project.NewEvent;
import overlap.project.R;
import overlap.project.User;
import overlap.project.getUser;

public class CalendarSelect extends AppCompatActivity {

	private CalendarView calendar;
	private TextView datesText;
	private EditText durationField;

	private String DEF_TEXT = "Schedule ";
	private String DEF_TEXT_EMPTY = "within the next 2 months";

	public static final String EVENT_START = "overlap.project.EVENT_START";
	public static final String EVENT_END = "overlap.project.EVENT_END";

	private ArrayList<DateRange> validDays;
	private ArrayList<User> users;

	private GoogleSignInAccount account;

	private int duration = 60;
	private int startTime = 8;
	private int endTime = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_select);

        // Grab account from sign in
        Intent intent = getIntent();
        account = (GoogleSignInAccount) intent.getSerializableExtra(MainActivity.USER_ACCOUNT);

        // Init components and lists
        datesText = findViewById(R.id.datesText);
        datesText.setText(DEF_TEXT + DEF_TEXT_EMPTY);

		durationField = findViewById(R.id.durationField);
		durationField.setText(Integer.toString(duration));

        validDays = new ArrayList<>();

        users = new ArrayList<>();
        // Add users
		users.add(new User(account));

		// Set the minDate to only today.
		calendar = findViewById(R.id.calendarView);
		calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
			@Override
			public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
				// Creates the new date, restricted by the times that people set
				Date selectedDate = new Date(year, month, dayOfMonth);

				int toRemove = -1;

				// Check if this date already exists and remove it
				for (int i = 0; i < validDays.size() - 1 ; i++){
					if (validDays.get(i).sameDay(selectedDate)) {
						toRemove = i;
						break;
					}
				}

				// remove date if it already exists
				if (toRemove != -1) {
					validDays.remove(toRemove);
				} else{
					Date endDate = (Date) selectedDate.clone();
					selectedDate.setHours(startTime);
					endDate.setHours(endTime);
					validDays.add(new DateRange(selectedDate, endDate));
				}

				System.out.println(validDays.size());

				// Update datesText
				if (validDays.size() == 0){
					datesText.setText(DEF_TEXT + DEF_TEXT_EMPTY);
				} else {
					String dates = " on ";
					// Restrict it to max 5 dates before ...
					// todo remove magic number todo convert to month string
					for (int i = 0; i < validDays.size()-1 && i < 6; i++){
						if (i == 5)
							dates += "...";
						else {
							dates += Integer.toString(validDays.get(i).getStart().getMonth()+1) + "/" + Integer.toString(validDays.get(i).getStart().getDate()) + ", ";
						}
					}
					datesText.setText(DEF_TEXT + dates);
				}
			}
		});
		calendar.setMinDate(Calendar.getInstance().getTimeInMillis());



//		// test debug
//		Context context = getApplicationContext();
//		CharSequence text = users.get(0).getDisplayName();
//		final int duration = Toast.LENGTH_SHORT;
//
//		Toast toast = Toast.makeText(context, text, duration);
//		toast.show();



		// On button clicks
		final Button schedule = findViewById(R.id.schedule);
		schedule.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if (validDays.size() == 0){
					// Set as two months from now
					// todo account for preferences
					Date twomonths = new Date();
					twomonths.setMonth(twomonths.getMonth()+2);
					DateRange validTime = new DateRange(new Date(), twomonths);
					validDays.add(validTime);
				}

				// TODO REMOVE THIS AFTER DEBUGGING NOT GOOD NOT GOOD AT ALL!!!!11!
				users.clear();
				users.add(new User("000000000", "MoonMoon",  "Moony",  "Magan", "moonmoon@gmail.com", "BEGIN:VCALENDAR\n" +
						"PRODID:-//Google Inc//Google Calendar 70.9054//EN\n" +
						"VERSION:2.0\n" +
						"CALSCALE:GREGORIAN\n" +
						"METHOD:PUBLISH\n" +
						"X-WR-TIMEZONE:America/Edmonton\n" +
						"END:VCALENDAR\n", "deprecated", duration, startTime, endTime));
				users.add(new User("000000001", "MoonMoon",  "Moony",  "Magan", "moonmoon@gmail.com", "BEGIN:VCALENDAR\n" +
						"PRODID:-//Google Inc//Google Calendar 70.9054//EN\n" +
						"VERSION:2.0\n" +
						"CALSCALE:GREGORIAN\n" +
						"METHOD:PUBLISH\n" +
						"X-WR-TIMEZONE:America/Edmonton\n" +
						"END:VCALENDAR\n", "deprecated", duration, startTime, endTime));

				scheduleEvent();
			}
		});

		// On button clicks
		final ImageButton settingsButton = findViewById(R.id.settingsButton);
		settingsButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				toSettingsPage();
			}
		});


		// On button clicks
		final ImageButton inviteesButton = findViewById(R.id.inviteesButton);
		inviteesButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				toInviteesPage();
			}
		});

    }

	private void toSettingsPage(){
		// Move to settings
		Intent myIntent = new Intent(this, SettingsPage.class);
		startActivity(myIntent);
		// todo pass user info across or have it stored locally
	}


	private void toInviteesPage(){
		// Move to settings
		Intent myIntent = new Intent(this, InviteesPage.class);
		startActivity(myIntent);
	}

	private void scheduleEvent(){
		Scheduler scheduler = new Scheduler(users);

		DateRange event = scheduler.schedule(validDays, duration);

		// Pass the date to create event
		Intent myIntent = new Intent(this, NewEvent.class);
		myIntent.putExtra(EVENT_START, event.getStart().getTime());
		myIntent.putExtra(EVENT_END, event.getEnd().getTime());
		startActivity(myIntent);
	}

}
