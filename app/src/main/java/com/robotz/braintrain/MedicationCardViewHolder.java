/*
package com.robotz.braintrain;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.robotz.braintrain.Entity.Medication;

import java.util.List;



public class MedicationCardViewHolder extends RecyclerView.ViewHolder {

    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onDeleteClick(int position);

    }

    public TextView MedicationName;
    public TextView Unit;
    public ImageView DeleteImage;

    public MedicationCardViewHolder(@NonNull View itemView, final OnItemClickListener mListener) {
        super(itemView);
        MedicationName = itemView.findViewById(R.id.MedicationName);
        Unit = itemView.findViewById(R.id.MedicationUnit);
        DeleteImage = itemView.findViewById(R.id.imagedelete);

        DeleteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    int position = getAdapterPosition();
                    System.out.println(position);
                    if(position != RecyclerView.NO_POSITION){
                        mListener.onDeleteClick(position);
                        System.out.println(position);

                    }
                }
            }
        });
    }
}
*/
