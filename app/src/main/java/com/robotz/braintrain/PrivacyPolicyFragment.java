package com.robotz.braintrain;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;


public class PrivacyPolicyFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_privacy_policy, container, false);
        TextView text = view.findViewById(R.id.privacypolicyText);
        text.setText("This software is developed as a part of Master’s thesis by Roji Maharjan.\n" +
                "1.\tData collected to provide basic functionality\n" +
                "We ask for your consent when you start using BrainTrain because BrainTrain’s basic functionality relies on processing your health data. You can no longer use this application if you withdraw this consent.\n" +
                "a.\tRegistering user account\n" +
                "To use BrainTrain application, you need user account entering personal data. Your personal data are only used to create a username at the initial phase. This generated username is used to anonymize users. To create an account, you must enter your father’s first name, mother’s maiden name and year of birth.\n" +
                "b.\tSupport your treatment\n" +
                "You can use BrainTrain to manage your treatment by entering treatment schedules for medication and diagnosis.\n" +
                "c.\tPlaying Game\n" +
                "Game scores are collected for survey and studies. Game scores are also compared with previously available scores to give feedback to the current situation.\n" +
                "2.\tStoring your data\n" +
                "BrainTrain initially stores data on your smartphone. When your smartphone connects to the internet, BrainTrain transmits your data to centralized database. We keep your personal data for no longer than is necessary for the purpose for which your personal data is processed. If you delete your account, we delete any personal data contained in this account. This cannot be reversed.\n" +
                "3.\tYour data protection rights\n" +
                "You can request information on data stored about you and have the right to receive your data in a common machine-readable format. You may also request the deletion, restriction of processing of your data.\n" +
                "4.\tWithdrawing your consent and contact\n" +
                "You are free to withdraw any consent you have given at any time. In case of question of to exercise your rights, please contact to us at maharj01@gw.uni-passau.de\n" +
                "5.\tAmendments\n" +
                "We reserve the right to amend this Privacy Policy. \n" +
                "Policy was last changed on 10.26.2020\n");
        text.setMovementMethod(new ScrollingMovementMethod());
        ((NavigationHost) getActivity()).setUpBackBtn(view);
        return view;

    }
}