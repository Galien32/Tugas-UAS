package com.galien.SMGundang.UI;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.galien.SMGundang.Adapters.BarangAdapter;
import com.galien.SMGundang.DB.FirebaseConfig;
import com.galien.SMGundang.Models.Barang;
import com.galien.SMGundang.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListBarangFragment extends Fragment {
    private OnFragmentInteractionListener mListener;
    private final String TAG = "LIST_BARANG";
    private FirebaseConfig config;
    private FirebaseFirestore db;

    private RecyclerView rvBarang;
    private BarangAdapter adapter;

    private FloatingActionButton btnFormAdd;

    private List<Barang> listBarang = new ArrayList<>();
    private List<String> listKey = new ArrayList<>();

    public ListBarangFragment() {
        config = new FirebaseConfig();
        db = config.getFirestore();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_barang, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvBarang = view.findViewById(R.id.rv_barang);
        btnFormAdd = view.findViewById(R.id.floating_add_btn);
        db.collection("barang")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult() != null) {
                                for (QueryDocumentSnapshot doc : task.getResult()) {
                                    listBarang.add(new Barang(
                                            doc.get("name").toString(),
                                            doc.get("id").toString(),
                                            Integer.parseInt(doc.get("stock").toString())
                                    ));
                                    listKey.add(doc.getId());
                                }
                                createListView(listBarang);
                            }
                        } else {
                            Log.d(TAG, "Failed Read data!");
                        }
                    }
                });
    }

    private void deleteItem(String key, final int pos) {
        Toast.makeText(getContext(), "Please wait!", Toast.LENGTH_SHORT).show();
        db.collection("barang")
                .document(key)
                .delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            listBarang.remove(pos);
                            Toast.makeText(getContext(), "Delete Success", Toast.LENGTH_SHORT).show();
                            reloadListView();
                        } else {
                            Toast.makeText(getContext(), "Deleting fail, check your internet!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void createListView(List<Barang> ls) {
        adapter = new BarangAdapter(ls);
        adapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                deleteItem(listKey.get(position), position);
//                Toast.makeText(getContext(), "doc key: "+listKey.get(position), Toast.LENGTH_SHORT).show();
            }
        });
        reloadListView();
    }

    private void reloadListView() {
        rvBarang.setAdapter(adapter);
        rvBarang.setLayoutManager(new LinearLayoutManager(getContext()));
        btnFormAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.toFormAdd();
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
        void toFormAdd();
    }
}
