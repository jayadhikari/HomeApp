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

   /* public void okClick(View view)
    {
        Intent intent = new Intent(getApplicationContext(),addDevice.class);
        startActivity(intent);
    }
    public void addDeviceClick(View view)
    {
        Intent intent = new Intent(getApplicationContext(),deviceDetail.class);
        startActivity(intent);

    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_device_list);

        Log.d(TAG,"OnCreate: Started.");

        final ListView deviceListView = (ListView) findViewById(R.id.deviceList);
       // devListAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,devNames);

        //devNames.add(0,"light");
        //create device list objects

        /*deviceClass dev1 = new deviceClass("dev1","OFF","Light");
        deviceClass dev2 = new deviceClass("dev2","OFF","Light");
        deviceClass dev3 = new deviceClass("dev3","OFF","Light");
        deviceClass dev4 = new deviceClass("dev4","OFF","Light");
        deviceClass dev5 = new deviceClass("dev5","OFF","Light");
        deviceClass dev6 = new deviceClass("dev6","OFF","Light");
        deviceClass dev7 = new deviceClass("dev7","OFF","Light");
        deviceClass dev8 = new deviceClass("dev8","OFF","Light");
        deviceClass dev9 = new deviceClass("dev9","OFF","Light");
        deviceClass dev10 = new deviceClass("dev10","OFF","Light");
*/
        //add deviceclass objects to device class list
        final ArrayList<deviceClass> deviceClassList = new ArrayList<>();
 /*       deviceClassList.add(dev1);
        deviceClassList.add(dev2);
        deviceClassList.add(dev3);
        deviceClassList.add(dev4);
        deviceClassList.add(dev5);
        deviceClassList.add(dev6);
        deviceClassList.add(dev7);
        deviceClassList.add(dev8);
        deviceClassList.add(dev9);
        deviceClassList.add(dev10);
*/


/*
        deviceListView.setAdapter(devListAdapter);
        deviceListView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

*/
        deviceClassAdapter adapterDC = new deviceClassAdapter(this,R.layout.editdevadapter,deviceClassList);
        deviceListView.setAdapter(adapterDC);


        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if(extras != null)
        {
             int listId = extras.getInt("listId");
             name = extras.getString("name");
             devID = extras.getString("id");
             channel = extras.getString("ch");
             devType = extras.getString("type");

            Log.i("from intent", String.valueOf(listId));
            Log.i("from intent", name);
            Log.i("from intent", devID);
            Log.i("from intent", channel);
            Log.i("from intent", devType);
          //  Toast.makeText(this, name, Toast.LENGTH_SHORT).show();
            try
            {
                devDatabase = this.openOrCreateDatabase("DeviceData", MODE_PRIVATE, null);
                if(listId != -1) {
                    listId +=1;
                    devDatabase.execSQL("UPDATE deviceTable1 SET name='"+ name +"', deviceID='" +devID+ "',channel='"+channel+"',status ='OFF',devType = '"+ devType +"' WHERE id="+listId);
                }
                else{
                    devDatabase.execSQL("INSERT INTO deviceTable1 (name,deviceID, channel,status, devType) VALUES ('" + name + "','" + devID + "','" + channel + "','OFF','" + devType + "')");

                }

                    //devDatabase.execSQL("INSERT INTO device (name,deviceID, channel, status) VALUES ('name','devID','channel','OFF')");
                Cursor c = devDatabase.rawQuery("SELECT * FROM deviceTable1", null);

                int nameIndex = c.getColumnIndex("name");
                int devTypeIndex = c.getColumnIndex("devType");
                int statusIndex = c.getColumnIndex("status");

                c.moveToFirst();

                while(c != null)
                {
                    deviceClass dev = new deviceClass(c.getString(nameIndex),c.getString(devTypeIndex),c.getString(statusIndex));
                    deviceClassList.add(dev);

                  //  Log.i("in intent",c.getString(nameIndex));
                    c.moveToNext();
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }

        }
        else
        {
            try
            {//initialise the database
                devDatabase = this.openOrCreateDatabase("DeviceData",MODE_PRIVATE, null);
                devDatabase.execSQL("CREATE TABLE IF NOT EXISTS deviceTable1 (name VARCHAR, deviceID VARCHAR, channel VARCHAR, status VARCHAR, devType VARCHAR, id INTEGER PRIMARY KEY )");
                //devDatabase.execSQL("CREATE TABLE IF NOT EXISTS device (name VARCHAR)");

                // devDatabase.execSQL("INSERT INTO device (name,deviceID,channel,status) VALUES ('fan','28019','3','OFF')");
                //devDatabase.execSQL("INSERT INTO device (name,deviceID,channel,status) VALUES ('TV','28029','2','OFF')");
                // devDatabase.execSQL("INSERT INTO device (name) VALUES ('jay')");
                // devDatabase.execSQL("INSERT INTO device (name) VALUES ('vijay')");

                // devDatabase.execSQL("DELETE FROM device WHERE name ='vijay' ");
                //devDatabase.execSQL("DELETE FROM device WHERE name ='jay' ");
               // devDatabase.execSQL("ALTER TABLE device ADD COLUMN devType VARCHAR");

                Cursor c = devDatabase.rawQuery("SELECT * FROM deviceTable1", null);

                int nameIndex = c.getColumnIndex("name");
                int devTypeIndex = c.getColumnIndex("devType");
                int statusIndex = c.getColumnIndex("status");

                c.moveToFirst();

                while(c != null)
                {
                    Log.i("log from SQL", c.getString(nameIndex));
                    Log.i("log from SQL", c.getString(devTypeIndex));
                    Log.i("log from SQL", c.getString(statusIndex));

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
                    Cursor c = devDatabase.rawQuery("SELECT * FROM deviceTable1", null);
                    //c.moveToFirst();
                    c.moveToPosition(position);//go to current list view position clicked by user

                    int idIndex = position;//row count
                    int nameIndex = c.getColumnIndex("name");
                    int devIdIndex = c.getColumnIndex("deviceID");
                    int chIndex = c.getColumnIndex("channel");
                    int statusIndex = c.getColumnIndex("status");
                    int typeIndex = c.getColumnIndex("devType");

                    name = c.getString(nameIndex);
                    devID = c.getString(devIdIndex);
                    channel = c.getString(chIndex);
                    devType = c.getString(typeIndex);

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
                mainActivity.HttpRequest("http://192.168.0.103:80/rf?D=4&t=200&id="+devID+"&on=1&channel="+channel);

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
                    Cursor c = devDatabase.rawQuery("SELECT * FROM deviceTable1", null);
                    //c.moveToFirst();
                    c.moveToPosition(position);//go to current list view position clicked by user

                    int idIndex = position;//row count
                    int nameIndex = c.getColumnIndex("name");
                    int devIdIndex = c.getColumnIndex("deviceID");
                    int chIndex = c.getColumnIndex("channel");
                    int statusIndex = c.getColumnIndex("status");
                    int typeIndex = c.getColumnIndex("devType");

                    name = c.getString(nameIndex);
                    devID = c.getString(devIdIndex);
                    channel = c.getString(chIndex);
                    devType = c.getString(typeIndex);

                    Log.i("longClick", String.valueOf(idIndex));
                    Log.i("longClick", name);
                    Log.i("longClick", devID);
                    Log.i("longClick", channel);
                    Log.i("longClick", c.getString(statusIndex));
                    Log.i("longClick", devType);
                }
                catch(Exception e)
                {
                    Log.i("longClick", "exception generated");
                    e.printStackTrace();

                }
                Intent intent = new Intent(getApplicationContext(), deviceDetail.class);
                Bundle bundle = new Bundle();

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
