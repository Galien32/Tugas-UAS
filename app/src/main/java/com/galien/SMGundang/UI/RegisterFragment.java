package com.galien.SMGundang.UI;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.galien.SMGundang.DB.FirebaseConfig;
import com.galien.SMGundang.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class RegisterFragment extends Fragment {
    private OnFragmentInteractionListener mListener;

    private final String TAG = "REGISTER_FRAGMENT";

    private FirebaseConfig mAuth;

    private EditText username, email, password;
    private Button btnRegister;
    private TextView alreadyHave;

    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mAuth = new FirebaseConfig();
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        username = view.findViewById(R.id.reg_username);
        email = view.findViewById(R.id.reg_email);
        password = view.findViewById(R.id.reg_pass);
        btnRegister = view.findViewById(R.id.reg_btn_create);
        alreadyHave = view.findViewById(R.id.already_have_account);

        alreadyHave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.toLoginForm();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sUName = username.getText().toString();
                String sEmail = email.getText().toString();
                String sPass = password.getText().toString();
                if (sUName.isEmpty() || sEmail.isEmpty() || sPass.isEmpty()) {
                    Toast.makeText(getContext(), "Failed Register", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "Failed_Register");
                } else {
                    createNewAccount(sUName, sEmail, sPass);
                }
            }
        });
    }

    private void createNewAccount(final String username, String email, String pass) {
        mAuth.getAuth().createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getContext(), "Create New Account Success", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getAuth().getCurrentUser();
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(username)
                                    .build();
                            if (user != null) {
                                user.updateProfile(profileUpdates)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    mListener.toLoginForm();
                                                }
                                            }
                                        });
                            } else {
                                Log.d(TAG, "User is NULL");
                            }
                        } else {
                            Toast.makeText(getContext(), "Create New Account Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void toLoginForm();
    }
}
