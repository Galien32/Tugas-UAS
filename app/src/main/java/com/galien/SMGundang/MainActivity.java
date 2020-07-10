package com.galien.SMGundang;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.galien.SMGundang.DB.FirebaseConfig;
import com.galien.SMGundang.UI.AuthActivity;
import com.galien.SMGundang.UI.FormBarangFragment;
import com.galien.SMGundang.UI.ListBarangFragment;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements
        ListBarangFragment.OnFragmentInteractionListener,
        FormBarangFragment.OnFragmentInteractionListener {
    private FirebaseConfig db;
    private FirebaseUser user;

    private Button signOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        signOut = findViewById(R.id.btn_logout);
        db = new FirebaseConfig();
        user = db.getNowUser();
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doSignOut();
                goToAuth();
            }
        });

        Toast.makeText(this, "Welcome back "+user.getDisplayName()+" !", Toast.LENGTH_LONG).show();
        changeFragment(new ListBarangFragment());
    }

    private void goToAuth() {
        Intent intent = new Intent(this, AuthActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void changeFragment(Fragment f){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_container, f)
                .addToBackStack(null)
                .commit();
    }

    private void doSignOut(){
        if (db.isLogin()) {
            db.getAuth().signOut();
        }
    }

    @Override
    public void toFormAdd() {
        changeFragment(new FormBarangFragment());
    }

    @Override
    public void toList() {
        changeFragment(new ListBarangFragment());
    }
}
