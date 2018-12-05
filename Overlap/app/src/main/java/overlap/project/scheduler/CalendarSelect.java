package overlap.project.scheduler;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CalendarView;

import java.util.Calendar;
import java.util.Date;

import overlap.project.MainActivity;
import overlap.project.R;

public class CalendarSelect extends AppCompatActivity {

	CalendarView calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_select);


        Intent intent = getIntent();
        String email = intent.getStringExtra(MainActivity.USER_EMAIL);
        String id = intent.getStringExtra(MainActivity.USER_ID);

		calendar = findViewById(R.id.calendarView);

		// Set the minDate to only today.
		calendar.setMinDate(Calendar.getInstance().getTimeInMillis());

    }
}
