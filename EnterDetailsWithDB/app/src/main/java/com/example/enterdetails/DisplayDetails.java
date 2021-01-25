package com.example.enterdetails;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class DisplayDetails extends AppCompatActivity {

    ListView listDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_details);

        listDetails = (ListView) findViewById(R.id.listDisplay);

        Intent intent = getIntent();
        String name = intent.getStringExtra(MainActivity.NAME);
        String phone = intent.getStringExtra(MainActivity.PHONE);
        String address = intent.getStringExtra(MainActivity.ADDRESS);
        String city = intent.getStringExtra(MainActivity.CITY);
        String zip = intent.getStringExtra(MainActivity.ZIP);
        String email = intent.getStringExtra(MainActivity.EMAIL);
        String area = intent.getStringExtra(MainActivity.AREA);
        String state = intent.getStringExtra(MainActivity.STATE);
        String bd = intent.getStringExtra(MainActivity.BD);


        ArrayList<String> details = new ArrayList<>();
        details.add(area);
        details.add(state);
        details.add(name);
        details.add(phone);
        details.add(address);
        details.add(city);
        details.add(zip);
        details.add(email);
        details.add(bd);

        ArrayList<String> detailsFilterEmpty = new ArrayList<>();
        for (String detail : details) {
            if (detail.length() > 0) detailsFilterEmpty.add(detail);
        }

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, detailsFilterEmpty);

        listDetails.setAdapter(arrayAdapter);
    }
}