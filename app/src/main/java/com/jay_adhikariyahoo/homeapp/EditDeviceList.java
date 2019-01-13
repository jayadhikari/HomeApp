package com.jay_adhikariyahoo.homeapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class EditDeviceList extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    String name;
    String devID;
    String channel;
    String devType;
    int toTime;
    int fromTime;

    SQLiteDatabase devDatabase;

     ArrayList<String> devNames = new ArrayList<>();
     ArrayAdapter devListAdapter;


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            Intent intent = new Intent(getApplicationContext(),addDevice.class);
            startActivity(intent);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.editdevice,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        super.onOptionsItemSelected(item);

        switch (item.getItemId())
        {
            case R.id.addDev:
                Intent intent = new Intent(getApplicationContext(),deviceDetail.class);
                startActivity(intent);
                return true;
            case R.id.removeDev:
                return true;
            case R.id.editDev:
                return true;
            default:
                return false;

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_device_list);

        Log.d(TAG,"OnCreate: Started.");

        final ListView deviceListView = (ListView) findViewById(R.id.deviceList);

        //add deviceclass objects to device class list
        final ArrayList<deviceClass> deviceClassList = new ArrayList<>();

        deviceClassAdapter adapterDC = new deviceClassAdapter(this,R.layout.editdevadapter,deviceClassList);
        deviceListView.setAdapter(adapterDC);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if(extras != null)
        {//control come here if its passed from device detail activity

            Log.i("Edit device List", "Rec bundle info from intent");

             int listId = extras.getInt("listId");
             name = extras.getString("name");
             devID = extras.getString("id");
             channel = extras.getString("ch");
             devType = extras.getString("type");
             toTime = extras.getInt("toTime");
             fromTime = extras.getInt("fromTime");

            Log.i("from intent", String.valueOf(listId));
            Log.i("from intent", name);
            Log.i("from intent", devID);
            Log.i("from intent", channel);
            Log.i("from intent", devType);
            Log.i("from intent to time", String.valueOf(toTime));
            Log.i("from intent to time", String.valueOf(fromTime));
          //  Toast.makeText(this, name, Toast.LENGTH_SHORT).show();
            try
            {
                devDatabase = this.openOrCreateDatabase("DeviceData", MODE_PRIVATE, null);
                if(listId != -1) {
                    listId +=1;
                    devDatabase.execSQL("UPDATE deviceTable2 SET name='"+ name +"', deviceID='" +devID+ "',channel='"+channel+"',status ='OFF',devType = '"+ devType +"',toTime = "+toTime+",fromTime ="+fromTime+" WHERE id="+listId);
                }
                else{
                    devDatabase.execSQL("INSERT INTO deviceTable2 (name,deviceID, channel,status, devType, toTime, fromTime)" +
                                                " VALUES ('"+name+"','"+devID+"','"+channel+"','OFF','"+devType+"',"+toTime+","+fromTime+")");

                }
                    //devDatabase.execSQL("INSERT INTO device (name,deviceID, channel, status) VALUES ('name','devID','channel','OFF')");
                Cursor c = devDatabase.rawQuery("SELECT * FROM deviceTable2", null);

                int nameIndex = c.getColumnIndex("name");
                int devTypeIndex = c.getColumnIndex("devType");
                int statusIndex = c.getColumnIndex("status");
                int toTimeIndex = c.getColumnIndex("toTime");
                int fromTimeIndex = c.getColumnIndex("fromTime");

                c.moveToFirst();

                while(c != null)
                {
                    deviceClass dev = new deviceClass(c.getString(nameIndex),c.getString(devTypeIndex),c.getString(statusIndex));
                    deviceClassList.add(dev);

                    Log.i("after saving DB to time", String.valueOf(c.getInt(toTimeIndex)));
                    Log.i("after saving DB frtime", String.valueOf(c.getInt(fromTimeIndex)));
                    c.moveToNext();
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        else
        {//this block gets executed when app transitions from add device activity to edit device list activity. i.e first time when app starts
            Log.i("Edit device List", "No info from intent");
            try
            {//initialise the database
                devDatabase = this.openOrCreateDatabase("DeviceData",MODE_PRIVATE, null);
                devDatabase.execSQL("CREATE TABLE IF NOT EXISTS deviceTable2 (name VARCHAR, deviceID VARCHAR, channel VARCHAR, status VARCHAR, devType VARCHAR, toTime INTEGER, fromTime INTEGER, id INTEGER PRIMARY KEY )");
                // devDatabase.execSQL("INSERT INTO device (name,deviceID,channel,status) VALUES ('fan','28019','3','OFF')");
                // devDatabase.execSQL("DELETE FROM device WHERE name ='vijay' ");
               // devDatabase.execSQL("ALTER TABLE device ADD COLUMN devType VARCHAR");

                Cursor c = devDatabase.rawQuery("SELECT * FROM deviceTable2", null);

                int nameIndex = c.getColumnIndex("name");
                int devTypeIndex = c.getColumnIndex("devType");
                int statusIndex = c.getColumnIndex("status");
                int toTimeIndex = c.getColumnIndex("toTime");
                int fromTimeIndex = c.getColumnIndex("fromTime");

                c.moveToFirst();

                while(c != null)
                {
                    Log.i("Edit dev list", c.getString(nameIndex));
                    Log.i("Edit dev list", c.getString(devTypeIndex));
                    Log.i("Edit dev list", c.getString(statusIndex));
                    Log.i("Edit dev list to", String.valueOf(c.getInt(toTimeIndex)));
                    Log.i("Edit dev list from", String.valueOf(c.getInt(fromTimeIndex)));

                    deviceClass dev = new deviceClass(c.getString(nameIndex),c.getString(devTypeIndex),c.getString(statusIndex));
                    deviceClassList.add(dev);

                    c.moveToNext();
                }

            }catch(Exception e)
            {
                e.printStackTrace();

            }

        }

        deviceListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                try
                {//initialise the database
                    //  devDatabase = getApplicationContext().openOrCreateDatabase("DeviceData",MODE_PRIVATE, null);
                    Cursor c = devDatabase.rawQuery("SELECT * FROM deviceTable2", null);
                    //c.moveToFirst();
                    c.moveToPosition(position);//go to current list view position clicked by user

                    int idIndex = position;//row count
                    int nameIndex = c.getColumnIndex("name");
                    int devIdIndex = c.getColumnIndex("deviceID");
                    int chIndex = c.getColumnIndex("channel");
                    int statusIndex = c.getColumnIndex("status");
                    int typeIndex = c.getColumnIndex("devType");
                    int toTimeIndex= c.getColumnIndex("toTime");
                    int fromTimeIndex = c.getColumnIndex("fromTime");

                    name = c.getString(nameIndex);
                    devID = c.getString(devIdIndex);
                    channel = c.getString(chIndex);
                    devType = c.getString(typeIndex);
                    toTime = c.getInt(toTimeIndex);
                    fromTime = c.getInt(fromTimeIndex);

                    Log.i("shortClick", String.valueOf(idIndex));
                    Log.i("shortClick", name);
                    Log.i("shortClick", devID);
                    Log.i("shortClick", channel);
                    Log.i("shortClick", c.getString(statusIndex));
                    Log.i("shortClick", devType);
                }
                catch(Exception e)
                {
                    Log.i("longClick", "exception generated");
                    e.printStackTrace();

                }
                 MainActivity mainActivity= new MainActivity();
                //mainActivity.HttpRequest("http://192.168.0.103:80/rf?D=4&t=200&id="+devID+"&on=1&channel="+channel);
                mainActivity.HttpRequest("http://192.168.1.3:80/time?D=4&t=200&id="+devID+"&on=1&channel="+channel+"&timeTo="+toTime+"&timeFr="+fromTime);

            }
        });
        deviceListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id)
            {
              /*
               //use this to find current list view items
                String name = ((TextView) view.findViewById(R.id.layoutTextView1)).getText().toString();
                String name = ((TextView) view.findViewById(R.id.layoutTextView2)).getText().toString();
                String name = ((TextView) view.findViewById(R.id.layoutTextView3)).getText().toString();
               */
                Log.i("longClick", "Long click done");
                try
                {//initialise the database
                  //  devDatabase = getApplicationContext().openOrCreateDatabase("DeviceData",MODE_PRIVATE, null);
                    Cursor c = devDatabase.rawQuery("SELECT * FROM deviceTable2", null);

                    c.moveToPosition(position);//go to current list view position clicked by user

                    int idIndex = position;//row count
                    int nameIndex = c.getColumnIndex("name");
                    int devIdIndex = c.getColumnIndex("deviceID");
                    int chIndex = c.getColumnIndex("channel");
                    int statusIndex = c.getColumnIndex("status");
                    int typeIndex = c.getColumnIndex("devType");
                    int toTimeIndex= c.getColumnIndex("toTime");
                    int fromTimeIndex = c.getColumnIndex("fromTime");

                    Log.i("index idIndex", String.valueOf(idIndex));
                    Log.i("index nameIndex", String.valueOf(nameIndex));
                    Log.i("index devIdIndex", String.valueOf(devIdIndex));
                    Log.i("index chIndex", String.valueOf(chIndex));
                    Log.i("index statusIndex", String.valueOf(statusIndex));
                    Log.i("index typeIndex", String.valueOf(typeIndex));
                    Log.i("index toTimeIndex", String.valueOf(toTimeIndex));
                    Log.i("index fromTimeIndex", String.valueOf(fromTimeIndex));




                    name = c.getString(nameIndex);
                    devID = c.getString(devIdIndex);
                    channel = c.getString(chIndex);
                    devType = c.getString(typeIndex);
                    toTime = c.getInt(toTimeIndex);
                    fromTime = c.getInt(fromTimeIndex);

                    Log.i("longClick index", String.valueOf(idIndex));
                    Log.i("longClick name", name);
                    Log.i("longClick devID", devID);
                    Log.i("longClick channel", channel);
                    Log.i("longClick status", c.getString(statusIndex));
                    Log.i("longClick devType", devType);
                    Log.i("longClick fromTime", String.valueOf(fromTime));
                    Log.i("longClick toTime", String.valueOf(toTime));

                }
                catch(Exception e)
                {
                    Log.i("longClick", "exception generated");
                    e.printStackTrace();

                }
                Intent intent = new Intent(getApplicationContext(), deviceDetail.class);
                Bundle bundle = new Bundle();

                bundle.putInt("toTime", toTime);
                bundle.putInt("fromTime", fromTime);
                bundle.putInt("listId", position);
                bundle.putString("name", name);
                bundle.putString("id", devID);
                bundle.putString("ch", channel);
                bundle.putString("type", devType);
                intent.putExtras(bundle);

                startActivity(intent);

                return true;//set to true so that a long click will not be also assumed as a short click
            }
        });


    }
}
