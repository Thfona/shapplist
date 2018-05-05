package com.dispmov.listacompras;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    public DatabaseHelper dbHelper = new DatabaseHelper(this);
    private ListView listaDeCompras = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listaDeCompras = findViewById(R.id.listaDeComprasListView);
        Button btnAdc = findViewById(R.id.adicionarBtn);
        Button btnEdt = findViewById(R.id.editarBtn);
        Button btnRem = findViewById(R.id.removerBtn);
        Intent itemIntent = new Intent(this, ItemActivity.class);

        exibirLista();

    }

    public void exibirLista() {
        ListViewCursorAdapter lvca = new ListViewCursorAdapter(this, dbHelper.getDados());
        listaDeCompras.setAdapter(lvca);
    }

}
