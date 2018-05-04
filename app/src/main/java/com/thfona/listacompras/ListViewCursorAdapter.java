package com.thfona.listacompras;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class ListViewCursorAdapter extends CursorAdapter {

    public ListViewCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView tvNome = view.findViewById(R.id.tvNome);
        TextView tvQtd = view.findViewById(R.id.tvQtd);

        String nome = cursor.getString(cursor.getColumnIndexOrThrow("Nome"));
        int quantidade = cursor.getInt(cursor.getColumnIndexOrThrow("Quantidade"));

        tvNome.setText(nome);
        tvQtd.setText(String.valueOf(quantidade));
    }
}
