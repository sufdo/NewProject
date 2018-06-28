package com.example.user.project;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

public class PlayerRecommend extends AppCompatActivity {

    ListView mylist;
    String[] playerName;
    String[] eligibility;
    Integer[] playerimage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_recommend);

        mylist = (ListView) findViewById(R.id.listveiw);

        CustomListViewAdapter customListViewAdapter=new CustomListViewAdapter(this,playerName,eligibility,playerimage);
        mylist.setAdapter(customListViewAdapter);
    }



}

