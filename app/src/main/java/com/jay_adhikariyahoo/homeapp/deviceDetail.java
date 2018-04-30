package com.jay_adhikariyahoo.homeapp;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


public class deviceDetail extends AppCompatActivity
{

    String name, devID, channel, devType;
    int listId = -1;

    int hourFrom=0,hourTo=24;
    int minuteFrom=0,minuteTo=0;
    static final int DIALOG_ID_FROM =0,DIALOG_ID_TO=1;
    Button fromBtn,toBtn;
    TextView fromTimeTv, toTimeTv;

    void displayTime(int fromHr,int fromMin,int toHr, int toMin,int dialog)
    {
        String displayMin,displayHr;
        int hourT,minT;

        if(dialog == DIALOG_ID_TO) {
            hourT = toHr; minT = toMin;
        }
        else{
            hourT = fromHr;minT = fromMin;
        }
        displayMin = String.valueOf(minT);
        displayHr = String.valueOf(hourT);

        if(((toHr*60)+toMin) < ((fromHr*60)+fromMin))
        {
            Toast.makeText(deviceDetail.this,"To Time Should Be Greater Than From Time",Toast.LENGTH_LONG).show();

            if(fromMin <10)
                displayMin = "0"+fromMin;
            if(fromHr >=12)
            {
                if(fromHr == 12)
                    displayHr = String.valueOf(12);
                else
                    displayHr = String.valueOf(fromHr - 12);
                displayMin += " PM";
            }
            else
                displayMin +=" AM";

            if(dialog == DIALOG_ID_TO)
                toTimeTv.setText(displayHr+":"+displayMin);
        }
        else
        {
            if(minT <10)
                displayMin = "0"+minT;
            if(hourT >=12)
            {
                if(hourT == 12)
                    displayHr = String.valueOf(12);
                else
                    displayHr = String.valueOf(hourT - 12);

                displayMin += " PM";
            }
            else
                displayMin += " AM";

            if(dialog == DIALOG_ID_TO)
                toTimeTv.setText(displayHr + ":" + displayMin);
            else
                fromTimeTv.setText(displayHr + ":" + displayMin);
        }

    }

    public void showTimePickerDialog()
    {
        fromTimeTv = (TextView) findViewById(R.id.fromTimeTv);
        toTimeTv = (TextView) findViewById(R.id.toTimeTv);

        fromBtn = (Button) findViewById(R.id.fromBtn);
        toBtn = (Button) findViewById(R.id.toBtn);

        fromBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DIALOG_ID_FROM);
            }
        });
        toBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DIALOG_ID_TO);
            }
        });
    }

    @Override
    protected Dialog onCreateDialog(int id)
    {
        if(id == DIALOG_ID_FROM)
            return new TimePickerDialog(deviceDetail.this,kTimePickerListener,hourFrom,minuteFrom,false);
        else if(id == DIALOG_ID_TO)
            return new TimePickerDialog(deviceDetail.this,kTimePickerListener2,hourTo,minuteTo,false);

        return null;
    }
    protected TimePickerDialog.OnTimeSetListener kTimePickerListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

            hourFrom = hourOfDay;
            minuteFrom = minute;
            displayTime(hourFrom, minuteFrom,hourTo, minuteTo,DIALOG_ID_FROM);

        }
    };
    protected TimePickerDialog.OnTimeSetListener kTimePickerListener2 = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

            hourTo = hourOfDay;
            minuteTo = minute;
            displayTime(hourFrom, minuteFrom,hourTo, minuteTo,DIALOG_ID_TO);
        }
    };


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
            bundle.putInt("fromTime",((60*hourFrom)+minuteFrom));
            bundle.putInt("toTime",((60*hourTo)+minuteTo));
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
        showTimePickerDialog();//time picker method


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
            hourTo = extras.getInt("toTime")/60;
            minuteTo = (int) (0.6*(extras.getInt("toTime")%60));
            hourFrom = extras.getInt("fromTime")/60;
            minuteFrom = (int) (0.6*(extras.getInt("fromTime")%60));

            Log.i("device detail list id", String.valueOf(listId));
            Log.i("device detail name", name);
            Log.i("device detail devID", devID);
            Log.i("device detail channel", channel);
            Log.i("device detail dev Type", devType);



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

        if(listId != -1)
        {
            int spinnerPos = adapterDeviceIDSpinner.getPosition(devID);
            deviceIDSpinner.setSelection(spinnerPos);

            spinnerPos = adapterChannelSpinner.getPosition(channel);
            channelSpinner.setSelection(spinnerPos);

            spinnerPos = adapterDevTypeSpinner.getPosition(devType);
            devTypeSpinner.setSelection(spinnerPos);

            displayTime(hourFrom, minuteFrom,hourTo, minuteTo,DIALOG_ID_FROM);
            displayTime(hourFrom, minuteFrom,hourTo, minuteTo,DIALOG_ID_TO);
        }

    }
}
