package com.example.user.project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class FitnessQuestionnaire extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.fitness_q);
        setContentView(R.layout.activity_fitness_questionnaire);
    }

    public void fitnessSubmit_clicked(View view)
    {
        if(view.getId()==R.id.fitnessSubmit)
        {
            Intent intent=new Intent(this,TSB.class);
            startActivity(intent);
        }
    }
}
