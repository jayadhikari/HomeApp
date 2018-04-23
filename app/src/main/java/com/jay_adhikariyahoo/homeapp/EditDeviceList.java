package com.jay_adhikariyahoo.homeapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class EditDeviceList extends AppCompatActivity {


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

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if(extras != null)
        {

            String name = extras.getString("name");
            String devID = extras.getString("id");
            String channel = extras.getString("ch");
            String devType = extras.getString("type");

            Log.i("info", name);
            Log.i("info", devID);
            Log.i("info", channel);
            Log.i("info", devType);

            Toast.makeText(this, name, Toast.LENGTH_SHORT).show();
        }


    }
}
