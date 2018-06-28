package com.example.user.project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class TrainerLogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer_login);
    }

    public void login_clicked(View view)
    {
        if(view.getId()==R.id.login_btn)
        {
            Intent intent=new Intent(this,TrainerInterface.class);
            startActivity(intent);
        }
    }
}
