package overlap.project;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class readAlg extends AppCompatActivity {


    private DatabaseReference mDatabase;
    private ListView mUserList;

    private ArrayList<String> mUsernames = new ArrayList<>();

    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);

        //Get database reference
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mUserList = (ListView) findViewById(R.id.user_list);

        //This is for displaying array of data
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mUsernames);

        mUserList.setAdapter(arrayAdapter);

        //Listerener that updates data page when change is made in database
        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                //Tbis is where we decide how we read the data. If this is any different from how our data is stored, app will crash.

                //Right now, we have 2 children in each 'key'. One is Name and other is Data. You will see this if you log into the firebase
                //Hence to read the value at the Name child and Data child seperately, we do the following.
                //If we add more data fields to each key, we can just keep adding more child references to get the data
                String value1 = dataSnapshot.child("Name").getValue(String.class);
                String value2 = dataSnapshot.child("Data").getValue(String.class);

                //This adds the data onto the ListView in app
                mUsernames.add("Name: " + value1 + " Data: " + value2);
                arrayAdapter.notifyDataSetChanged();

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

}
