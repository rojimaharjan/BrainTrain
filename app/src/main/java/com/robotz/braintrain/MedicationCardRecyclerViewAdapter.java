package com.robotz.braintrain;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.robotz.braintrain.Entity.Medication;
import com.robotz.braintrain.network.ImageRequester;
import com.robotz.braintrain.network.MedicationEntry;
import com.robotz.braintrain.network.ProductEntry;

import java.util.ArrayList;
import java.util.List;


public class MedicationCardRecyclerViewAdapter extends RecyclerView.Adapter<MedicationCardViewHolder> {

    private List<Medication> medications = new ArrayList<>();

 /*   public MedicationCardRecyclerViewAdapter(List<Medication> medicationList) {
        this.medicationList = medicationList;
    }*/

    public MedicationCardRecyclerViewAdapter() {
        this.medications = medications;
    }

    @NonNull
    @Override
    public MedicationCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.medication_card, parent, false);
        return new MedicationCardViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull MedicationCardViewHolder holder, int position) {
        Medication medication = medications.get(position);
        holder.MedicationName.setText(medication.getMed_name());
        holder.Unit.setText(medication.getType());
    /*    if (medications != null && position < medications.size()) {
            holder.MedicationName.setText(medication.getMed_name());
            holder.Unit.setText(medication.getType());
        }*/

    }

    public void setMedications(List<Medication> medications){
        this.medications = medications;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return medications.size();
    }
}
