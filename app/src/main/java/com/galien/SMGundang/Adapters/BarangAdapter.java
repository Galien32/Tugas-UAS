package com.galien.SMGundang.Adapters;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.galien.SMGundang.Models.Barang;
import com.galien.SMGundang.R;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BarangAdapter extends BaseQuickAdapter<Barang, BaseViewHolder> {

    public BarangAdapter(@Nullable List<Barang> data) {
        super(R.layout.item_barang, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder viewHolder, Barang barang) {
        addChildClickViewIds(R.id.item_delete);
        viewHolder
                .setText(R.id.item_ID, "ID: "+barang.getId())
                .setText(R.id.item_name, "Name: "+barang.getName())
                .setText(R.id.item_stock, "Stock: "+barang.getStock());

    }
}
