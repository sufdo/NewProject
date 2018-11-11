package com.example.user.project;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.user.project.model.AthleteInitial;
import com.example.user.project.model.Fitness;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TSB extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    String trainerID;
    Spinner spinner2;
    ArrayAdapter<String> adapter2;
    ProgressBar mProgressBar2;
    static String stream=null;
    List<AthleteInitial> athleteList=new ArrayList<AthleteInitial>();
    List athleteArr =new ArrayList();
    String athleteName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.tsb_ui);
        setContentView(R.layout.activity_tsb);

        SharedPreferences sharedPref=getApplicationContext().getSharedPreferences("TrainerPref",0);
        trainerID=sharedPref.getString("TrainerID","");

        mProgressBar2=(ProgressBar)findViewById(R.id.progressBar2);
        mProgressBar2.setVisibility(View.GONE);

        spinner2= findViewById(R.id.spinner_tsb);
        adapter2= new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,athleteArr);
        new GetData().execute();

        spinner2.setOnItemSelectedListener(this);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        athleteName= parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(),athleteName,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    private class GetData extends AsyncTask<Void,Void,Void> {

        protected void onPreExecute() {
            super.onPreExecute();
            mProgressBar2.setVisibility(View.VISIBLE);

        }
        @Override
        protected Void doInBackground(Void... voids) {
            doGet();
            return null;
        }
        protected void doGet()
        {
            try
            {
                URL url = new URL("https://murmuring-cove-69371.herokuapp.com/athleteInit/trainerID/"+trainerID);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                //check the connection status
                if(conn.getResponseCode()==200)
                {
                    //if response code=200 - HTTP.OK
                    InputStream in=new BufferedInputStream(conn.getInputStream());

                    //read the BufferedInputStream
                    BufferedReader r=new BufferedReader(new InputStreamReader(in));
                    StringBuilder sb=new StringBuilder();
                    String line;
                    while ((line=r.readLine())!=null)
                    {
                        sb.append(line);
                    }
                    stream=sb.toString();
                    conn.disconnect();
                }

                else
                {

                }

                Gson gson=new Gson();
                Type listType=new TypeToken<List<AthleteInitial>>(){}.getType();
                athleteList=gson.fromJson(stream,listType);//parse to list



            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        protected void onPostExecute(Void voids) {
            super.onPostExecute(voids);


            if(!athleteList.isEmpty())
            {
                Log.i("TSB", "size"+athleteList.size());

                    for(int i=0;i<(athleteList.size()-1);i++)
                    {
                        Log.i("TSB", "name"+athleteList.get(i).getName());
                        Log.i("TSB" , "nic"+athleteList.get(i).getNic());
                        if(athleteArr.isEmpty())
                        {
                            athleteArr.add(athleteList.get(i).getName());
                        }
                        else
                        {
                            if(!athleteArr.contains(athleteList.get(i).getName()))
                            {
                                athleteArr.add(athleteList.get(i).getName());
                            }
                        }
                    }
                spinner2.setAdapter(adapter2);
                mProgressBar2.setVisibility(View.GONE);
            }
            else
            {

            }


        }
    }
}
