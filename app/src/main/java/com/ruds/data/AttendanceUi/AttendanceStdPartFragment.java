package com.ruds.data.AttendanceUi;

import android.app.DatePickerDialog;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.ruds.data.R;
import com.ruds.data.models.AttendanceStd;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class AttendanceStdPartFragment extends Fragment implements DatePickerDialog.OnDateSetListener {

    public NavController navController;
    int countArray = 0;
    Button btn, startDateBtn, endDateBtn;
    TextView attendancePercentageTv, totalLecturesTV, lecturesAttendedTV, safeOrNotTV;
    LinearLayout percentageLayout;
    int studentsPrecent;
    ProgressBar progressAttendance;
    public Date dateD;
    public Long startDateLong, endADateLong;
    String whatItIs, fullName, startDate, endDate;
    AttendanceStd attendanceStd;
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    public AttendanceStdPartFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            fullName = getArguments().getString("fullName");
        }
        attendanceStd = new ViewModelProvider(this).get(AttendanceStd.class);
        setRetainInstance(true);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showList();
            }
        });

        startDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                whatItIs = "startDate";
                showDatePickerDialog();

            }
        });

        endDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                whatItIs = "endDate";
                showDatePickerDialog();
            }
        });
    }

    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(Objects.requireNonNull(getContext()), this, calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_attendance_std_part, container, false);
        /*if(savedInstanceState!=null){
            String attPer = savedInstanceState.getString("attPercentage");
            attendancePercentageTv.setVisibility(View.VISIBLE);
            attendancePercentageTv.setText(attPer + "%");
        }*/
        btn = view.findViewById(R.id.btn);
        attendancePercentageTv = view.findViewById(R.id.attendancePercentageTV);
        startDateBtn = view.findViewById(R.id.startDatePicker);
        endDateBtn = view.findViewById(R.id.endDatePicker);
        lecturesAttendedTV = view.findViewById(R.id.lecturesAttendedTV);
        totalLecturesTV = view.findViewById(R.id.totalLecturesTV);
        safeOrNotTV = view.findViewById(R.id.safeOrNotTV);
        percentageLayout = view.findViewById(R.id.percentageLayout);
        progressAttendance = view.findViewById(R.id.progressAttendance);

        return view;
    }

    private void showList() {
        progressAttendance.setVisibility(View.VISIBLE);
        countArray = 0;
        Query query = database.getReference("Attendance/CSE/Sem1").orderByKey().startAt(attendanceStd.getStartDate()).endAt(attendanceStd.getEndDate());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Log.d("TAG", "Data exists within dates");
                    try {
                        countArray = 0;
                        Map<String, Integer> tot_record = new HashMap<>();
                        int tot = 0, tot1 = 0, tot2 = 0, tot3 = 0, tot4 = 0, tot5 = 0, tot6 = 0;
                        int att = 0, att1 = 0, att2 = 0, att3 = 0, att4 = 0, att5 = 0, att6 = 0;
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            for (DataSnapshot lectures : ds.getChildren()) {
                                switch (lectures.getKey()) {
                                    case "Lecture1":
                                        tot1 += 1;
                                        tot_record.put(lectures.getKey(), tot1);
                                        break;
                                    case "Lecture2":
                                        tot2 += 1;
                                        tot_record.put(lectures.getKey(), tot2);
                                        break;
                                    case "Lecture3":
                                        tot3 += 1;
                                        tot_record.put(lectures.getKey(), tot3);
                                        break;
                                    case "Lecture4":
                                        tot4 += 1;
                                        tot_record.put(lectures.getKey(), tot4);
                                        break;
                                    case "Lecture5":
                                        tot5 += 1;
                                        tot_record.put(lectures.getKey(), tot5);
                                        break;
                                    case "Lecture6":
                                        tot6 += 1;
                                        tot_record.put(lectures.getKey(), tot6);
                                        break;
                                    default:
                                        return;
                                }
                                if (lectures.child(fullName).getValue() != null) {
                                    switch (lectures.getKey()) {
                                        case "Lecture1":
                                            att1 += 1;
                                            break;
                                        case "Lecture2":
                                            att2 += 1;
                                            break;
                                        case "Lecture3":
                                            att3 += 1;
                                            break;
                                        case "Lecture4":
                                            att4 += 1;
                                            break;
                                        case "Lecture5":
                                            att5 += 1;
                                            break;
                                        case "Lecture6":
                                            att6 += 1;
                                            break;
                                        default:
                                            return;
                                    }
                                }
                                tot = tot1 + tot2 + tot3 + tot4 + tot5 + tot6;
                                att = att1 + att2 + att3 + att4 + att5 + att6;
                                Log.d("total lectures", Integer.toString(tot));
                                Log.d("total Attended Lectures", Integer.toString(att));
                            }
                        }
                        studentsPrecent = att;
                        totalLecturesTV.setText(Integer.toString(tot));
                        lecturesAttendedTV.setText(Integer.toString(studentsPrecent));
                        totalLecturesTV.setVisibility(View.VISIBLE);
                        lecturesAttendedTV.setVisibility(View.VISIBLE);
                        double percent = ((double) studentsPrecent / (double) tot) * 100;
                        double attendancePercentage = Math.round(percent * 100.0) / 100.0;
                        if (attendancePercentage < 75.00) {
                            safeOrNotTV.setText("Please make your Attendace upto 75% or more.");
                            safeOrNotTV.setTextColor(Color.RED);
                        }
                        attendanceStd.setAttendancePercentage(Double.toString(attendancePercentage));
                        attendancePercentageTv.setText(attendanceStd.getAttendancePercentage() + "%");
                        progressAttendance.setVisibility(View.GONE);
                        percentageLayout.setVisibility(View.VISIBLE);
                        Log.d("Percentage dekh", Double.toString(attendancePercentage));
                    } catch (Exception e) {
                        Log.d("Exception", e.getMessage());
                        if (e.getMessage().equals("Attempt to invoke interface method 'java.util.Set java.util.Map.keySet()' on a null object reference")) {
                            final Snackbar snackbar = Snackbar.make(getView(), "Please wait for the end of the day, if still problem occurs, run out to respected Authority.", 7000);
                            snackbar.setAction("Close", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    snackbar.dismiss();
                                }
                            }).show();
                        }
                    }
                    studentsPrecent = 0;
                } else {
                    Log.d("TAG", "Data does not exist within dates");
                }
                studentsPrecent = 0;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        btn.setEnabled(false);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        int m = month + 1;
        String dateSelected = dayOfMonth + "-" + m + "-" + year;
        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        dateD = null;
        try {
            dateD = (Date) formatter.parse(dateSelected);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (whatItIs == "endDate") {
            //endADateLong = dateD.getTime();
            attendanceStd.setEndDate(Long.toString(dateD.getTime()));
            Toast.makeText(getActivity(), attendanceStd.getEndDate(), Toast.LENGTH_SHORT).show();
        } else if (whatItIs == "startDate") {
            //startDateLong = dateD.getTime();
            attendanceStd.setStartDate(Long.toString(dateD.getTime()));
            Toast.makeText(getActivity(), attendanceStd.getStartDate(), Toast.LENGTH_SHORT).show();
        }
    }
}