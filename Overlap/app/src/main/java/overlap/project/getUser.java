package overlap.project;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class getUser {

    private String id;
    private String displayName;
    private String fname;
    private String lname;
    private String email;
    private String ics;
    private String weekdayPref;
    private int defDuration;
    private int beginTime;
    private int endTime;

    private boolean exists;

    // Get database
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    //Create a database reference
    private DatabaseReference myRef = database.getReference();

    public getUser(final GoogleSignInAccount acct) {
		myRef.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot dataSnapshot) {
				for (DataSnapshot data : dataSnapshot.getChildren()) {
//					if (data.child(acct.getId()).exists()) {
//						loadUser(acct.getEmail());
//					} else {
						// Create a new user
						displayName = acct.getDisplayName();
						fname = acct.getGivenName();
						lname = acct.getFamilyName();
						email = acct.getEmail();
						id = acct.getId();

						// Set as defaults
						// todo retrieve calendar
						// todo remove magic numbers
						ics = "";
						weekdayPref = "";
						defDuration = 60;
						beginTime = 8;
						endTime = 20;

						//Add id to database
						myRef = database.getReference(id);

						//Create child name to key
						myRef.child("Display").setValue(displayName);
						myRef.child("FName").setValue(fname);
						myRef.child("LName").setValue(lname);
						myRef.child("Email").setValue(email);
						myRef.child("ID").setValue(id);
						myRef.child("ics").setValue(ics);
						myRef.child("weekdayPref").setValue(weekdayPref);
						myRef.child("defDuration").setValue(defDuration);
						myRef.child("beginTime").setValue(beginTime);
						myRef.child("endTime").setValue(endTime);

					//}
				}
			}

			@Override
			public void onCancelled(@NonNull DatabaseError databaseError) {

			}
		});
	}

	public getUser(final String email) {
    	loadUser(email);
	}

	private void loadUser(final String email){
		myRef.addChildEventListener(new ChildEventListener() {
			@Override
			public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

				String value = dataSnapshot.child("Email").getValue(String.class);
				if (value.equals(email)) {

					id = dataSnapshot.child("ID").getValue(String.class);
					DatabaseReference newRef = database.getReference(id);

					displayName = dataSnapshot.child("Display").getValue(String.class);
					fname = dataSnapshot.child("FName").getValue(String.class);
					lname = dataSnapshot.child("LName").getValue(String.class);
					ics = dataSnapshot.child("ICS").getValue(String.class);
					weekdayPref = dataSnapshot.child("WeekdayPref").getValue(String.class);
					String temp1 = dataSnapshot.child("DefDuration").getValue(String.class);
					defDuration = Integer.parseInt(temp1);
					String temp2 = dataSnapshot.child("BeginTime").getValue(String.class);
					beginTime = Integer.parseInt(temp2);
					String temp3 = dataSnapshot.child("EndTime").getValue(String.class);
					endTime = Integer.parseInt(temp3);
				}

			}

			@Override
			public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

			}

			@Override
			public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

			}

			@Override
			public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

			}

			@Override
			public void onCancelled(@NonNull DatabaseError databaseError) {

			}
		});
	}

//
//	public void addToDatabase() {
//
//		//Add id to database
//		myRef = database.getReference(id);
//
//		//Create child name to key
//		myRef.child("Display").setValue(displayName);
//		myRef.child("FName").setValue(fname);
//		myRef.child("LName").setValue(lname);
//		myRef.child("Email").setValue(email);
//		myRef.child("ID").setValue(id);
//
//	}

    public String getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    public String getEmail() {
        return email;
    }

    public String getIcs() {
        return ics;
    }

    public String getWeekdayPref() {
        return weekdayPref;
    }

    public int getDefDuration() {
        return defDuration;
    }

    public int getBeginTime() {
        return beginTime;
    }

    public int getEndTime() {
        return endTime;
    }
}
