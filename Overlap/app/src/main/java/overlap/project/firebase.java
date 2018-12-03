package overlap.project;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class firebase extends AppCompatActivity {

    // Get database
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    //Create a database reference
    DatabaseReference myRef;

    //Vars used to store input data
    EditText name;
    EditText data;
    EditText keyNum;
    TextView currentKey;

    //Unique key per user
    // * Need to get current highest key in database instead of always setting to 0
    int key = 0;


    //Writes to Database

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase);

        //Get input data
        name = (EditText) findViewById(R.id.firebaseInput);
        data = (EditText) findViewById(R.id.firebaseData);
        keyNum = (EditText) findViewById(R.id.firebaseKey);

        //Initialize buttons
        Button add = (Button) findViewById(R.id.firebaseAdd);
        Button remove = (Button) findViewById(R.id.firebaseRemove);

        //Get Current Key Display Area
        currentKey = (TextView) findViewById(R.id.currentKeyView);

        currentKey.setText("To Key #: " + Integer.toString(key));

        //When the add button is clicked
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get name
                String nameInput = name.getText().toString();
                //Add new hey to database
                myRef = database.getReference(Integer.toString(key));
                //Create child name to key
                myRef.child("Name").setValue(nameInput);

                //Add child data to key
                String dataInput = data.getText().toString();
                myRef.child("Data").setValue(dataInput);

                //Increment Key
                key++;

                //Update currentkey display
                currentKey.setText("To Key #: " + Integer.toString(key));

            }
        });

        //When the remove button is clicked
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get the key to be removed as input and remove the reference
                myRef = database.getReference(keyNum.getText().toString());
                myRef.removeValue();

            }
        });



    }

}