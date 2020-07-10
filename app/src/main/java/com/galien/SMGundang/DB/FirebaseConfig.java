package com.galien.SMGundang.DB;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class FirebaseConfig {

    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseFirestore store;

    public FirebaseConfig() {
        this.mAuth = FirebaseAuth.getInstance();
        this.user = this.mAuth.getCurrentUser();
        this.store = FirebaseFirestore.getInstance();
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

    public FirebaseFirestore getFirestore() {
        return this.store;
    }

    public FirebaseAuth getAuth() {
        return this.mAuth;
    }

}
