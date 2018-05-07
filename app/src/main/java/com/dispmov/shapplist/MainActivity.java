package com.dispmov.shapplist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper = new DatabaseHelper(this);
    private ListView listaDeCompras = null;
    private Integer itemSelecionado = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listaDeCompras = findViewById(R.id.listaDeComprasListView);

        initListViewListener();
        initAdicionarListener();
        initEditarListener();
        initRemoverListener();
        exibirLista();

    }

    public void exibirLista() {
        ListViewCursorAdapter lvca = new ListViewCursorAdapter(this, dbHelper.getDados());
        listaDeCompras.setAdapter(lvca);
    }

    public void initListViewListener() {
        listaDeCompras.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                itemSelecionado = (int) id;
            }
        });
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
                if(itemSelecionado != null) {
                    String[] conteudo = dbHelper.getItem(itemSelecionado);
                    Intent intent = new Intent(MainActivity.this, ItemActivity.class);
                    intent.putExtra("conteudo", conteudo);
                    startActivity(intent);
                } else {
                    Toast toast = Toast.makeText(MainActivity.this, "Selecione um item para editar", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
    }

    public void initRemoverListener() {
        Button btnRem = findViewById(R.id.removerBtn);
        btnRem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(itemSelecionado != null) {
                    String[] conteudo = dbHelper.getItem(itemSelecionado);
                    dbHelper.removerItem(conteudo[0]);
                    exibirLista();
                } else {
                    Toast toast = Toast.makeText(MainActivity.this, "Selecione um item para remover", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
    }

}
