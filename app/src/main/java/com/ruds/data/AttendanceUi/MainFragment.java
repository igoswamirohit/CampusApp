package com.ruds.data.AttendanceUi;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavBackStackEntry;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ruds.data.R;
import com.ruds.data.models.Students;
import com.ruds.data.viewholder.StudentsViewHolder;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class MainFragment extends Fragment implements DatePickerDialog.OnDateSetListener {

    public NavController navController;
    private Button next_btn, dateBtn;
    private Spinner dep, sem, lec;
    public String semester, department, lecture, ajkidate;
    public Date dateD;
    public Long dateLong;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser mUser = mAuth.getCurrentUser();

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        navController = Navigation.findNavController(view);
    }

    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(Objects.requireNonNull(getContext()), this, calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        int m = month + 1;
        ajkidate = dayOfMonth + "-" + m + "-" + year;
        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        dateD = null;
        try {
            dateD = (Date) formatter.parse(ajkidate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        dateLong = dateD.getTime();
        //Snackbar.make(getView(),date.getTime(),5000).show();
        Toast.makeText(getContext(), Long.toString(dateLong), Toast.LENGTH_SHORT).show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        next_btn = view.findViewById(R.id.next_btn);
        dep = view.findViewById(R.id.spinner);
        sem = view.findViewById(R.id.spinner2);
        lec = view.findViewById(R.id.spinner3);
        dateBtn = view.findViewById(R.id.date);

        ArrayAdapter<CharSequence> semAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.Semester, android.R.layout.simple_spinner_item);
        semAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<CharSequence> lecAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.Lecture, android.R.layout.simple_spinner_item);
        lecAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<CharSequence> depAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.Department, android.R.layout.simple_spinner_item);
        depAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sem.setAdapter(semAdapter);
        dep.setAdapter(depAdapter);
        lec.setAdapter(lecAdapter);

        sem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                semester = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        dep.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                department = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        lec.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                lecture = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        dateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putString("Department", department);
                args.putString("Semester", semester);
                args.putString("Lecture", lecture);
                args.putLong("Date", dateLong);
                //args.putString("Date", ajkidate);
                navController.navigate(R.id.action_mainFragment_to_attendanceListFragment, args);
            }
        });
        return view;
    }
}