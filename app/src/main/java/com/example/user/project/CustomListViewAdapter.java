package com.example.user.project;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomListViewAdapter extends ArrayAdapter<String>{

    private String[] playerName;
    private String[] eligibility;
    private Integer[] playerimage;
    private Activity context;


    public CustomListViewAdapter(Activity context, String[] playerName,String[] eligibility, Integer[] playerimage) {
        super(context, R.layout.listveiw_layout, playerName);

        this.context=context;
        this.playerName=playerName;
        this.eligibility=eligibility;
        this.playerimage=playerimage;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View r=convertView;
        ViewHolder viewholder= null;

        if(r==null){

            LayoutInflater layoutInflater=context.getLayoutInflater();
            r=layoutInflater.inflate(R.layout.listveiw_layout,null,true);
            viewholder=new ViewHolder(r);
            r.setTag(viewholder);
        }

        else {

            viewholder=(ViewHolder) r.getTag();
        }

        viewholder.player.setImageResource(playerimage[position]);
        viewholder.name.setText(playerName[position]);
        viewholder.eligible.setText(eligibility[position]);

        return r;
    }

    class ViewHolder{

        TextView name;
        TextView eligible;
        ImageView player;
        Button more;
        ViewHolder( View v){

            name=(TextView) v.findViewById(R.id.playername);
            eligible=(TextView) v.findViewById(R.id.eligibility);
            player=(ImageView) v.findViewById(R.id.playerimage);



        }

    }
}
