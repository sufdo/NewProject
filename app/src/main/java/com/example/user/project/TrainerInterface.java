package com.example.user.project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class TrainerInterface extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer_interface);
    }

    public void fitness_clicked(View view)
    {
        if(view.getId()==R.id.fitness_btn)
        {
            Intent intent=new Intent(this,FitnessQuestionnaire.class);
            startActivity(intent);
        }
    }
}
