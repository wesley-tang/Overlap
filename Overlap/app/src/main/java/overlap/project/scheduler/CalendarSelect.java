package overlap.project.scheduler;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

import overlap.project.MainActivity;
import overlap.project.R;
import overlap.project.User;
import overlap.project.getUser;

public class CalendarSelect extends AppCompatActivity {

	CalendarView calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_select);


        Intent intent = getIntent();
        String email = intent.getStringExtra(MainActivity.USER_EMAIL);


		// Set the minDate to only today.
		calendar = findViewById(R.id.calendarView);
		calendar.setMinDate(Calendar.getInstance().getTimeInMillis());

		getUser user = new getUser(email);

		// Create pop up notification for authentication
		Context context = getApplicationContext();
		CharSequence text = user.getDisplayName();
		int duration = Toast.LENGTH_SHORT;

		Toast toast = Toast.makeText(context, text, duration);
		toast.show();

    }
}
