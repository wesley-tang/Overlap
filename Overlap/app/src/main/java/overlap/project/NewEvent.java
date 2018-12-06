package overlap.project;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;


import java.util.Date;

import overlap.project.scheduler.CalendarSelect;
import overlap.project.scheduler.DateRange;

public class NewEvent extends AppCompatActivity {

	DateRange event;

	TextView dateInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_event);


        //
        Intent intent = getIntent();
		long start = intent.getLongExtra(CalendarSelect.EVENT_START, 0);
		long end = intent.getLongExtra(CalendarSelect.EVENT_END, 0);

		event = new DateRange(new Date(start), new Date(end));

		dateInfo = findViewById(R.id.dateInfo);
		dateInfo.setText(event.getStart().toString() + " to " + event.getEnd().toString());

    }
}
