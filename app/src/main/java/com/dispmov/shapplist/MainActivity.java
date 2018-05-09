package com.dispmov.shapplist;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
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
        initExcluirListener();
        exibirLista();

    }

    protected void exibirLista() {
        ListViewCursorAdapter lvca = new ListViewCursorAdapter(this, dbHelper.getLista());
        listaDeCompras.setAdapter(lvca);
    }

    protected void initListViewListener() {
        listaDeCompras.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                itemSelecionado = (int) id;
            }
        });
    }

    protected void initAdicionarListener() {
        Button btnAdc = findViewById(R.id.adicionarBtn);
        btnAdc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ItemActivity.class);
                startActivity(intent);
            }
        });
    }

    protected void initEditarListener() {
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

    protected void initExcluirListener() {
        Button btnRem = findViewById(R.id.excluirBtn);
        btnRem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(itemSelecionado != null) {

                    final String[] conteudo = dbHelper.getItem(itemSelecionado);

                    AlertDialog alerta;

                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Confirmar Exclusão");
                    builder.setMessage("Deseja realmente excluir o item " + conteudo[0] + "?");
                    builder.setPositiveButton("Excluir", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {
                            dbHelper.excluirItem(conteudo[0]);
                            exibirLista();
                        }
                    });
                    builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {

                        }
                    });

                    alerta = builder.create();
                    alerta.show();

                } else {
                    Toast toast = Toast.makeText(MainActivity.this, "Selecione um item para excluir", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
    }

}
