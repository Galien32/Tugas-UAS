package com.galien.SMGundang.UI;

import android.content.Context;
import android.net.Uri;
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

public class LoginFragment extends Fragment {
    private OnFragmentInteractionListener mListener;

    private final String TAG = "LOGIN_FRAGMENT";

    private EditText email, password;
    private TextView registerLink;
    private Button btnLogin;

    private FirebaseConfig myConfig;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myConfig = new FirebaseConfig();
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        email = view.findViewById(R.id.login_user);
        password = view.findViewById(R.id.login_pass);
        registerLink = view.findViewById(R.id.login_new);
        btnLogin = view.findViewById(R.id.login_btn);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sEmail = email.getText().toString();
                String sPass = password.getText().toString();
                if (!sEmail.isEmpty() && !sPass.isEmpty()) {
                    doLogin(sEmail, sPass);
                } else {
                    Toast.makeText(getContext(), "Please fill the blank!!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        registerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.toRegisterForm();
            }
        });

    }

    private void doLogin(String email, String password) {
        Toast.makeText(getContext(), "Please wait..!", Toast.LENGTH_SHORT).show();
        myConfig.getAuth().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getContext(), "Success Login", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "Success Login");
                            mListener.loginSuccess();
                        } else {
                            Toast.makeText(getContext(), "Please check you email and password!", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "Failed Login");
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
        void toRegisterForm();
        void loginSuccess();
    }
}
