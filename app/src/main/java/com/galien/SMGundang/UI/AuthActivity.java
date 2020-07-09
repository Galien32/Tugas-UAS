package com.galien.SMGundang.UI;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;

import com.galien.SMGundang.R;

public class AuthActivity extends AppCompatActivity {
    private Fragment registerFragment = new RegisterFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.auth_view, new LoginFragment())
                .addToBackStack(null)
                .commit();
    }
}
