package com.robotz.braintrain;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class DaysFragment extends DialogFragment {
    int position = 12;

    public interface  SingleChoiceListener{
        void onPositiveButtonClicked(String[] list, int position);
        void onNegativeButtonClicked();

    }


    UnitDialog.SingleChoiceListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mListener = (UnitDialog.SingleChoiceListener) getTargetFragment();
        } catch (Exception e) {
            throw new ClassCastException(getActivity().toString() +"  * singlechoiceListener must implemented *"+ e.toString());
        }

    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final String[] list = getActivity().getResources().getStringArray(R.array.unit);

        builder.setTitle("Unit")
                .setSingleChoiceItems(list, position, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        position = i;

                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mListener.onPositiveButtonClicked(list, position);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mListener.onNegativeButtonClicked();
                    }
                });
        return builder.create();

    }
}