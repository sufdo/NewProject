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
import android.widget.TextView;
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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TSB extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    String trainerID;
    Spinner spinner2;
    ArrayAdapter<String> adapter2;
    ProgressBar mProgressBar2;
    static String stream3=null;
    static String stream4=null;
    List<AthleteInitial> athleteList=new ArrayList<AthleteInitial>();
    List athleteArr =new ArrayList();
    String athleteName;
    HashMap<String,String> nameNic=new HashMap<String, String>();
    private TextView textView_latestTSB;
    String nic;
    List<Fitness> fitnessList2=new ArrayList<Fitness>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.tsb_ui);
        setContentView(R.layout.activity_tsb);

        SharedPreferences sharedPref=getApplicationContext().getSharedPreferences("TrainerPref",0);
        trainerID=sharedPref.getString("TrainerID","");

        textView_latestTSB=findViewById(R.id.latest_tsb_value);

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

        nic=nameNic.get(athleteName);
        //textView_latestTSB.setText(nic);
        new GetAthleteFitnessData2().execute();

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
                    stream3=sb.toString();
                    conn.disconnect();
                }

                else
                {

                }

                Gson gson=new Gson();
                Type listType=new TypeToken<List<AthleteInitial>>(){}.getType();
                athleteList=gson.fromJson(stream3,listType);//parse to list



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
                            nameNic.put(athleteList.get(i).getName(),athleteList.get(i).getNic());
                        }
                        else
                        {
                            if(!athleteArr.contains(athleteList.get(i).getName()))
                            {
                                athleteArr.add(athleteList.get(i).getName());
                                nameNic.put(athleteList.get(i).getName(),athleteList.get(i).getNic());
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

    private class GetAthleteFitnessData2 extends AsyncTask<Void,Void,Void> {

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
                URL url = new URL("https://murmuring-cove-69371.herokuapp.com/fitness/"+nic);
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
                    stream4=sb.toString();
                    conn.disconnect();
                }

                else
                {

                }

                Gson gson=new Gson();
                Type listType=new TypeToken<List<Fitness>>(){}.getType();
                fitnessList2=gson.fromJson(stream4,listType);//parse to list



            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        protected void onPostExecute(Void voids) {
            super.onPostExecute(voids);
            //textView_latestTSB.setText("hello");
            Log.i("TSB" , "LATEST TSB = "+fitnessList2.size());
            if(!fitnessList2.isEmpty())
            {
                for(int i=fitnessList2.size()-1;i>((fitnessList2.size()-1)-1);i--)
                 {
                     Log.i("TSB" , "fitness size minus one := "+(fitnessList2.size()-1));
                     Log.i("TSB" , "fitness size minus one minus onw := "+((fitnessList2.size()-1)-1));
                     Log.i("TSB" , "fitnessPerDay := "+fitnessList2.get(i).getFitnessPerDay());
                     Log.i("TSB" , "fatiguePerDay := "+fitnessList2.get(i).getFatiguePerDay());
                     Log.i("TSB" , "TSB := "+fitnessList2.get(i).getTsb());
                     Log.i("TSB" , "ATL := "+fitnessList2.get(i).getAtl());
                     Log.i("TSB" , "CTL := "+fitnessList2.get(i).getCtl());
                     textView_latestTSB.setText(fitnessList2.get(i).getTsb());
                 }
            }
            mProgressBar2.setVisibility(View.GONE);


        }
    }
}
