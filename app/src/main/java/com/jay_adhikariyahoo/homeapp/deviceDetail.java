package com.jay_adhikariyahoo.homeapp;

import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


public class deviceDetail extends AppCompatActivity
{

    String name, devID, channel, devType;
    int listId = -1;



    public void cancelClick(View view)
    {

        Intent intent = new Intent(getApplicationContext(),EditDeviceList.class);
        startActivity(intent);
    }

    public void saveClick(View view)
    {
        EditText nameText = (EditText) findViewById(R.id.editText);
        name = "";

        name = nameText.getText().toString();

        if(name.matches(""))
        {
            Toast.makeText(this, "Please enter a device name", Toast.LENGTH_LONG).show();

        }
        else
        {
            Intent intent = new Intent(getApplicationContext(), EditDeviceList.class);

            /* intent.putExtra("name", name);//for single data send
            intent.putExtra("id", devId);
            intent.putExtra("ch", channel);
            intent.putExtra("type", devType);
            */

            Bundle bundle = new Bundle();
            bundle.putInt("listId",listId);
            bundle.putString("name", name);
            bundle.putString("id", devID);
            bundle.putString("ch", channel);
            bundle.putString("type", devType);
            intent.putExtras(bundle);

            startActivity(intent);

        }
    }

    Spinner deviceIDSpinner,channelSpinner,devTypeSpinner;//define spinner

    String deviceIDSpinnerList[] = {"28010","28011","28012","28013","28014","28015","28016","28017","28018","28019"};
    String channelSpinnerList[] = {"0","1","2","3"};
    String devTypeSpinnerList[] = {"Light","FanAC","TVFridge","Others"};

    ArrayAdapter<String> adapterDeviceIDSpinner,adapterChannelSpinner,adapterDevTypeSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_detail);

        Log.i("Device Detail", "in on create...");


         name = "";
         devID = "";
         channel = "";
         devType = "";

        //get intent and bundle data if any passed from previous activity
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if(extras != null)//if something passed from prev activity
        {
            listId = extras.getInt("listId");
            name = extras.getString("name");
            devID = extras.getString("id");
            channel = extras.getString("ch");
            devType = extras.getString("type");

            Log.i("device detail", String.valueOf(listId));
            Log.i("device detail", name);
            Log.i("device detail", devID);
            Log.i("device detail", channel);
            Log.i("device detail", devType);

            EditText nameText = (EditText) findViewById(R.id.editText);

            nameText.setText(name);
        }

        deviceIDSpinner = (Spinner) findViewById(R.id.deviceIdSpinner);
        adapterDeviceIDSpinner = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,deviceIDSpinnerList);

        deviceIDSpinner.setAdapter(adapterDeviceIDSpinner);//set adapter to spinner



        deviceIDSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {

               //implement this
                devID = deviceIDSpinnerList[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
                if(listId != -1)
                {
                    Log.i("spinner 1","nothing selected");
                }

            }
        });

        channelSpinner = (Spinner) findViewById(R.id.channelSpinner);
        adapterChannelSpinner = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,channelSpinnerList);

        channelSpinner.setAdapter(adapterChannelSpinner);//set adapter to spinner

        channelSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                //implement this
                channel = channelSpinnerList[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        devTypeSpinner = (Spinner) findViewById(R.id.deviceTypeSpinner);
        adapterDevTypeSpinner = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,devTypeSpinnerList);

        devTypeSpinner.setAdapter(adapterDevTypeSpinner);//set adapter to spinner

        devTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                //implement this
                devType = devTypeSpinnerList[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if(listId != -1) {
            int spinnerPos = adapterDeviceIDSpinner.getPosition(devID);
            deviceIDSpinner.setSelection(spinnerPos);

            spinnerPos = adapterChannelSpinner.getPosition(channel);
            channelSpinner.setSelection(spinnerPos);

            spinnerPos = adapterDevTypeSpinner.getPosition(devType);
            devTypeSpinner.setSelection(spinnerPos);
        }

    }
}
