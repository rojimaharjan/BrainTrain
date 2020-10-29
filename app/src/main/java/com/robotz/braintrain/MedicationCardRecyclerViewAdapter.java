package com.robotz.braintrain;

import android.icu.text.Transliterator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.robotz.braintrain.Entity.Medication;
import com.robotz.braintrain.network.ImageRequester;
import com.robotz.braintrain.network.MedicationEntry;
import com.robotz.braintrain.network.ProductEntry;

import java.text.FieldPosition;
import java.util.ArrayList;
import java.util.List;


public class MedicationCardRecyclerViewAdapter extends RecyclerView.Adapter<MedicationCardRecyclerViewAdapter.MedicationCardViewHolder> {


    private List<Medication> medications;
    private OnItemClickListener editlistener;

    public MedicationCardRecyclerViewAdapter(List<Medication> medications) {
        this.medications = medications;
    }

/*    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onDeleteClick(int position);

    }*/

   /* public MedicationCardRecyclerViewAdapter() {
        this.medications = medications;
    }*/

    @NonNull
    @Override
    public MedicationCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.medication_card, parent, false);
        return new MedicationCardViewHolder(layoutView, editlistener, medications);
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

/*    public void setMedications(List<Medication> medications){
        this.medications = medications;
        notifyDataSetChanged();
    }*/

    public Medication getMedAt(int position){
        return medications.get(position);
    }
    @Override
    public int getItemCount() {
        /*int no = medicationList.size();
        if(no < 0){
            return 0;
        }else{

            return no;
        }*/
        return medications.size();
    }

    public static class MedicationCardViewHolder extends RecyclerView.ViewHolder {

        public TextView MedicationName;
        public TextView Unit;
        public ImageView DeleteImage;

        public MedicationCardViewHolder(@NonNull View itemView, final OnItemClickListener listener, List<Medication> medicationList) {
            super(itemView);
            MedicationName = itemView.findViewById(R.id.MedicationName);
            Unit = itemView.findViewById(R.id.MedicationUnit);
            DeleteImage = itemView.findViewById(R.id.imagedelete);

            MedicationName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.out.println("clicked"+ view.getVerticalScrollbarPosition() + " position "+ getPosition());
                    if (listener != null) {
                        int position = getAdapterPosition();
                        Medication medication = medicationList.get(position);
                        int medPosition = medication.getId();
                        System.out.println(position);
                        if(medPosition != RecyclerView.NO_POSITION){
                            listener.onItemClick(medPosition);
                            System.out.println(medPosition);

                        }
                    }
                }
            });
        }
    }


    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.editlistener = listener;
    }
}
