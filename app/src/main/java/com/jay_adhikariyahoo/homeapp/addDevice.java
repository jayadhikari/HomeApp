package com.jay_adhikariyahoo.homeapp;

import android.content.Intent;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import static java.util.Arrays.asList;

public class addDevice extends AppCompatActivity {



    public void homeClick(View view)
    {

        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_device);

        try
        {//initialise the database
            SQLiteDatabase devDatabase = this.openOrCreateDatabase("DeviceData",MODE_PRIVATE, null);
            devDatabase.execSQL("CREATE TABLE IF NOT EXISTS device (name VARCHAR, deviceID VARCHAR, channel VARCHAR, status VARCHAR, id INTEGER PRIMARY KEY )");
        }catch(Exception e)
        {
            e.printStackTrace();

        }


        ListView settingsListView = (ListView) findViewById(R.id.settingsListView);
        final ArrayList<String> mySetting = new ArrayList<String>(asList("Edit Device List","Device IP","Change Password"));
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,mySetting);
        settingsListView.setAdapter(arrayAdapter);

        settingsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                switch(position)
                {
                    case 0:
                        Intent intent = new Intent(getApplicationContext(),EditDeviceList.class);
                        startActivity(intent);
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                    default:
                        break;
                }

            }
        });
    }
}
