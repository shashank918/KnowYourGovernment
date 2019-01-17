package com.example.naray.knowyourgovernment;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.List;

/**
 * Created by naray on 4/3/2017.
 */

public class RepresentativeViewHolder extends RecyclerView.ViewHolder {

    public TextView rep_name;
    public TextView rep_desgn;


    public RepresentativeViewHolder(View view) {
        super(view);
        rep_name = (TextView) view.findViewById(R.id.name);
        rep_desgn = (TextView) view.findViewById(R.id.desgn);
    }
}
