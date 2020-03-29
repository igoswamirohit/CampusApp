package com.ruds.data;

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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ruds.data.models.Students;

public class ChoosePasswordFragment extends Fragment {

    public NavController navController;
    Students studentsViewModel;
    public TextInputEditText pwd, cnfpwd;
    public TextInputLayout pwdlayout, cnflayout;
    TextView textView;
    MaterialButton loginBtn, signUpBtn;
    public String fullname, gender, contact, dep, sem, email, degree, enroll, password, conpassword;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser mUser = mAuth.getCurrentUser();

    public ChoosePasswordFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            fullname = getArguments().getString("fullname");
            gender = getArguments().getString("gender");
            enroll = getArguments().getString("enroll");
            contact = getArguments().getString("contact");
            degree = getArguments().getString("degree");
            dep = getArguments().getString("department");
            sem = getArguments().getString("semester");
            email = getArguments().getString("email");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_choose_password, container, false);
        studentsViewModel = new ViewModelProvider(this).get(Students.class);
        textView = rootView.findViewById(R.id.textView);
        textView.setText("Welcome " + studentsViewModel.getFullName() + ", Please Choose a Password for your Account.");
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        loginBtn = view.findViewById(R.id.loginRedirect);
        pwd = view.findViewById(R.id.pwdField);
        cnfpwd = view.findViewById(R.id.confirmPwdField);
        pwdlayout = view.findViewById(R.id.pwdLayout);
        cnflayout = view.findViewById(R.id.cnflayout);
        mUser = mAuth.getCurrentUser();
        signUpBtn = view.findViewById(R.id.signUpBtn);
        view.findViewById(R.id.signUpBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                password = pwd.getText().toString();
                conpassword = cnfpwd.getText().toString();
                if (password.equals(conpassword)) {
                    studentsViewModel.setPassword(pwd.getText().toString());
                    signUp();
                } else {
                    pwdlayout.setError("Password Does not match");
                    cnflayout.setError("Password Does not match");
                }
            }
        });
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_choosePasswordFragment2_to_signInFragment);
            }
        });
    }

    public void signUp() {
        mAuth.createUserWithEmailAndPassword(studentsViewModel.getEmail(), studentsViewModel.getPassword()).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d("ChoosePasswordSuccess", "createUserWithEmail:success");
                    sendVerificationEmail();
                } else {
                    Log.w("ChoosePasswordError", "createUserWithEmail:failure", task.getException());
                    return;
                }
            }
        }).addOnFailureListener(getActivity(), new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w("ChoosePasswordError", e.getMessage());
                if (e.getMessage().equals("The email address is already in use by another account.")) {
                    accountExist();
                }
            }
        });
    }

    public void sendVerificationEmail() {
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mUser.sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            UpdateUI();
                        }
                    }
                }).addOnFailureListener(getActivity(), new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), "Email not Sent buddy", Toast.LENGTH_SHORT).show();
                Log.d("Email Failure", e.getMessage());
            }
        });
    }

    private void UpdateUI() {
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        Log.d("Sent Email", "Email sent.");
        Snackbar.make(getView(), "Verification Email has sent.", 7000).show();
        textView.setText("Verification Email has been sent to your Email address, Kindly Verify and then Login");
        loginBtn.setVisibility(View.VISIBLE);
        signUpBtn.setVisibility(View.GONE);
        pwd.setEnabled(false);
        cnfpwd.setEnabled(false);
        if (!mUser.isEmailVerified()) {
            mAuth.signOut();
        }
        addDataToDatabase();
    }

    private void addDataToDatabase() {
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        studentsViewModel.setUid(mUser.getUid());

    }

    private void accountExist() {
        textView.setText("Account already exist, Kindly Login");
        Toast.makeText(getActivity(), "Account already exist, Kindly Login.", Toast.LENGTH_SHORT).show();
        navController.navigate(R.id.action_choosePasswordFragment2_to_signInFragment);
    }
}