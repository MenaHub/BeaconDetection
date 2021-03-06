package com.example.beacondetection;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class MyCourses extends AppCompatActivity {

    private ImageButton back;
    private ListView listview;
    private ArrayAdapter ad;
    private ArrayList<String> list;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_courses);

        back = (ImageButton) findViewById(R.id.back);
        listview = (ListView) findViewById(R.id.list);
        username = MainActivity.getUsername();

        // go back to MainActivity on back-button click
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent start = new Intent(MyCourses.this, MainActivity.class);
                startActivity(start);
            }
        });

        list = new ArrayList<>();
        // displays the lecture info in recycler view
        ad = new ArrayAdapter<String>(this, R.layout.course_items, R.id.course, list);
        listview.setAdapter(ad);

        String path = "Courses/" + username;

        // get connection to Firebase and get specified path
        DatabaseReference reference = FirebaseDatabase.getInstance("https://iotprojectg4-79ffa-default-rtdb.firebaseio.com/").getReference(path);

        // get lecture data from database everytime data changes
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot snap: snapshot.getChildren()) {
                    list.add(snap.getValue().toString());
                }
                Collections.sort(list);
                ad.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
    }
}