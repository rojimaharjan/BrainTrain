package com.robotz.braintrain;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.robotz.braintrain.Entity.Medication;

public class MedicationNameFragment extends Fragment {

/*    private nameTransferListener Listener;

    public interface nameTransferListener{
        void medName(String medName);
    }*/
    TextInputEditText MedicationName;
    TextInputLayout medicationLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_medication_name, container, false);
        MedicationName = view.findViewById(R.id.MedicationNameTxt);
        medicationLayout = view.findViewById(R.id.MedicationName_text_input);
        MaterialButton continueBtn = view.findViewById(R.id.continue_button);

        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(MedicationName.toString() != null) {
//                    MedicationName = view.findViewById(R.id.MedicationNameTxt);
                    AddMedicationFragment amf = new AddMedicationFragment();
                    Bundle args = new Bundle();
                    args.putString("MedName", MedicationName.getText().toString());
                    amf.setArguments(args);
//                    Listener.medName(MedicationName.toString());
                    ((NavigationHost) getActivity()).navigateTo(amf, "Add Medication", false);
                }else{
                    medicationLayout.setError(getString(R.string.error_medicationname));

                }
            }
        });

        return view;
    }

   /* @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof nameTransferListener){
            Listener = (nameTransferListener) context;
        }else {
            throw  new RuntimeException(context.toString()
                    +"must implement FragmentListner");
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        Listener = null;
    }*/

}