package com.ruds.data.SubjectsUI;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ruds.data.Adapters.SubjectsAdapter;
import com.ruds.data.R;
import com.ruds.data.models.Subjects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddSubjectsFragment extends Fragment {

    public static final ArrayList<String> items = new ArrayList<String>();
    RecyclerView recyclerView;
    static RecyclerView.Adapter adapter;
    FloatingActionButton listFab;
    Button subjectsSubmitBtn;
    EditText subjectsNameET;
    Float dX, dY;
    int listSize;
    Map<String, String> dataMap = new HashMap<>();

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference();

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;

    public AddSubjectsFragment() {
    }

    public static void removeAt(int position) {
        items.remove(position);
        adapter.notifyDataSetChanged();
        adapter.notifyItemRemoved(position);
        adapter.notifyItemRangeChanged(position, items.size());
        Log.d("data from removeAt", items.toString());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_subjects, container, false);
        recyclerView = view.findViewById(R.id.list);
        listFab = view.findViewById(R.id.listFab);
        subjectsNameET = view.findViewById(R.id.subjectsAddET);
        subjectsSubmitBtn = view.findViewById(R.id.subjectsSubmitBtn);
        listFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String subjectName = subjectsNameET.getText().toString();
                items.add(subjectName);
                Log.d("data", items.toString());
                adapter.notifyDataSetChanged();
                subjectsNameET.setText("");

            }
        });
        subjectsSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listSize = items.size();
                Toast.makeText(getActivity(), Integer.toString(items.size()), Toast.LENGTH_SHORT).show();
                for (int i = 0; i < listSize; i++) {
                    int subNo = i + 1;
                    String subjectKey = "Subject" + subNo;
                    dataMap.put(subjectKey, items.get(i));
                }
                Log.d("Map Data", dataMap.toString());
                databaseReference.child("Subjects").child("CSE").child("Sem1").setValue(dataMap).addOnSuccessListener(new OnSuccessListener<Void>() {
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
        });
        Context context = view.getContext();
        if (mColumnCount <= 1) {
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
        }
        adapter = new SubjectsAdapter(items);
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                switch (event.getAction()) {
                    case DragEvent.ACTION_DRAG_LOCATION:
                        dX = event.getX();
                        dY = event.getY();
                        break;
                    case DragEvent.ACTION_DRAG_ENDED:
                        listFab.setX(dX - listFab.getWidth() / 2);
                        listFab.setY(dY - listFab.getHeight() / 2);
                        break;
                }
                return true;
            }
        });

        listFab.setOnLongClickListener(new View.OnLongClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public boolean onLongClick(View v) {
                View.DragShadowBuilder myShadow = new View.DragShadowBuilder(listFab);
                v.startDragAndDrop(null, myShadow, null, View.DRAG_FLAG_GLOBAL);
                return true;
            }
        });
    }
}