package com.example.user.project;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.user.project.model.AthleteInitial;
import com.example.user.project.model.Fitness;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FitnessQuestionnaire extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    //String ServerURL = "https://murmuring-cove-69371.herokuapp.com/athleteInit/trainerID";
    String ServerURL;
    String id,athlete,nic,date,dob,trainer,approved;
    String trainerID;

    List<AthleteInitial> athleteList=new ArrayList<AthleteInitial>();
    List athleteArr =new ArrayList();
    Spinner spinner;
    ArrayAdapter<String> adapter;
    ProgressBar mProgressBar;

    private EditText editText_totalShuttle;
    private EditText editText_flightTime;

    private RadioButton type_radio1,type_radio2,type_radio3,fatigue_radio1,fatigue_radio2,fatigue_radio3,fatigue_radio4;

    String type,fatigueLevel;

    String shuttleLoad;
    String flightTime;
    String athleteName;
    Long dateLong;
    String currentDate;
    String CTL,fitnessPerDay,fatiguePerDay,ATL,TSB;

    double score,fitnessPerDayDouble,flightTimeDouble,jumpHeight,fatiguePerDayDouble;
    double weight_ShuttleLoad,weight_cmj,weight_fatigue;
    int fatigueLevelInt;
    Validation validation=new Validation();
    static String stream=null;
    String message;
    List<Fitness> fitnessList=new ArrayList<Fitness>();
    int count;
    Double ATLdouble=0.0;
    Double CTLdouble=0.0;
    long TSBLong=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.fitness_q);
        setContentView(R.layout.activity_fitness_questionnaire);

        SharedPreferences sharedPref=getApplicationContext().getSharedPreferences("TrainerPref",0);
        trainerID=sharedPref.getString("TrainerID","");

        mProgressBar=(ProgressBar)findViewById(R.id.progressBar);
        mProgressBar.setVisibility(View.GONE);

        spinner= findViewById(R.id.spinner);
        adapter= new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,athleteArr);
        new GetData().execute();

        spinner.setOnItemSelectedListener(this);

        editText_totalShuttle=findViewById(R.id.totalShuttle_txt);
        editText_flightTime=findViewById(R.id.flightTime_txt);

        type_radio1=(RadioButton)findViewById(R.id.type1);
        type_radio2=(RadioButton)findViewById(R.id.type2);
        type_radio3=(RadioButton)findViewById(R.id.type3);

        fatigue_radio1=(RadioButton)findViewById(R.id.fatigue1);
        fatigue_radio2=(RadioButton)findViewById(R.id.fatigue2);
        fatigue_radio3=(RadioButton)findViewById(R.id.fatigue3);
        fatigue_radio4=(RadioButton)findViewById(R.id.fatigue4);

        type_radio1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type=type_radio1.getText().toString();
            }
        });
        type_radio2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type=type_radio2.getText().toString();
            }
        });
        type_radio3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type=type_radio3.getText().toString();
            }
        });

        fatigue_radio1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fatigueLevel=fatigue_radio1.getText().toString();
            }
        });
        fatigue_radio2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fatigueLevel=fatigue_radio2.getText().toString();
            }
        });
        fatigue_radio3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fatigueLevel=fatigue_radio3.getText().toString();
            }
        });
        fatigue_radio4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fatigueLevel=fatigue_radio4.getText().toString();
            }
        });

    }

    public void fitnessSubmit_clicked(View view)
    {
        if(view.getId()==R.id.fitnessSubmit)
        {
            shuttleLoad=editText_totalShuttle.getText().toString();
            flightTime=editText_flightTime.getText().toString();


            //dateLong=System.currentTimeMillis();
            //currentDate=Long.toString(dateLong);
            DateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");
            Date date=new Date();
            currentDate=dateFormat.format(date);
            /**
            toastMessage("trainerID ="+trainerID);
            toastMessage("athleteNIC ="+nic);
            toastMessage("athleteName ="+athleteName);
            toastMessage("shuttleLoad ="+shuttleLoad);
            toastMessage("type ="+type);
            toastMessage("flightTime ="+flightTime);
            toastMessage("fatigueLevel ="+fatigueLevel);
            toastMessage("currentDate ="+currentDate);
            **/

            Boolean result=validation.isEmpty(athleteName,shuttleLoad,type,flightTime,fatigueLevel);
            if(result.equals(false))
            {
                weight_ShuttleLoad=1.0;
                weight_cmj=0.75;
                weight_fatigue=0.25;
                int shuttleLoadInt=Integer.parseInt(shuttleLoad);
                if((shuttleLoadInt>=700)&&(shuttleLoadInt<=750))
                {
                    score=2;
                }
                else if((shuttleLoadInt>=650)&&(shuttleLoadInt<700))
                {
                    score=2;
                }
                else if((shuttleLoadInt>=600)&&(shuttleLoadInt<650))
                {
                    score=1.5;
                }
                else if((shuttleLoadInt>=550)&&(shuttleLoadInt<600))
                {
                    score=1;
                }
                else
                {
                    score=0;
                }
                fitnessPerDayDouble=((score*weight_ShuttleLoad)*100)/2;
                fitnessPerDayDouble=Math.round(fitnessPerDayDouble*100.0)/100.0;
                fitnessPerDay=Double.toString(fitnessPerDayDouble);

                flightTimeDouble=Double.parseDouble(flightTime);
                jumpHeight=(9.81*(Math.pow(flightTimeDouble,2)))/8;

                switch (fatigueLevel)
                {
                    case "None":
                    {
                        fatigueLevelInt=0;
                        break;
                    }
                    case "Mild":
                    {
                        fatigueLevelInt=1;
                        break;
                    }
                    case "Moderate":
                    {
                        fatigueLevelInt=2;
                        break;
                    }
                    case "Severe":
                    {
                        fatigueLevelInt=3;
                        break;
                    }
                    case "Worst":
                    {
                        fatigueLevelInt=4;
                        break;
                    }
                    default:
                    {
                        fatigueLevelInt=-1;
                        break;
                    }
                }

                fatiguePerDayDouble=((jumpHeight*weight_cmj)+(fatigueLevelInt*weight_fatigue))*100;
                fatiguePerDayDouble=Math.round(fatiguePerDayDouble*100.0)/100.0;
                fatiguePerDay=Double.toString(fatiguePerDayDouble);

                new GetAthleteFitnessData().execute();

                new SendData().execute();
            }
            else
            {
                AlertDialog dialog=new AlertDialog.Builder(this).setTitle("Error").setMessage("All fields need to be filled").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create();
                dialog.show();
            }


        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        athleteName= parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(),athleteName,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    //get athlete name and other details
    private class GetData extends AsyncTask<Void,Void,Void> {

        protected void onPreExecute() {
            super.onPreExecute();
            mProgressBar.setVisibility(View.VISIBLE);

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
                Log.i("FITNESS_Q", "size"+athleteList.size());

                for(int i=0;i<(athleteList.size()-1);i++)
                {
                    Log.i("FITNESS_Q", "name"+athleteList.get(i).getName());
                    Log.i("FITNESS_Q" , "nic"+athleteList.get(i).getNic());
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
                spinner.setAdapter(adapter);
                mProgressBar.setVisibility(View.GONE);
            }
            else
            {

            }


        }
    }
    //post fitness details
    class SendData extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] objects) {
            doPost();
            return null;
        }

        protected void doPost() {
            try {
                URL url = new URL("https://murmuring-cove-69371.herokuapp.com/fitness");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("Accept", "application/json");
                conn.setDoOutput(true);
                conn.setDoInput(true);
                conn.connect();

                JSONObject jsonParam = new JSONObject();
                jsonParam.put("trainerID", trainerID);
                jsonParam.put("athleteNIC", nic);
                jsonParam.put("athleteName", athleteName);
                jsonParam.put("shuttleLoad", shuttleLoad);
                jsonParam.put("type", type);
                jsonParam.put("flightTime", flightTime);
                jsonParam.put("fatigueLevel", fatigueLevel);
                jsonParam.put("fitnessPerDay", fitnessPerDay);
                jsonParam.put("CTL", CTL);
                jsonParam.put("fatiguePerDay", fatiguePerDay);
                jsonParam.put("ATL", ATL);
                jsonParam.put("TSB", TSB);
                jsonParam.put("date", currentDate);


                conn.getOutputStream();
                try {
                    DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                    os.writeBytes(jsonParam.toString());

                    os.flush();
                    os.close();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (ProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Log.i("STATUS", String.valueOf(conn.getResponseCode()));
                Log.i("MSG" , conn.getResponseMessage());
                if(conn.getResponseCode()==200)
                {
                    message="Successfully saved";
                }
                else
                {
                    message="Error";
                }
                conn.disconnect();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        protected void onPostExecute(Object object) {
            super.onPostExecute(object);
            toastMessage(message);
            Intent intent=new Intent(FitnessQuestionnaire.this,TSB.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
    }

    private class GetAthleteFitnessData extends AsyncTask<Void,Void,Void> {

        protected void onPreExecute() {
            super.onPreExecute();

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
                    stream=sb.toString();
                    conn.disconnect();
                    //message="succesfully saved";
                }

                else
                {
                   // message="error not saved";
                }

                Gson gson=new Gson();
                Type listType=new TypeToken<List<Fitness>>(){}.getType();
                fitnessList=gson.fromJson(stream,listType);//parse to list



            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        protected void onPostExecute(Void voids) {
            super.onPostExecute(voids);
            //toastMessage(message);

            if(!fitnessList.isEmpty())
            {
                Log.i("FITNESS", "size"+fitnessList.size());
                if(fitnessList.size()>=6)
                {
                    for(int i=fitnessList.size()-1;i>(fitnessList.size()-1)-6;i--)
                    {
                        Log.i("FATIGUE", "date"+fitnessList.get(i).getDate());
                        Log.i("FATIGUE" , "fitnessPerDay"+fitnessList.get(i).getFitnessPerDay());
                        Log.i("FATIGUE", "fatiguePerDay"+fitnessList.get(i).getFatiguePerDay());
                        Log.i("FATIGUE" , "CTL"+fitnessList.get(i).getCTL());
                        Log.i("FATIGUE" , "ATL"+fitnessList.get(i).getATL());

                        ATLdouble=ATLdouble+Double.parseDouble(fitnessList.get(i).getFatiguePerDay());
                        Log.i("FATIGUE" , "ATLdouble"+ATLdouble);
                        /**count +=1;
                         Log.i("FATIGUE", "count"+count);

                         if(count>=6)
                         {

                         }**/
                    }

                    ATLdouble=(ATLdouble+fatiguePerDayDouble)/7;
                    ATLdouble=Math.round(ATLdouble*100.0)/100.0;
                    ATL=Double.toString(ATLdouble);

                    if(fitnessList.size()>=14)
                    {
                        for(int i=fitnessList.size()-1;i>(fitnessList.size()-1)-14;i--)
                        {
                            Log.i("FITNESS", "date"+fitnessList.get(i).getDate());
                            Log.i("FITNESS" , "fitnessPerDay"+fitnessList.get(i).getFitnessPerDay());
                            Log.i("FITNESS", "fatiguePerDay"+fitnessList.get(i).getFatiguePerDay());
                            Log.i("FITNESS" , "CTL"+fitnessList.get(i).getCTL());
                            Log.i("FITNESS" , "ATL"+fitnessList.get(i).getATL());

                            CTLdouble=CTLdouble+Double.parseDouble(fitnessList.get(i).getFitnessPerDay());
                            Log.i("FITNESS" , "CTLdouble"+CTLdouble);

                        }
                        CTLdouble=(CTLdouble+fitnessPerDayDouble)/15;
                        CTLdouble=Math.round(CTLdouble*100.0)/100.0;
                        CTL=Double.toString(CTLdouble);

                        TSBLong=Math.round(CTLdouble-ATLdouble);
                        TSB=Long.toString(TSBLong);
                    }
                }
                else
                {
                    CTL="null";
                    ATL="null";
                    TSB="null";
                }

            }


        }
    }

    private void toastMessage(String message)
    {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}
