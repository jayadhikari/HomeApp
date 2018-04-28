package com.jay_adhikariyahoo.homeapp;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jay on 26/04/18.
 */

public class deviceClassAdapter extends ArrayAdapter<deviceClass> {
    private static final String TAG = "deviceClassAdapter";
    private Context mContext;
    private int mResource;
    private int lastPosition= -1;


    static class ViewHolder
    {
        TextView name;
        TextView status;
        TextView type;
    }

    /*
            Default constructor for the deviceClassAdapter

     */
    public deviceClassAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<deviceClass> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        String name = getItem(position).getName();
        String status = getItem(position).getStatus();
        String type = getItem(position).getType();
        //create device class object with the info

        //deviceClass device = new deviceClass(name,status,type);


        //create the view result for showing the animation
        final View result;
        ViewHolder holder;


        if(convertView == null)
        {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource,parent,false);

            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.layoutTextView1);
            holder.status = (TextView) convertView.findViewById(R.id.layoutTextView2);
            holder.type = (TextView) convertView.findViewById(R.id.layoutTextView3);

            result = convertView;
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
            result = convertView;

        }


        Animation animation = AnimationUtils.loadAnimation(mContext,
                (position > lastPosition)? R.anim.load_down_anim : R.anim.load_up_anim);

        result.startAnimation(animation);
        lastPosition =position;

        holder.name.setText(name);
        holder.status.setText(status);
        holder.type.setText(type);
/*
        TextView tvName = (TextView) convertView.findViewById(R.id.layoutTextView1);
        TextView tvStatus = (TextView) convertView.findViewById(R.id.layoutTextView2);
        TextView tvType = (TextView) convertView.findViewById(R.id.layoutTextView3);

        tvName.setText(name);
        tvStatus.setText(status);
        tvType.setText(type);
*/
        return convertView;
    }
}




