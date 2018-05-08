package com.example.saadshafi.to_do_app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class SingleTask extends AppCompatActivity {

    private String task_key = null;
    private TextView singletaskname;
    private TextView Singletasktime;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_task);


        task_key = getIntent().getExtras().getString("TaskId");
        databaseReference =  FirebaseDatabase.getInstance().getReference().child("Tasks");

        singletaskname = (TextView)findViewById(R.id.singletaskname);
        Singletasktime = (TextView)findViewById(R.id.singletaskdate);

        databaseReference.child(task_key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String taskname = (String) dataSnapshot.child("name").getValue();
                String tasktime = (String) dataSnapshot.child("time").getValue();
                singletaskname.setText(taskname);
                Singletasktime.setText(tasktime);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
