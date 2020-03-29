package com.ruds.data;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
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
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignInFragment extends Fragment {

    public NavController navController;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser mUser = mAuth.getCurrentUser();

    public TextInputEditText email, password;
    String Email, Pwd;
    TextView status;
    public TextInputLayout pwdL, emailL;

    public SignInFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);
        email = view.findViewById(R.id.emailField);
        password = view.findViewById(R.id.passwordField);


        view.findViewById(R.id.signInBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        return view;
    }

    private void signIn() {
        setEmailPassword();
        mAuth.signInWithEmailAndPassword(Email, Pwd).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d("Successfull Login", "signInWithEmail:success");
                mUser = mAuth.getCurrentUser();
                if (mUser != null) {
                    if (!mUser.isEmailVerified()) {
                        Snackbar.make(getView(), "Please verify Email first.", 5000).show();
                        mAuth.signOut();
                    } else {
                        navController.navigate(R.id.action_signInFragment_to_mainFragment2);
                    }
                }
            }
        }).addOnFailureListener(getActivity(), new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("Login Failed", e.getMessage());
                if (e.getMessage().equals("The password is invalid or the user does not have a password.")) {
                    Snackbar.make(getView(), "Password seems Invalid", 5000).show();
                    pwdL.setError("Wrong Password");
                    password.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            if (pwdL.isErrorEnabled()) {
                                pwdL.setErrorEnabled(false);
                            }
                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                        }
                    });
                } else if (e.getMessage().equals("The user may have been deleted.") || e.getMessage().equals("There is no user record corresponding to this identifier. The user may have been deleted.")) {
                    Snackbar.make(getView(), "User does not exist,kindly Sign Up", 5000).setAction("Sign up", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            navController.navigate(R.id.action_signInFragment_to_signUpFragment);
                        }
                    }).show();
                }
            }
        });
    }

    private void setEmailPassword() {
        Email = email.getText().toString();
        Pwd = password.getText().toString();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        emailL = view.findViewById(R.id.emailLayout);
        pwdL = view.findViewById(R.id.pwdLayout);
        if (mUser != null && mUser.isEmailVerified()) {
            navController.navigate(R.id.action_signInFragment_to_mainFragment2);
        }
        view.findViewById(R.id.signUpBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_signInFragment_to_signUpFragment);
            }
        });
    }

}