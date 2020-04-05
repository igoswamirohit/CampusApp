package com.ruds.data;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.ruds.data.R;
import com.ruds.data.models.AttendanceStd;
import com.ruds.data.models.Students;
import com.ruds.data.viewholder.AttendanceViewHolderStd;
import com.ruds.data.viewholder.StudentsViewHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class AttendanceStdPartFragment extends Fragment {

    public NavController navController;
    public String semester, department, lecture, ajkidate;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    FirebaseRecyclerAdapter<AttendanceStd, AttendanceViewHolderStd> adapter;
    public static ArrayList<String> attendanceArrayList = new ArrayList<String>();
    int count;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReferenceAttendance = database.getReference();

    public AttendanceStdPartFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        showList();


        Query query = database.getReference("Attendance/CSE/Sem1").orderByKey().startAt("1586025000000").endAt("1586370600000");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Log.d("TAG", "Data exists within dates");
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        Log.d("TAG", "Data are " + ds.getValue().toString());
                        AttendanceStd astd = new AttendanceStd();
                        //Map<String,Object> newHash = astd.getLecture1();
                        //Toast.makeText(getContext(), Collections.singletonList(newHash).toString(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.d("TAG", "Data does not exist within dates");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_attendance_std_part, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        for (String str : attendanceArrayList) {
            //Students std = new Students(str);
            databaseReferenceAttendance.child("Attendance").child(department).child(semester).child(ajkidate).child(lecture).child(str).setValue("true").addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(getContext(), "Successfull", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
                }
            });
        }
        return view;
    }

    private void showList() {
        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<AttendanceStd>()
                .setQuery(database.getReference("Attendance/CSE/Sem1")
                        , AttendanceStd.class).build();
        adapter = new FirebaseRecyclerAdapter<AttendanceStd, AttendanceViewHolderStd>(options) {

            @Override
            public void onViewAttachedToWindow(@NonNull AttendanceViewHolderStd holder) {
               /* for (int i = 0; i < count; i++) {
                    if (holder.checkBox.getText() != "Rohit") {
                        count--;
                    }
                }
                String countString = Integer.toString(count);
                Toast.makeText(getContext(), countString, Toast.LENGTH_SHORT).show();*/
                super.onViewAttachedToWindow(holder);
            }

            @Override
            public void onDataChanged() {
                count = adapter.getItemCount();
                super.onDataChanged();
            }

            @Override
            public int getItemCount() {
                count = super.getItemCount();
                return super.getItemCount();
            }

            @NonNull
            @Override
            public AttendanceViewHolderStd onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitemview, parent, false);
                AttendanceViewHolderStd holder1 = new AttendanceViewHolderStd(view);
                return holder1;
            }

            @Override
            protected void onBindViewHolder(@NonNull final AttendanceViewHolderStd holder, int position, @NonNull AttendanceStd model) {
                holder.checkBox.setText(model.getRohit());
            }
        };
        adapter.startListening();
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
        count = adapter.getItemCount();
    }
}