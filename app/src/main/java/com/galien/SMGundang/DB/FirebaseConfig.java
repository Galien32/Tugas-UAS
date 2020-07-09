package com.galien.SMGundang.DB;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FirebaseConfig {

    private FirebaseAuth mAuth;
    private FirebaseUser user;

    public FirebaseConfig() {
        this.mAuth = FirebaseAuth.getInstance();
        this.user = this.mAuth.getCurrentUser();
    }

    public boolean isLogin() {
        if (this.user != null) {
            return true;
        } else return false;
    }

    public FirebaseUser getNowUser() {
        if (isLogin()) {
            return this.mAuth.getCurrentUser();
        } else return null;
    }

    public FirebaseAuth getAuth() {
        return this.mAuth;
    }

}
