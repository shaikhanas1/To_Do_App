package com.example.saadshafi.to_do_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference dbrefer;
    private RecyclerView tasklist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tasklist = (RecyclerView) findViewById(R.id.addtask_list);
        tasklist.setHasFixedSize(true);
        tasklist.setLayoutManager(new LinearLayoutManager(this));
        dbrefer = FirebaseDatabase.getInstance().getReference().child("Tasks");

        //Adding date values
        TextView tvday = (TextView)findViewById(R.id.currentDay);
        TextView tvdate = (TextView)findViewById(R.id.currentDate);
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        Date d = new Date();
        String displayDay = sdf.format(d);
        tvday.setText(displayDay);

        long date = System.currentTimeMillis();
        SimpleDateFormat dd = new SimpleDateFormat("MMM MM dd, yyyy h:mm a");
        String displayDate = dd.format(date);
        tvdate.setText(displayDate);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder
    {
        View newView;
        public TaskViewHolder(View itemView)
        {
            super(itemView);
            newView = itemView;
        }

        public void setName(String name)
        {
            TextView taskName = (TextView) newView.findViewById(R.id.task_name);
            taskName.setText(name);
        }

        public void setTime(String time)
        {
            TextView taskTime = (TextView) newView.findViewById(R.id.task_time);
            taskTime.setText(time);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<com.example.saadshafi.to_do_app.Task,TaskViewHolder> FBRA = new FirebaseRecyclerAdapter<com.example.saadshafi.to_do_app.Task, TaskViewHolder>(
                com.example.saadshafi.to_do_app.Task.class,
                R.layout.task_row,
                TaskViewHolder.class,
                dbrefer
        ) {
            @Override
            protected void populateViewHolder(TaskViewHolder viewHolder, com.example.saadshafi.to_do_app.Task model, int position) {

                final String key = getRef(position).getKey().toString();
                viewHolder.setName(model.getName());
               viewHolder.setTime(model.getTime());

               viewHolder.newView.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                        Intent movetotask = new Intent(MainActivity.this,SingleTask.class);
                        movetotask.putExtra("TaskId",key);
                        startActivity(movetotask);
                   }
               });
            }
        };
        tasklist.setAdapter(FBRA);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        else if(id == R.id.addTask)
        {
            Intent addIntent = new Intent(MainActivity.this,AddTask.class);
            startActivity(addIntent);
        }

        return super.onOptionsItemSelected(item);
    }
}
