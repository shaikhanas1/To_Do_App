package com.example.saadshafi.to_do_app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;

public class AddTask extends AppCompatActivity {
    private FirebaseDatabase database;
    private DatabaseReference dbref;
    EditText addtask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        database = FirebaseDatabase.getInstance();
    }

    public void addbuttonclick(View view)
    {
        addtask = (EditText) findViewById(R.id.editTask);
        String task = addtask.getText().toString(); //get value entered by user
        long date = System.currentTimeMillis(); //Returns current time
        SimpleDateFormat format = new SimpleDateFormat("MMM MM dd, yyyy h:mm a");
        String datevalue = format.format(date);

        dbref = database.getInstance().getReference().child("Tasks");
        DatabaseReference newTask = dbref.push();
        newTask.child("name").setValue(task);
        newTask.child("time").setValue(datevalue);
    }
}
