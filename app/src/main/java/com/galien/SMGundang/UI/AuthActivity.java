package com.galien.SMGundang.UI;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;

import com.galien.SMGundang.DB.FirebaseConfig;
import com.galien.SMGundang.MainActivity;
import com.galien.SMGundang.R;

public class AuthActivity extends AppCompatActivity implements
        LoginFragment.OnFragmentInteractionListener,
        RegisterFragment.OnFragmentInteractionListener {

    private FirebaseConfig fConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        fConfig = new FirebaseConfig();
        if (fConfig.isLogin()) {
            loginSuccess();
        } else {
            changeFragment(new LoginFragment());
        }
    }

    private void changeFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.auth_view, fragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void toRegisterForm() {
        changeFragment(new RegisterFragment());
    }

    @Override
    public void loginSuccess() {
        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    public void toLoginForm() {
        changeFragment(new LoginFragment());
    }
}
