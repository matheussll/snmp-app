package com.matheus.gerenciaapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.matheus.gerenciaapp.R;

import java.util.ArrayList;
import java.util.List;

public class ConsoleAdapter extends RecyclerView.Adapter<ConsoleAdapter.ViewHolder>  {
    private List<String> mResponses;
//    private final Context context;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView commandResponse;

        public ViewHolder(View itemView) {
            super(itemView);

            commandResponse = itemView.findViewById(R.id.commandResponse);
        }
    }

    @Override
    public ConsoleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.console_command, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    public ConsoleAdapter(List<String> responses) {
        mResponses = responses;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(ConsoleAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        String response = mResponses.get(position);

        // Set item views based on your views and data model
        TextView tvCommandResponse = viewHolder.commandResponse;
        tvCommandResponse.setText(response);

    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mResponses.size();
    }

}
