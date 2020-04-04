package com.ruds.data.AttendanceUi;

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
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ruds.data.R;
import com.ruds.data.models.Students;
import com.ruds.data.viewholder.StudentsViewHolder;

import java.util.ArrayList;

public class AttendanceListFragment extends Fragment {

    public NavController navController;
    private Button submit_btn, updateBtn, cancel_btn;
    public String semester, department, lecture, ajkidate;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter mAdapter;
    private CheckBox checkBox;
    FirebaseRecyclerAdapter<Students, StudentsViewHolder> adapter;
    public static ArrayList<String> studentsArrayList = new ArrayList<String>();
    public static ArrayList<String> removedArrayList = new ArrayList<String>();

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReferenceAttendance = database.getReference();

    public AttendanceListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            semester = getArguments().getString("Semester");
            department = getArguments().getString("Department");
            lecture = getArguments().getString("Lecture");
            ajkidate = getArguments().getString("Date");
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        showList(department, semester);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_attendance_list, container, false);
        submit_btn = view.findViewById(R.id.submit_btn);
        cancel_btn = view.findViewById(R.id.cancel_btn);
        updateBtn = view.findViewById(R.id.delete_btn);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        try {
            submit_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //String key = databaseReferenceAttendance.child("Attendance").push().getKey();

                    for (String str : studentsArrayList) {
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
                }
            });
        } catch (Exception ex) {
            Log.w("Exception", ex.getMessage());
        }

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (String str : studentsArrayList) {
                    Students std = new Students(str);
                    databaseReferenceAttendance.child("Attendance").child(department).child(semester).child(ajkidate).child(lecture).child(str).removeValue();
                }
            }
        });

        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        return view;
    }

    private void showList(String department1, String semester1) {
        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<Students>().setQuery(database.getReference("Students/" + department1 + "/" + semester1), Students.class).build();
        adapter = new FirebaseRecyclerAdapter<Students, StudentsViewHolder>(options) {

            @NonNull
            @Override
            public StudentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitemview, parent, false);
                StudentsViewHolder holder1 = new StudentsViewHolder(view);
                return holder1;
            }

            @Override
            protected void onBindViewHolder(@NonNull final StudentsViewHolder holder, int position, @NonNull Students model) {
                holder.checkBox.setText(model.getName());
                holder.checkBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Integer pos = (Integer) holder.checkBox.getTag();
                        //Toast.makeText(getBaseContext(), imageModelArrayList.get(pos).getAnimal() + " clicked!", Toast.LENGTH_SHORT).show();
                        Students std = new Students();
                        std.setName(holder.checkBox.getText().toString());
                        //String name = (String) holder.checkBox.getText();
                        //Toast.makeText(MainActivity.this, "Checkbox Checked", Toast.LENGTH_SHORT).show();
                        if (holder.checkBox.isChecked()) {
                            studentsArrayList.add(std.getName());
                        } else if (!holder.checkBox.isChecked()) {
                            studentsArrayList.remove(std.getName());
                            //studentsArrayList.remove((String) holder.checkBox.getText());
                            //removedArrayList.add(nae);
                        }
                        for (String str : studentsArrayList) {
                            Log.w("array", str);
                        }
                    }
                });
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Launch PostDetailActivity
                        holder.checkBox.setChecked(true);
                    }
                });
                holder.setItemClickListener(new StudentsViewHolder.ItemClickListener() {
                    @Override
                    public void onItemClick(View v, int pos) {
                        CheckBox checkBox = (CheckBox) v;
                        Toast.makeText(getContext(), "This is working too.", Toast.LENGTH_SHORT).show();

                    }
                });

            }
        };
        adapter.startListening();
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }

}