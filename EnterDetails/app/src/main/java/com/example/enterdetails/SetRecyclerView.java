package com.example.enterdetails;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.enterdetails.database.AppDatabase;
import com.example.enterdetails.entities.User;
import com.example.enterdetails.util.AppExecutors;

import java.util.List;

public class SetRecyclerView extends AppCompatActivity {

    // 1 Model
    // 2 Adapter
    // 3 View
    private AppDatabase appDB;
    private UserAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_view);
        RecyclerView recyclerView;

        // TODO 1: Initialize Database
        appDB= AppDatabase.getInstance(getApplicationContext());

        // TODO 2: Initialize Recyclerview
        recyclerView = findViewById(R.id.rcvUser);
        // 2.1 Layout
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        // TODO 3: Bind the custom adapter
        // 2.2 Adapter
        userAdapter = new UserAdapter(this);
        recyclerView.setAdapter(userAdapter);
        // TODO 5: Call function
        retrievePersonListFromDatabase();



    }

    public void retrievePersonListFromDatabase(){
        // TODO 4: Call worker thread to get the data and pass to adapter
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                // separate
                final List<User> userList = appDB.iUserDAO().loadAllUser();

                runOnUiThread(new Runnable() {
                    // main UI thread
                    @Override
                    public void run() {
                        userAdapter.setUserDataInAdapter(userList);
                    }
                });
            }
        });
    }
}
