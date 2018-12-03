package overlap.project;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

public class GoogleSigninActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // https://developers.google.com/identity/sign-in/android/sign-in?authuser=1#top_of_page

        // Configure sign-in to request the user's ID, email address, and basic
// profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
		// todo ask for OATH2.0 to get calendar

		// Build a GoogleSignInClient with the options specified by gso.
		GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

		// Check for existing Google Sign In account, if the user is already signed in
// the GoogleSignInAccount will be non-null.
		GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
		if (account != null)
			updateUI(account);

		// Add Google sign in button
		setContentView(R.layout.activity_googlesignin);


    }

	private void updateUI(GoogleSignInAccount account) {
		// todo Update your UI accordingly—that is, hide the sign-in button, launch your main activity, or whatever is appropriate for your app.
		/* Does this work?
		Intent myIntent = new Intent(this, MyActivityName.class);
startActivity(myIntent);

		 */
    }



}
