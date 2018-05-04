package com.thfona.listacompras;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    public DatabaseHelper dbHelper = new DatabaseHelper(this);
    private ListView listaDeCompras = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listaDeCompras = findViewById(R.id.listaDeComprasListView);
        ImageButton btnAdc = findViewById(R.id.adicionarBtn);
        ImageButton btnEdt = findViewById(R.id.editarBtn);
        ImageButton btnRem = findViewById(R.id.removerBtn);
        Intent adcIntent = new Intent(this, ItemViewActivity.class);

        exibirLista();

    }

    public void exibirLista() {
        ListViewCursorAdapter lvca = new ListViewCursorAdapter(this, dbHelper.getDados());
        listaDeCompras.setAdapter(lvca);
    }

}
