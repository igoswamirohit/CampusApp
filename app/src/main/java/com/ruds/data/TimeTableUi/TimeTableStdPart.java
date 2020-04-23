package com.ruds.data.TimeTableUi;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ruds.data.R;
import com.ruds.data.models.AddTimeTableViewModel;

import java.util.Map;

public class TimeTableStdPart extends Fragment implements View.OnClickListener {

    Button stdMondayBtn, stdTuesdayBtn, stdWednesdayBtn, stdThursdayBtn, stdFridayBtn, stdSaturdayBtn;
    AddTimeTableViewModel addTimeTableViewModel;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference("TimeTable");

    public TimeTableStdPart() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
        addTimeTableViewModel = new ViewModelProvider(this).get(AddTimeTableViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_time_table_std_part, container, false);
        stdMondayBtn = view.findViewById(R.id.stdMondayBtn);
        stdMondayBtn.setOnClickListener(this);
        stdTuesdayBtn = view.findViewById(R.id.stdTuesdayBtn);
        stdTuesdayBtn.setOnClickListener(this);
        stdWednesdayBtn = view.findViewById(R.id.stdWednesdayBtn);
        stdWednesdayBtn.setOnClickListener(this);
        stdThursdayBtn = view.findViewById(R.id.stdThursdayBtn);
        stdThursdayBtn.setOnClickListener(this);
        stdFridayBtn = view.findViewById(R.id.stdFridayBtn);
        stdFridayBtn.setOnClickListener(this);
        stdSaturdayBtn = view.findViewById(R.id.stdSaturdayBtn);
        stdSaturdayBtn.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.stdMondayBtn:
                openSubjectsDialog("monday");
                break;
            case R.id.stdTuesdayBtn:
                openSubjectsDialog("tuesday");
                break;
            case R.id.stdWednesdayBtn:
                openSubjectsDialog("wednesday");
                break;
            case R.id.stdThursdayBtn:
                openSubjectsDialog("thursday");
                break;
            case R.id.stdFridayBtn:
                openSubjectsDialog("friday");
                break;
            case R.id.stdSaturdayBtn:
                openSubjectsDialog("saturday");
                break;
        }
    }

    private void openSubjectsDialog(final String day) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        final View customView = LayoutInflater.from(getContext()).inflate(R.layout.timetable_std_custom_layout, null);
        Button dialogCloseBtn = customView.findViewById(R.id.ttStdCloseBtn);
        builder.setView(customView);
        final TextView lecture1TV = customView.findViewById(R.id.lecture1TV);
        final TextView lecture2TV = customView.findViewById(R.id.lecture2TV);
        final TextView lecture3TV = customView.findViewById(R.id.lecture3TV);
        final TextView lecture4TV = customView.findViewById(R.id.lecture4TV);
        final TextView lecture5TV = customView.findViewById(R.id.lecture5TV);
        final TextView lecture6TV = customView.findViewById(R.id.lecture6TV);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    try {
                        addTimeTableViewModel = dataSnapshot.child(day).getValue(AddTimeTableViewModel.class);
                        lecture1TV.setText(addTimeTableViewModel.getLecture_1());
                        lecture2TV.setText(addTimeTableViewModel.getLecture_2());
                        lecture3TV.setText(addTimeTableViewModel.getLecture_3());
                        lecture4TV.setText(addTimeTableViewModel.getLecture_4());
                        lecture5TV.setText(addTimeTableViewModel.getLecture_5());
                        lecture6TV.setText(addTimeTableViewModel.getLecture_6());
                    } catch (Exception e) {
                        Log.d("Exception", e.getMessage());
                        if (e.getMessage().contains("java.lang.String com.ruds.data.models.AddTimeTableViewModel")) {
                            Snackbar.make(customView, "Please Contact Any Authorized Person", 5000).show();
                        }
                    }
                } else {
                    Snackbar.make(customView, "Please Contact Any Authorized Person", 5000).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        final AlertDialog customDialog = builder.create();
        customDialog.show();
        dialogCloseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog.cancel();
            }
        });
    }
}