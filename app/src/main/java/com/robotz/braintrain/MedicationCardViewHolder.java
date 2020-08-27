package com.robotz.braintrain;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;
public class MedicationCardViewHolder extends RecyclerView.ViewHolder {

public TextView MedicationName;
public TextView Unit;

    public MedicationCardViewHolder(@NonNull View itemView) {
        super(itemView);
        MedicationName = itemView.findViewById(R.id.MedicationName);
        Unit = itemView.findViewById(R.id.MedicationUnit);
    }
}

