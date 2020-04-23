package com.ruds.data.CircularsUi;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ruds.data.R;
import com.ruds.data.models.UploadPDF;

import java.util.ArrayList;
import java.util.List;

public class DownloadCirculars extends Fragment {

    ListView lv;
    DatabaseReference databaseReference;
    ArrayList<UploadPDF> uploadPDFS;
    ArrayList<String> uploadsArrayList;
    NavController navController;

    public DownloadCirculars() {
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
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_download_circulars, container, false);
        uploadPDFS = new ArrayList<>();
        lv = view.findViewById(R.id.downloadCircularsLV);
        viewAllFiles();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UploadPDF uploadPDF = uploadPDFS.get(position);
                Bundle bundle = new Bundle();
                bundle.putString("url", uploadPDF.getUrl());
                navController.navigate(R.id.action_downloadCirculars_to_pdfViewer, bundle);
                /*Intent intent = new Intent();
                intent.setType(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.parse(uploadPDF.getUrl()),"application/pdf");
                startActivity(intent);*/
            }
        });
        return view;
    }

    private void viewAllFiles() {
        databaseReference = FirebaseDatabase.getInstance().getReference("Uploads");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    UploadPDF uploadPDF = postSnapshot.getValue(UploadPDF.class);
                    uploadPDFS.add(uploadPDF);
                }
                String[] uploads = new String[uploadPDFS.size()];
                for (int i = 0; i < uploads.length; i++) {
                    uploads[i] = uploadPDFS.get(i).getName();
                }
                /*for(UploadPDF ul:uploadPDFS){
                    uploadsArrayList.add(ul.getName());
                }*/

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, uploads);
                lv.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}