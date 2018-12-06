package overlap.project.scheduler;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import overlap.project.R;

public class SettingsPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_page);

        // Buttons go heeere
		// On button clicks
		final Button doneButton = findViewById(R.id.doneButton);
		doneButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				done();
			}
		});

	}

	private void done(){
		// Move to settings
		Intent myIntent = new Intent(this, CalendarSelect.class);
		startActivity(myIntent);
		// todo pass user info across and save to database

    }
}
