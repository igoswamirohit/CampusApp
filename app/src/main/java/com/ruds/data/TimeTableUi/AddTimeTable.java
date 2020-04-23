package com.ruds.data.TimeTableUi;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ruds.data.R;
import com.ruds.data.models.AddTimeTableViewModel;

import java.util.HashMap;
import java.util.Map;

public class AddTimeTable extends Fragment implements View.OnClickListener {

    private AddTimeTableViewModel mViewModel;
    Button mondayBtn, tuesdayBtn, wednesdayBtn, thursdayBtn, fridayBtn, saturdayBtn;
    Map<String, String> dataMap = new HashMap<>();

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference();

    public static AddTimeTable newInstance() {
        return new AddTimeTable();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(AddTimeTableViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_time_table_fragment, container, false);
        mondayBtn = view.findViewById(R.id.mondayBtn);
        mondayBtn.setOnClickListener(this);
        tuesdayBtn = view.findViewById(R.id.tuesdayBtn);
        tuesdayBtn.setOnClickListener(this);
        wednesdayBtn = view.findViewById(R.id.wednesdayBtn);
        wednesdayBtn.setOnClickListener(this);
        thursdayBtn = view.findViewById(R.id.thursdayBtn);
        thursdayBtn.setOnClickListener(this);
        fridayBtn = view.findViewById(R.id.fridayBtn);
        fridayBtn.setOnClickListener(this);
        saturdayBtn = view.findViewById(R.id.saturdayBtn);
        saturdayBtn.setOnClickListener(this);


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(AddTimeTableViewModel.class);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mondayBtn:
                openSubjectsDialog("monday");
                break;
            case R.id.tuesdayBtn:
                openSubjectsDialog("tuesday");
                break;
            case R.id.wednesdayBtn:
                openSubjectsDialog("wednesday");
                break;
            case R.id.thursdayBtn:
                openSubjectsDialog("thursday");
                break;
            case R.id.fridayBtn:
                openSubjectsDialog("friday");
                break;
            case R.id.saturdayBtn:
                openSubjectsDialog("saturday");
                break;
        }
    }

    private void openSubjectsDialog(final String day) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        final View customView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_custom_layout, null);
        Button dialogConfirmBtn = customView.findViewById(R.id.dialogConfirmButton);
        final EditText lecture1ET = customView.findViewById(R.id.lecture1ET);
        final EditText lecture2ET = customView.findViewById(R.id.lecture2ET);
        final EditText lecture3ET = customView.findViewById(R.id.lecture3ET);
        final EditText lecture4ET = customView.findViewById(R.id.lecture4ET);
        final EditText lecture5ET = customView.findViewById(R.id.lecture5ET);
        final EditText lecture6ET = customView.findViewById(R.id.lecture6ET);
        builder.setView(customView);
        final AlertDialog customDialog = builder.create();
        customDialog.show();
        dialogConfirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lecture1ET.getText().toString().isEmpty() || lecture2ET.getText().toString().isEmpty() || lecture3ET.getText().toString().isEmpty() ||
                        lecture4ET.getText().toString().isEmpty() || lecture5ET.getText().toString().isEmpty() || lecture6ET.getText().toString().isEmpty()) {
                    Snackbar.make(customView, "Please Enter Name of all Lectures.", 4000).show();
                } else {
                    dataMap.put("Lecture_1", lecture1ET.getText().toString());
                    dataMap.put("Lecture_2", lecture2ET.getText().toString());
                    dataMap.put("Lecture_3", lecture3ET.getText().toString());
                    dataMap.put("Lecture_4", lecture4ET.getText().toString());
                    dataMap.put("Lecture_5", lecture5ET.getText().toString());
                    dataMap.put("Lecture_6", lecture6ET.getText().toString());

                    databaseReference.child("TimeTable").child(day).setValue(dataMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Snackbar.make(getView(), "Successful", 5000).show();
                            customDialog.cancel();
                        }
                    });
                }
            }
        });
    }
}