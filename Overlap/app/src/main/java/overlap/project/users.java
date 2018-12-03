package overlap.project;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class users extends AppCompatActivity {

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

    // Get database
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    //Create a database reference
    private DatabaseReference myRef;

    //User Constructor: Adds user to database
    public users() {

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        if (acct != null) {
            displayName = acct.getDisplayName();
            fname = acct.getGivenName();
            lname = acct.getFamilyName();
            email = acct.getEmail();
            id = acct.getId();

        }

    }

    public void addToDatabase() {

        //Add id to database
        myRef = database.getReference(id);

        //Create child name to key
        myRef.child("Display").setValue(displayName);
        myRef.child("FName").setValue(fname);
        myRef.child("LName").setValue(lname);
        myRef.child("Email").setValue(email);
        myRef.child("ID").setValue(id);

    }

    public String getId() {
        return this.id;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public String getFname() {
        return this.fname;
    }

    public String getLname() {
        return this.lname;
    }

    public String getEmail() {
        return this.email;
    }

    public void setIcs(String ics) {
        this.ics = ics;
        myRef.child("ICS").setValue(ics);

    }

    public String getIcs() {
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                ics = dataSnapshot.child("ICS").getValue(String.class);
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
        return ics;
    }

    public void setWeekdayPref(String weekdayPref) {
        this.weekdayPref = weekdayPref;
        myRef.child("WeekdayPref").setValue(weekdayPref);

    }

    public String getWeekdayPref() {
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                weekdayPref = dataSnapshot.child("WeekdayPref").getValue(String.class);
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
        return weekdayPref;
    }

    public void setDefDuration(int defDuration) {
        this.defDuration = defDuration;
        myRef.child("DefDuration").setValue(Integer.toString(this.defDuration));

    }

    public int getDefDuration() {
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String temp = dataSnapshot.child("DefDuration").getValue(String.class);
                defDuration = Integer.parseInt(temp);
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
        return defDuration;
    }

    public void setBeginTime(int beginTime) {
        this.beginTime = beginTime;
        myRef.child("BeginTime").setValue(Integer.toString(beginTime));

    }

    public int getBeginTime() {
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String temp = dataSnapshot.child("BeginTime").getValue(String.class);
                beginTime = Integer.parseInt(temp);
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
        return beginTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
        myRef.child("EndTime").setValue(Integer.toString(endTime));

    }

    public int getEndTime() {
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String temp = dataSnapshot.child("EndTime").getValue(String.class);
                endTime = Integer.parseInt(temp);
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
        return endTime;
    }




}
