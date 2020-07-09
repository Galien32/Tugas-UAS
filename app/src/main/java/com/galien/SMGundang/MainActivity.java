package com.galien.SMGundang;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.galien.SMGundang.DB.FirebaseConfig;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private FirebaseConfig db;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new FirebaseConfig();
        user = db.getNowUser();
        Toast.makeText(this, "Welcome back "+user.getDisplayName()+" !", Toast.LENGTH_LONG).show();
    }
}
