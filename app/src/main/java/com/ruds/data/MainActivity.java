package com.ruds.data;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ruds.data.viewholder.StudentsViewHolder;
import com.ruds.data.models.Students;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private Button verifybtn, btn, date, updateBtn, logout;
    private EditText edit1, edit2;
    private TextView username;
    private Spinner dep, sem, lec;
    public String semester, department, lecture, ajkidate;
    public Date ajkadate;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter mAdapter;
    private CheckBox checkBox;
    FirebaseRecyclerAdapter<Students, StudentsViewHolder> adapter;
    public static ArrayList<String> studentsArrayList = new ArrayList<String>();
    public static ArrayList<String> removedArrayList = new ArrayList<String>();

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser mUser = mAuth.getCurrentUser();

    Attendance_model am = new Attendance_model();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference("Students/CSE/Sem1");
    DatabaseReference databaseReferenceAttendance = database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = findViewById(R.id.button);
        dep = findViewById(R.id.spinner);
        sem = findViewById(R.id.spinner2);
        lec = findViewById(R.id.spinner3);
        date = findViewById(R.id.date);
        username = findViewById(R.id.userName);
        updateBtn = findViewById(R.id.updateButton);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        logout = findViewById(R.id.logOut);
        verifybtn = findViewById(R.id.verifyBtn);

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        /*mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();*/
        //username.setText(mUser.getDisplayName());
        if (mUser != null) {
            try {
                Boolean verified = mUser.isEmailVerified();
                username.setText(verified.toString());
                //showList(department,semester);
            } catch (Exception ex) {
                Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        ArrayAdapter<CharSequence> semAdapter = ArrayAdapter.createFromResource(this,
                R.array.Semester, android.R.layout.simple_spinner_item);
        semAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<CharSequence> lecAdapter = ArrayAdapter.createFromResource(this,
                R.array.Lecture, android.R.layout.simple_spinner_item);
        lecAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<CharSequence> depAdapter = ArrayAdapter.createFromResource(this,
                R.array.Department, android.R.layout.simple_spinner_item);
        depAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sem.setAdapter(semAdapter);
        dep.setAdapter(depAdapter);
        lec.setAdapter(lecAdapter);

        sem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                semester = parent.getItemAtPosition(position).toString();
                showList(department, semester);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        dep.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                department = parent.getItemAtPosition(position).toString();
                showList(department, semester);
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
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
        try {
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String key = databaseReferenceAttendance.child("Attendance").push().getKey();
                    Attendance_model attendance_model = new Attendance_model(department, lecture, semester, ajkidate);

                    if (ajkidate == null) {
                        Toast.makeText(MainActivity.this, "Select Date Please", Toast.LENGTH_SHORT).show();
                            /*Map<String, Object> postValues = Students.toMap();
                            Map<String, Object> childUpdates = new HashMap<>();
                            childUpdates.put("/Attendance/" + department +"//"+ semester + "/" + ajkidate+"/"+lecture, postValues);
                            //childUpdates.put("/user-posts/" + userId + "/" + key, postValues);
                            databaseReferenceAttendance.updateChildren(childUpdates);*/
                    } else {
                        for (String str : studentsArrayList) {
                            Students std = new Students(str);
                            databaseReferenceAttendance.child("Attendance").child(department).child(semester).child(ajkidate).child(lecture).child(str).setValue("true").addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(MainActivity.this, "Successfull", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

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

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                if (mUser != null) {
                    Intent intent = new Intent(MainActivity.this, SignInActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        sendUserToLogin();
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
                        String nae = (String) holder.checkBox.getText();
                        //Toast.makeText(MainActivity.this, "Checkbox Checked", Toast.LENGTH_SHORT).show();
                        if (holder.checkBox.isChecked()) {
                            studentsArrayList.add(nae);
                        } else if (!holder.checkBox.isChecked()) {
                            studentsArrayList.remove((String) holder.checkBox.getText());
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

                    }
                });

            }
        };

        adapter.startListening();
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        /*if(mUser != null){
            return false;
        }*/
        return super.onKeyDown(keyCode, event);
    }

    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, this, Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        int m = month + 1;
        ajkidate = dayOfMonth + "-" + m + "-" + year;

    }

    /*private void addArtist(){
        String name = edit1.getText().toString();
        String details = edit2.getText().toString();

        if(TextUtils.isEmpty(name)){
            edit1.setError("Enter kar kuch madarchod");
        }else{
            String id = databaseArtists.push().getKey();

            Artist artist = new Artist(id,name,details);

            databaseArtists.child(id).setValue(artist);

            Toast.makeText(this, "added", Toast.LENGTH_SHORT).show();
        }

        if(TextUtils.isEmpty(details)){
            edit2.setError("Enter kar kuch madarchod");
        }
    }*/


    public void sendUserToLogin() {
        if (mUser == null) {
            Intent intent = new Intent(MainActivity.this, SignInActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }
    }


}