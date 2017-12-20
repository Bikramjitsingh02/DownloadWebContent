package com.example.bikramjitsingh.downloadwebcontent;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {


   TextView textView;


    public class DownloadTask extends AsyncTask<String ,Void,String>{

        @Override
        protected String doInBackground(String... params) {

            String result="";
            URL url;
            HttpURLConnection urlConnection=null;
            try{
                url=new URL(params[0]);
                urlConnection=(HttpURLConnection) url.openConnection();
                InputStream is=urlConnection.getInputStream();
                InputStreamReader reader=new InputStreamReader(is);
                int data=reader.read();
                while(data!=-1){
                    char ch=(char)data;
                    result+=ch;
                    data=reader.read();
                }
                return result;
            }
            catch (Exception e ){
                e.printStackTrace();
            }

            return "Done";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                JSONObject jsonObject=new JSONObject(s);

                String weather=jsonObject.getString("weather");


                JSONArray jsonArray=new JSONArray(weather);

               for(int i=0;i<jsonArray.length();i++){
                   JSONObject jsonPart=jsonArray.getJSONObject(i);
                   textView.setText(jsonPart.toString());
                   System.out.println("this is for loop");
                    Log.i("lon",jsonPart.getString("main"));
                   Log.i("lat",jsonPart.getString("lat"));

               }



            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DownloadTask task=new DownloadTask();
        textView=(TextView)findViewById(R.id.textView);
        try {
          String string=task.execute("http://samples.openweathermap.org/data/2.5/weather?q=London,uk&appid=b1b15e88fa797225412429c1c50c122a1").get();
         // System.out.println(string);

        } catch (Exception e) {
            e.printStackTrace();
        }



    }
}
