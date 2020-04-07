package com.ruds.data;

import android.content.Intent;
import android.icu.text.Edits;
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
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AttendanceStdPartFragment extends Fragment {

    public NavController navController;
    public String semester, department, lecture, ajkidate, keyValue;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    FirebaseRecyclerAdapter<AttendanceStd, AttendanceViewHolderStd> adapter;
    public static ArrayList<String> attendanceArrayList = new ArrayList<String>();
    int count, countArray = 0;
    ArrayList<String> a = new ArrayList<>();
    Button btn;
    Map<String, String> Lecture1Map, Lecture2Map, Lecture3Map, Lecture4Map, Lecture5Map, Lecture6Map = new HashMap<>();
    TextView tt;
    ArrayList<Map<String, String>> ar = new ArrayList<>();
    ArrayList<String> listOfKeys = new ArrayList<>();
    AttendanceStd attendanceStd = new AttendanceStd();
    Set<String> currentSet;
    int clickCount = 1;

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

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



               /* FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference dbRef = database.getReference();
                dbRef.child("Attendance").child("CSE").child("Sem1").addValueEventListener(new ValueEventListener()
                {
                    @Override
                    public void onDataChange(DataSnapshot dataSnap)
                    {
                        Map<String, Integer> tot_record = new HashMap<>();
                        int tot1=0,tot2=0,tot3=0,tot4=0,tot5=0,tot6=0;
                        for(DataSnapshot dates : dataSnap.getChildren())
                        {
                            for(DataSnapshot lectures : dates.getChildren())
                            {
                                try
                                {
                                    switch (lectures.getKey())
                                    {
                                        case "Lecture1":
                                            tot1+=1;
                                            tot_record.put(lectures.getKey(),tot1);
                                            break;
                                        case "Lecture2":
                                            tot2+=1;
                                            tot_record.put(lectures.getKey(),tot2);
                                            break;
                                        case "Lecture3":
                                            tot3+=1;
                                            tot_record.put(lectures.getKey(),tot3);
                                            break;
                                        case "Lecture4":
                                            tot4+=1;
                                            tot_record.put(lectures.getKey(),tot4);
                                            break;
                                        case "Lecture5":
                                            tot5+=1;
                                            tot_record.put(lectures.getKey(),tot5);
                                            break;
                                        case "Lecture6":
                                            tot6+=1;
                                            tot_record.put(lectures.getKey(),tot6);
                                            break;
                                        default:
                                            return;
                                    }
                                    Log.d("total levtures",tot_record.toString());
                                    int tot = tot1+tot2+tot3+tot4+tot5+tot6;
                                    Log.d("total lectures",Integer.toString(tot));
                                }
                                catch (Exception e){
                                Snackbar.make(getView(),e.getMessage(),5000).show();
                                }

                            } }
                            *//*String lecture = "Lecture2";
                            int final_percent = (stu_record.get(lecture)/tot_record.get(lecture))*100;
                            Log.d("Attendance",Integer.toString(final_percent)+" %");*//*
                        }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });*/


                countArray = 0;
                Query query = database.getReference("Attendance/CSE/Sem1").orderByKey().startAt("1586025000000").endAt("1586370600000");
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            Log.d("TAG", "Data exists within dates");
                            //Iterable<DataSnapshot> snapshotIterable = dataSnapshot.getChildren();
                            try {
                                countArray = 0;
                                Map<String, Integer> tot_record = new HashMap<>();
                                int tot = 2, tot1 = 0, tot2 = 0, tot3 = 0, tot4 = 0, tot5 = 0, tot6 = 0;
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
                                        Log.d("total levtures", tot_record.toString());
                                        tot = tot1 + tot2 + tot3 + tot4 + tot5 + tot6;
                                        Log.d("total lectures", Integer.toString(tot));
                                    }
                                    Log.d("TAG", "Data are " + ds.getValue().toString());
                                    AttendanceStd asrd = ds.getValue(AttendanceStd.class);
                                    Long cc = ds.getChildrenCount();
                                    Snackbar.make(getView(), Long.toString(cc), 7000).show();
                                    Lecture1Map = asrd.getLecture1();
                                    Lecture2Map = asrd.getLecture2();
                                    Lecture3Map = asrd.getLecture3();
                                    Lecture4Map = asrd.getLecture4();
                                    Lecture5Map = asrd.getLecture5();
                                    Lecture6Map = asrd.getLecture6();

                                    ar.add(Lecture1Map);
                                    ar.add(Lecture2Map);
                                    ar.add(Lecture3Map);
                                    ar.add(Lecture4Map);
                                    ar.add(Lecture5Map);
                                    ar.add(Lecture6Map);
                                }
                                for (Map<String, String> a : ar) {
                                    Set<String> keySet = a.keySet();
                                    listOfKeys = new ArrayList<>(keySet);
                                    for (String key : listOfKeys) {
                                        if (key.equals("Rohit")) {
                                            countArray++;
                                        }
                                    }
                                }
                                int studentsPrecent = countArray;
                                double percent = ((double) studentsPrecent / (double) tot) * 100;
                                Log.d("Percentage dekh", Integer.toString(studentsPrecent));
                                Log.d("Percentage dekh", Integer.toString(tot));
                                Log.d("Percentage dekh", Double.toString(percent));
                            } catch (Exception e) {
                                Snackbar.make(getView(), e.getMessage(), 7000).show();
                            }
                            tt.setText(Integer.toString(countArray));
                        } else {
                            Log.d("TAG", "Data does not exist within dates");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_attendance_std_part, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        btn = view.findViewById(R.id.btn);
        tt = view.findViewById(R.id.tv);

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
                .setQuery(database.getReference("Attendance/CSE/Sem1").orderByKey().startAt("1586025000000").endAt("1586543400000")
                        , AttendanceStd.class).build();
        adapter = new FirebaseRecyclerAdapter<AttendanceStd, AttendanceViewHolderStd>(options) {

            @Override
            public void onViewAttachedToWindow(@NonNull AttendanceViewHolderStd holder) {
                        /*if(e.getMessage().equals("Attempt to invoke interface method 'java.util.Set java.util.Map.keySet()' on a null object reference")){
                            Snackbar.make(getView(),"Please Contact Your Teacher!!!",7000).show();
                super.onViewAttachedToWindow(holder);*/
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
                /*try {
                    Lecture1Map = model.getLecture1();
                    Lecture2Map = model.getLecture2();
                    Lecture3Map = model.getLecture3();
                    Lecture4Map = model.getLecture4();
                    Lecture5Map = model.getLecture5();
                    Lecture6Map = model.getLecture6();

                    Set<String> keySet1 = Lecture1Map.keySet();
                    Set<String> keySet2 = Lecture2Map.keySet();
                    Set<String> keySet3 = Lecture3Map.keySet();
                    Set<String> keySet4 = Lecture4Map.keySet();
                    Set<String> keySet5 = Lecture5Map.keySet();
                    Set<String> keySet6 = Lecture6Map.keySet();

                    listOfKeys = new ArrayList<String>(keySet1);
                    for (String key : listOfKeys) {
                        //holder.checkBox.setText(key);
                        // System.out.println(key);
                        if (key.equals("Rohit")) {
                            countArray++;
                        }
                    }
                    listOfKeys = new ArrayList<String>(keySet2);
                    for (String key : listOfKeys) {
                        //holder.checkBox.setText(key);
                        // System.out.println(key);
                        if (key.equals("Rohit")) {
                            countArray++;
                        }
                    }
                    listOfKeys = new ArrayList<String>(keySet3);
                    for (String key : listOfKeys) {
                        //holder.checkBox.setText(key);
                        // System.out.println(key);
                        if (key.equals("Rohit")) {
                            countArray++;
                        }
                    }
                    listOfKeys = new ArrayList<String>(keySet4);
                    for (String key : listOfKeys) {
                        //holder.checkBox.setText(key);
                        // System.out.println(key);
                        if (key.equals("Rohit")) {
                            countArray++;
                        }
                    }
                    listOfKeys = new ArrayList<String>(keySet5);
                    for (String key : listOfKeys) {
                        //holder.checkBox.setText(key);
                        // System.out.println(key);
                        if (key.equals("Rohit")) {
                            countArray++;
                        }
                    }
                    listOfKeys = new ArrayList<String>(keySet6);
                    for (String key : listOfKeys) {
                        //holder.checkBox.setText(key);
                        // System.out.println(key);
                        if (key.equals("Rohit")) {
                            countArray++;
                        }
                    }

                } catch (Exception e) {
                    Log.d("Exceptionaaya", e.getMessage());
                    if(e.getMessage().equals("Attempt to invoke interface method 'java.util.Set java.util.Map.keySet()' on a null object reference")){
                        Snackbar.make(getView(),"Please Contact Your Teacher!!!",7000).show();
                    }
                }*/




                /*Set<Map.Entry<String,String>> entrySet = LectureMap.entrySet();
                ArrayList<Map.Entry<String, String>> listOfEntry = new ArrayList<Map.Entry<String,String>>(entrySet);
                Log.d("PrintData",listOfEntry.toString());
                for (Map.Entry<String, String> entry : listOfEntry)
                {
                    *//*keyValue = entry.getKey()+" : "+entry.getValue();
                    a.add(keyValue);*//*
                    if(holder.checkBox.getText().toString() == "Rohit : true"){
                        countArray++;
                    }
                    holder.checkBox.setText(entry.getKey()+" : "+entry.getValue());
                    //System.out.println(entry.getKey()+" : "+entry.getValue());
                }*/

                /*a.add(model.getLecture1().toString());
                    if(a.contains("Rohit=true")){
                        countArray++;
                    }
                Log.d("BangyaArray",a.toString());
                model.getLecture1().size();
                holder.checkBox.setText(Integer.toString(countArray));*/
                //Log.d("Bangya",model.getLecture1().toString());
            }

        };
        adapter.startListening();
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }

    public void iterate(Set<String> s) {
        listOfKeys = new ArrayList<String>(s);
        for (String key : listOfKeys) {
            //holder.checkBox.setText(key);
            // System.out.println(key);
            if (key.equals("Rohit")) {
                countArray++;
            }
        }
    }
}