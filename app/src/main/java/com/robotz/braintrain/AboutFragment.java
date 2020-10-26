package com.robotz.braintrain;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class AboutFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_about, container, false);
        TextView text = view.findViewById(R.id.about);
        text.setText("Developed By\n" +
                "Roji Maharjan\n" +
                "as a part of Master’s thesis\n" +
                "Universität Passau\n" +
                "Email: maharj01@gw.uni-passau.de\n");
        ((NavigationHost) getActivity()).setUpBackBtn(view);
        return view;
    }
}