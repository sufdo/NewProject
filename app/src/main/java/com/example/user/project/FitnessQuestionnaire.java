package com.example.user.project;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class FitnessQuestionnaire extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.fitness_q);
        setContentView(R.layout.activity_fitness_questionnaire);
    }
}
