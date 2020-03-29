package com.ruds.data;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.ruds.data.models.Students;

public class SignUpFragment extends Fragment {

    TextView tv;
    Students studentsViewModel;
    public TextInputEditText fullName, enrollNo, sem, dep, degree, contact, gender, email;

    public SignUpFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        studentsViewModel = new ViewModelProvider(this).get(Students.class);
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_sign_up, container, false);
        tv = rootView.findViewById(R.id.textView);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final NavController navController = Navigation.findNavController(view);
        fullName = view.findViewById(R.id.nameField);
        enrollNo = view.findViewById(R.id.enrollField);
        contact = view.findViewById(R.id.contactField);
        email = view.findViewById(R.id.emailField);
        dep = view.findViewById(R.id.departmentField);
        sem = view.findViewById(R.id.semesterField);
        gender = view.findViewById(R.id.genderField);
        degree = view.findViewById(R.id.degreeField);
        view.findViewById(R.id.next_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                studentsViewModel.setFullName(fullName.getText().toString());
                studentsViewModel.setEnroll(enrollNo.getText().toString());
                studentsViewModel.setContact(contact.getText().toString());
                studentsViewModel.setEmail(email.getText().toString());
                studentsViewModel.setDep(dep.getText().toString());
                studentsViewModel.setSem(sem.getText().toString());
                studentsViewModel.setGender(gender.getText().toString());
                studentsViewModel.setDegree(degree.getText().toString());

                /*Bundle args = new Bundle();
                args.putString("fullname",studentsViewModel.getFullName());
                args.putString("enroll",studentsViewModel.getEnroll());
                args.putString("degree",studentsViewModel.getDegree());
                args.putString("department",studentsViewModel.getDep());
                args.putString("contact",studentsViewModel.getContact());
                args.putString("email",studentsViewModel.getEmail());
                args.putString("gender",studentsViewModel.getGender());
                args.putString("semester",studentsViewModel.getSem());*/

                navController.navigate(R.id.action_signUpFragment_to_choosePasswordFragment2);
            }
        });

    }
}