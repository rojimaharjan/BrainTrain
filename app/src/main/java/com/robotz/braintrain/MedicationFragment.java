package com.robotz.braintrain;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.button.MaterialButton;
import com.robotz.braintrain.Dao.MedicationDao;
import com.robotz.braintrain.Dao.UserDao;
import com.robotz.braintrain.Databse.BrainTrainDatabase;
import com.robotz.braintrain.Entity.Medication;
import com.robotz.braintrain.Entity.User;
import com.robotz.braintrain.ViewModel.MedicationViewModel;
import com.robotz.braintrain.network.MedicationEntry;
import com.robotz.braintrain.network.ProductEntry;

import java.util.List;


public class MedicationFragment extends Fragment {

    public static final String EXTRA_FATHERSNAME ="com.robotz.braintrain.EXTRA_FATHERSNAME";
    public static final String EXTRA_MOTHERSNAME ="com.robotz.braintrain.EXTRA_MOTHERSNAME";
    public static final String EXTRA_DOB ="com.robotz.braintrain.EXTRA_DOB";
    public static final String EXTRA_DIAGNOSIS ="com.robotz.braintrain.EXTRA_DIAGNOSIS";
    public MedicationDao medicationDao;
    private UserDao userDao;
    private BrainTrainDatabase connDB;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_medication, container, false);

        ((NavigationHost) getActivity()).setUpToolbar(view);
        connDB = Room.databaseBuilder(getContext(), BrainTrainDatabase.class, connDB.DBNAME).allowMainThreadQueries().build();
        medicationDao = connDB.medicationDao();
        userDao = connDB.userDao();
        List<Medication> medications = medicationDao.getAllMedications();
        List<User> users = userDao.getAllUsers();

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_medicationName);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1, GridLayoutManager.VERTICAL, false));
//        MedicationCardRecyclerViewAdapter adapter = new MedicationCardRecyclerViewAdapter(MedicationEntry.initMedicationEntryList(getResources()));

        RecyclerView.Adapter adapter = new MedicationCardRecyclerViewAdapter(medications);
        recyclerView.setAdapter(adapter);
//        adapter.setMedications(medications);
      /*  medicationViewModel = new ViewModelProvider(getActivity()).get(MedicationViewModel.class);
        if (medicationViewModel != null) {
            medicationViewModel.getAllMedications().observe(getActivity(), new Observer<List<Medication>>() {
                @Override
                public void onChanged(List<Medication> medications) {
                    adapter.setMedications(medications);
                }
            });
        }
        else
        {

        }*/

        int largePadding = getResources().getDimensionPixelSize(R.dimen.shr_product_grid_spacing);
        int smallPadding = getResources().getDimensionPixelSize(R.dimen.shr_product_grid_spacing_small);
        recyclerView.addItemDecoration(new ProductGridItemDecoration(largePadding, smallPadding));

        MaterialButton addMedication = view.findViewById(R.id.AddMedication);
        addMedication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((NavigationHost) getActivity()).navigateTo(new MedicationNameFragment(), "Add Medication", true);
            }
        });
        return view;


    }
}