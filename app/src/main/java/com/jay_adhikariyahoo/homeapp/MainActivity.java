package com.jay_adhikariyahoo.homeapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

//added a comment
public class MainActivity extends AppCompatActivity {


    public class DownloadTask extends AsyncTask<String, Void, String>
    {

        @Override
        protected String doInBackground(String... urls)
        {
            Log.i("URL",urls[0]);

            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;

            try
            {
                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();

                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);

                int data = reader.read();

                while(data != -1)
                {
                    char current = (char)data;
                    result += current;

                    data = reader.read();
                }

            }
            catch (Exception e)
            {

                e.printStackTrace();
            }

            return result;
        }
    }

    public void HttpRequest(String url)
    {
        DownloadTask lightTask = new DownloadTask();

        String result=null;
        try
        {
            result = lightTask.execute(url).get();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        } catch (ExecutionException e)
        {
            e.printStackTrace();
        }
        Log.i("Contents of URL", result);
    }

    public void DisplayMesg(String mesgTodisplay)
    {
        Toast.makeText(this, mesgTodisplay, Toast.LENGTH_SHORT).show();
    }
    public void micClick(View view)
    {
        Log.i("info","mic button pressed");
        DisplayMesg("mic button pressed");
    }
    public void lightClick(View view)
    {
        Log.i("info","light button pressed");
        DisplayMesg("light button pressed");

        HttpRequest("http://192.168.1.3:80/rf?D=4&t=200&id=28015&on=1&channel=1");

    }
    public void fanClick(View view)
    {
        Log.i("info","fan button pressed");
        DisplayMesg("fan button pressed");

        HttpRequest("http://192.168.1.3:80/rf?D=4&t=200&id=28015&on=1&channel=2");
    }
    public void tvClick(View view)
    {
        Log.i("info","tv button pressed");
        DisplayMesg("tv button pressed");
    }
    public void settingClick(View view)
    {
        Log.i("info","settings button pressed");
        DisplayMesg("settings button pressed");

        Intent intent = new Intent(getApplicationContext(),addDevice.class);
        startActivity(intent);

    }
    public void searchClick(View view)
    {
        EditText myTextView = (EditText) findViewById(R.id.myTextView);//creates a text view variable and gets the text from the text view

        Log.i("info","search button pressed");
        DisplayMesg(myTextView.getText().toString());
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
