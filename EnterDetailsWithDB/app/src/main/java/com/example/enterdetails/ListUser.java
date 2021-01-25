package com.example.enterdetails;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.example.enterdetails.database.AppDatabase;
import com.example.enterdetails.entities.User;
import com.example.enterdetails.util.AppExecutors;

import java.util.ArrayList;
import java.util.List;

public class ListUser extends ListActivity {

    private AppDatabase appDB;
    List<User> UserList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_list_persons);
        initializeDatabase();
        retrieveTask();
    }
    public void initializeDatabase(){
        appDB =AppDatabase.getInstance(getApplicationContext());
    }


    private void retrieveTask(){
        /**
         * Query it off the main thread
         */

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                UserList = appDB.iUserDAO().loadAllUser();
                final List<String> list = new ArrayList<>();
                for(int i =0; i<UserList.size(); i++)
                    list.add(UserList.get(i).getName());

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                                ListUser.this,
                                R.layout.list_item,
                                R.id.textview,
                                list);
                        ListUser.this.setListAdapter(arrayAdapter);
                    }
                });
            }
        });
        /**
         * Report result or update UI
         */

    }


    @Override
    protected void onResume() {
        super.onResume();
        initializeDatabase();

    }

    @Override
    protected void onStop() {
        super.onStop();
        appDB.close();
    }
}