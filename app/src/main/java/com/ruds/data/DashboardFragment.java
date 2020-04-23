package com.ruds.data;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class DashboardFragment extends Fragment {

    String fullName, studentOrFaculty;
    public NavController navController;
    TextView dashboardWelcomeTV;
    Button dashboardAttendanceBtn, dashboardLogoutBtn, dashboardUploadCircularsBtn, dashboardDownloadCircularsBtn;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser mUser = mAuth.getCurrentUser();
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    public DashboardFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        dashboardAttendanceBtn = view.findViewById(R.id.dashboardAttendanceBtn);
        dashboardUploadCircularsBtn = view.findViewById(R.id.dashboardUploadCircularsBtn);
        dashboardLogoutBtn = view.findViewById(R.id.dashboardLogoutBtn);
        dashboardWelcomeTV = view.findViewById(R.id.dashboardWelcomeTV);
        dashboardLogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                getActivity().onBackPressed();
            }
        });
        dashboardAttendanceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putString("fullName", fullName);
                //navController.navigate(R.id.action_dashboardFragment_to_attendanceStdPartFragment3,args);
                navController.navigate(R.id.action_dashboardFragment_to_mainFragment);
            }
        });
        dashboardUploadCircularsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_dashboardFragment_to_uploadCirculars);
            }
        });
        view.findViewById(R.id.dashboardDownloadCircularsBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_dashboardFragment_to_downloadCirculars);
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        getNameFromDatabase();
    }

    public void getNameFromDatabase() {
        Query query = database.getReference("Students");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    for (DataSnapshot departments : dataSnapshot.getChildren()) {
                        for (DataSnapshot semesters : departments.getChildren()) {
                            for (DataSnapshot enrolls : semesters.getChildren()) {
                                if (enrolls.child("uid").getValue().toString().equals(mUser.getUid())) {
                                    fullName = enrolls.child("name").getValue().toString();
                                    Log.d("data aaya", enrolls.child("name").getValue().toString());
                                    dashboardWelcomeTV.setText(fullName);

                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    Log.d("Exception", e.getMessage());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}