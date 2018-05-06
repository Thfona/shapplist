package com.dispmov.shapplist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper = new DatabaseHelper(this);
    private ListView listaDeCompras = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listaDeCompras = findViewById(R.id.listaDeComprasListView);

        initAdicionarListener();
        exibirLista();

    }

    public void exibirLista() {
        ListViewCursorAdapter lvca = new ListViewCursorAdapter(this, dbHelper.getDados());
        listaDeCompras.setAdapter(lvca);
    }

    public void initAdicionarListener() {
        Button btnAdc = findViewById(R.id.adicionarBtn);
        btnAdc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ItemActivity.class);
                startActivity(intent);
            }
        });
    }

    public void initEditarListener() {
        Button btnEdt = findViewById(R.id.editarBtn);
        btnEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ItemActivity.class);
                startActivity(intent);
            }
        });
    }

    public void initRemoverListener() {
        Button btnRem = findViewById(R.id.removerBtn);
    }

}
