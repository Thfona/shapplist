package com.dispmov.shapplist;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ItemActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        initConfirmarListener();
        initCancelarListener();

    }

    public void initConfirmarListener() {
        Button btnCfm = findViewById(R.id.confirmarBtn);
        btnCfm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText edtNome = findViewById(R.id.nomeEdt);
                EditText edtQtd = findViewById(R.id.qtdEdt);
                String nomeInserido = edtNome.getText().toString();
                String qtdInserida = edtQtd.getText().toString();

                if(nomeInserido.length() > 0 && qtdInserida.length() > 0) {
                    int qtdInt = Integer.parseInt(qtdInserida);
                    dbHelper.adicionarItem(nomeInserido, qtdInt);
                    Intent intent = new Intent(ItemActivity.this, MainActivity.class);
                    startActivity(intent);
                } else if(nomeInserido.length() <= 0) {

                } else {

                }
            }
        });
    }

    public void initCancelarListener() {
        Button btnCcl = findViewById(R.id.cancelarBtn);
        btnCcl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ItemActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

}