package com.example.naray.knowyourgovernment;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by naray on 4/3/2017.
 */

public class RepresentativeAdapter extends RecyclerView.Adapter<RepresentativeViewHolder>{
    private static final String TAG = "RepresentativeAdapter";
    private List<Representative> repList;
    private MainActivity mainAct;

    public RepresentativeAdapter(List<Representative> repsList, MainActivity ma) {
        this.repList = repsList;
        mainAct = ma;
    }

    @Override
    public RepresentativeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: MAKING NEW");
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.gov_row, parent, false);

        itemView.setOnClickListener(mainAct);
        itemView.setOnLongClickListener(mainAct);

        return new RepresentativeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RepresentativeViewHolder holder, int position) {
        //for(int i=0;i<repList.size();i++) {
            Representative representative = repList.get(position);
            holder.rep_desgn.setText(representative.getrepofficesName().toString());
            holder.rep_name.setText(representative.getrepofficialName() + " (" + representative.getrepParty() + ") ");
            holder.rep_name.setTextColor(Color.DKGRAY);

        //}
    }

    @Override
    public int getItemCount() {
        return repList.size();
    }
}
