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
import android.widget.Toast;

import com.galien.SMGundang.DB.FirebaseConfig;
import com.galien.SMGundang.Models.Barang;
import com.galien.SMGundang.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * A simple {@link Fragment} subclass.
 */
public class FormBarangFragment extends Fragment {
    private OnFragmentInteractionListener mListener;
    private FirebaseConfig config;
    private FirebaseFirestore db;

    private final String TAG = "FORM_BARANG_FRAGMENT";

    private Button cancel, save;
    private EditText id, name, stock;

    public FormBarangFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        config = new FirebaseConfig();
        db = config.getFirestore();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_form_barang, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cancel = view.findViewById(R.id.btn_cancel_add);
        save = view.findViewById(R.id.btn_add);
        id = view.findViewById(R.id.stock_ID);
        name = view.findViewById(R.id.stock_name);
        stock = view.findViewById(R.id.stock_number);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (id.getText().toString().isEmpty()
                || name.getText().toString().isEmpty()
                || stock.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "Fill the blank to save!", Toast.LENGTH_SHORT).show();
                } else if (stock.getText().toString().equals("0")) {
                    Toast.makeText(getContext(), "stock cannot be 0!", Toast.LENGTH_SHORT).show();
                } else {
                    db.collection("barang")
                            .add(new Barang(name.getText().toString()
                                    , id.getText().toString()
                                    , Integer.parseInt(stock.getText().toString())))
                            .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentReference> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(getContext(), "Save Complete!", Toast.LENGTH_SHORT).show();
                                        mListener.toList();
                                    } else {
                                        Log.d(TAG, "Save Failed!");
                                        Toast.makeText(getContext(), "Save Failed, Please check you internet!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.toList();
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
        void toList();
    }
}
