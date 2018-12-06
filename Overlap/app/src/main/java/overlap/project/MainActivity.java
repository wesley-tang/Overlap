package overlap.project;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import overlap.project.scheduler.CalendarSelect;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

	private GoogleSignInClient mGoogleSignInClient;
	private int RC_SIGN_IN = 1;

	public static final String USER_ACCOUNT = "overlap.project.ACCOUNT";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Set what the content for this activity will be
		setContentView(R.layout.activity_main);

		// https://developers.google.com/identity/sign-in/android/sign-in?authuser=1#top_of_page

		// Configure sign-in to request the user's ID, email address, and basic
		// profile. ID and basic profile are included in DEFAULT_SIGN_IN.
		GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
				.requestEmail()
				.build();
		// todo ask for OATH2.0 to get calendar

		// Build a GoogleSignInClient with the options specified by gso.
		mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

		// Check for existing Google Sign In account, if the user is already signed in
		// the GoogleSignInAccount will be non-null.
		GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

		updateUI(account);

		// Add Google sign in button

		// Set the dimensions of the sign-in button.
		SignInButton signInButton = findViewById(R.id.sign_in_button);
		signInButton.setSize(SignInButton.SIZE_STANDARD);

		// Set what the button will do
		findViewById(R.id.sign_in_button).setOnClickListener(this);

	}

	private void updateUI(GoogleSignInAccount account) {
		// todo Update your UI accordingly—that is, hide the sign-in button, launch your main activity, or whatever is appropriate for your app.

		if (account != null) {

			// Check to see if it already exists in database
			getUser user = new getUser(account);

			// Pass the authenticated user's email and id to the calendar activity, and start it
			Intent myIntent = new Intent(this, CalendarSelect.class);
			myIntent.putExtra(USER_ACCOUNT, account);
			startActivity(myIntent);

			// Create pop up notification for authentication
			Context context = getApplicationContext();
			CharSequence text = "Authentication success!";
			int duration = Toast.LENGTH_SHORT;

			Toast toast = Toast.makeText(context, text, duration);
			toast.show();

		}
		else{
			// Create a toast to let them know authentication failed, todo make more comprehensive
			Context context = getApplicationContext();
			CharSequence text = "Authentication failed! Try again later.";
			int duration = Toast.LENGTH_SHORT;

			Toast toast = Toast.makeText(context, text, duration);
			toast.show();
		}

	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.sign_in_button:
				signIn();
				break;

			// ...
		}
	}

	private void signIn() {
		Intent signInIntent = mGoogleSignInClient.getSignInIntent();
		// todo waht the heck is rc_Sign_in i made it 1
		startActivityForResult(signInIntent, RC_SIGN_IN);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		// Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
		if (requestCode == RC_SIGN_IN) {
			// The Task returned from this call is always completed, no need to attach
			// a listener.
			Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
			handleSignInResult(task);
		}
	}

	private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
		try {
			GoogleSignInAccount account = completedTask.getResult(ApiException.class);

			// Signed in successfully, show authenticated UI.
			updateUI(account);
		} catch (ApiException e) {
			// The ApiException status code indicates the detailed failure reason.
			// Please refer to the GoogleSignInStatusCodes class reference for more information.
			// todo why do I need tag?
			//Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
			updateUI(null);
		}
	}


// If you need to pass the currently signed-in user to a backend server, send the ID token to your backend server and validate the token on the server.

}