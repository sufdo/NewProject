package com.example.user.project;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class TrainerInterface extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer_interface);

        SharedPreferences preferences = getApplicationContext().getSharedPreferences("TrainerPref",0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("TrainerID","001");
        editor.commit();
    }

    public void fitness_clicked(View view)
    {
        if(view.getId()==R.id.fitness_btn)
        {
            Intent intent=new Intent(this,FitnessQuestionnaire.class);
            startActivity(intent);
        }
    }

    public void tsb_clicked(View view)
    {
        if(view.getId()==R.id.tsb_btn)
        {
            Intent intent=new Intent(this,TSB.class);
            startActivity(intent);
        }
    }
}
