package com.robotz.braintrain;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.Debug;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.robotz.braintrain.Dao.MedicationDao;
import com.robotz.braintrain.Dao.UserDao;
import com.robotz.braintrain.Databse.BrainTrainDatabase;
import com.robotz.braintrain.Entity.Medication;
import com.robotz.braintrain.Entity.User;
import com.robotz.braintrain.ViewModel.MedicationViewModel;
import com.robotz.braintrain.network.MedicationEntry;
import com.robotz.braintrain.network.ProductEntry;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;


public class MedicationFragment extends Fragment {

    public static final String EXTRA_FATHERSNAME ="com.robotz.braintrain.EXTRA_FATHERSNAME";
    public static final String EXTRA_MOTHERSNAME ="com.robotz.braintrain.EXTRA_MOTHERSNAME";
    public static final String EXTRA_DOB ="com.robotz.braintrain.EXTRA_DOB";
    public static final String EXTRA_DIAGNOSIS ="com.robotz.braintrain.EXTRA_DIAGNOSIS";
    public MedicationDao medicationDao;
    private UserDao userDao;
    private BrainTrainDatabase connDB;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    SharedPreferences sharedPreferences;
    public String currentUser, currentMedication;
    private MedicationCardRecyclerViewAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_medication, container, false);

        sharedPreferences = getContext().getSharedPreferences("app", MODE_PRIVATE);
        currentUser = sharedPreferences.getString("currentUser", "");

        ((NavigationHost) getActivity()).setUpToolbar(view);
        connDB = Room.databaseBuilder(getContext(), BrainTrainDatabase.class, connDB.DBNAME).allowMainThreadQueries().build();
        medicationDao = connDB.medicationDao();
        userDao = connDB.userDao();
        List<Medication> medications = medicationDao.getAllMedications();
        List<User> users = userDao.getAllUsers();
        if(medications.size() <= 0){
            TextView nomed = view.findViewById(R.id.no_medication);
            nomed.setText("No medication saved");
        }

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_medicationName);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1, GridLayoutManager.VERTICAL, false));
//        MedicationCardRecyclerViewAdapter adapter = new MedicationCardRecyclerViewAdapter(MedicationEntry.initMedicationEntryList(getResources()));

        mAdapter = new MedicationCardRecyclerViewAdapter(medications);
        recyclerView.setAdapter(mAdapter);


        //edit medication//

        mAdapter.setOnItemClickListener(new MedicationCardRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                AddMedicationFragment amf = new AddMedicationFragment();
                Bundle args = new Bundle();
               currentMedication = String.valueOf(position);
                System.out.println(currentMedication + "current position");
                args.putString("currentMedication", currentMedication);
                args.putString("edit", "true");
                amf.setArguments(args);
                ((NavigationHost) getActivity()).navigateTo(amf, "Edit Medication", false);

            }
        });


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT| ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                Medication position = ((MedicationCardRecyclerViewAdapter) mAdapter).getMedAt(viewHolder.getAdapterPosition());
//                Toast.makeText(getContext(), "position"+position, Toast.LENGTH_LONG).show();
                System.out.println(position.getId());
                medicationDao.updateDelete(true, position.getId());
//                medicationDao.delete(((MedicationCardRecyclerViewAdapter) adapter).getMedAt(viewHolder.getAdapterPosition()));

                //set isDeleted to true
                DatabaseReference medicationRef = database.getReference("users").child(currentUser).child("medication").child(Integer.toString(position.getId())).child("isDeleted");
                Map<String, String> medicationMap = new HashMap<>();
                medicationRef.setValue("true");
//                medicationMap.put("isDeleted", "true");
//                medicationRef.push().setValue(medicationMap);

                mAdapter.notifyDataSetChanged();
                ((NavigationHost) getActivity()).navigateTo(new MedicationFragment(), "Add Medication", true);

                Toast.makeText(getContext(), "Medication Deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);


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