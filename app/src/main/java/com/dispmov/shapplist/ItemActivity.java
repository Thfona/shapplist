package com.dispmov.shapplist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ItemActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        Intent intent = getIntent();

        if(intent.hasExtra("conteudo")) {
            String[] conteudo = intent.getStringArrayExtra("conteudo");
            EditText edtNome = findViewById(R.id.nomeEdt);
            EditText edtQtd = findViewById(R.id.qtdEdt);
            edtNome.setText(conteudo[0], TextView.BufferType.EDITABLE);
            edtQtd.setText(conteudo[1], TextView.BufferType.EDITABLE);
            initConfirmarListener(conteudo);
        } else {
            initConfirmarListener();
        }

        initCancelarListener();

    }

    protected void initConfirmarListener() {
        Button btnCfm = findViewById(R.id.confirmarBtn);
        btnCfm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText edtNome = findViewById(R.id.nomeEdt);
                EditText edtQtd = findViewById(R.id.qtdEdt);
                String nomeInserido = edtNome.getText().toString();
                String qtdInserida = edtQtd.getText().toString();
                boolean disponibilidade = !dbHelper.itemExiste(nomeInserido);
                if(parametrosAtendemCondicoes(nomeInserido, qtdInserida, disponibilidade)) {
                    int qtdInt = Integer.parseInt(qtdInserida);
                    dbHelper.adicionarItem(nomeInserido, qtdInt);
                    Intent intent = new Intent(ItemActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    avisos(nomeInserido, qtdInserida, disponibilidade);
                }
            }
        });
    }

    protected void initConfirmarListener(final String[] conteudo) {
        Button btnCfm = findViewById(R.id.confirmarBtn);
        btnCfm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText edtNome = findViewById(R.id.nomeEdt);
                EditText edtQtd = findViewById(R.id.qtdEdt);
                String nomeInserido = edtNome.getText().toString();
                String qtdInserida = edtQtd.getText().toString();
                boolean disponibilidade;
                if(nomeInserido.equals(conteudo[0])) {
                    disponibilidade = true;
                } else {
                    disponibilidade = !dbHelper.itemExiste(nomeInserido);
                }
                if(parametrosAtendemCondicoes(nomeInserido, qtdInserida, disponibilidade)) {
                    int qtdInt = Integer.parseInt(qtdInserida);
                    dbHelper.editarItem(conteudo[0], nomeInserido, qtdInt);
                    Intent intent = new Intent(ItemActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    avisos(nomeInserido, qtdInserida, disponibilidade);
                }
            }
        });
    }

    protected void initCancelarListener() {
        Button btnCcl = findViewById(R.id.cancelarBtn);
        btnCcl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ItemActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    protected boolean parametrosAtendemCondicoes(String nome, String quantidade, boolean disponivel) {
        if(nome.length() > 0 && quantidade.length() > 0 && disponivel) {
            try {
                Integer.parseInt(quantidade);
            } catch (NumberFormatException nfe) {
                return false;
            }
            int qtd = Integer.parseInt(quantidade);
            if(qtd > 9999 || qtd < 1) {
                return false;
            }
            return true;
        } else {
            return false;
        }
    }

    protected void avisos(String nome, String quantidade, boolean disponivel) {
        if(!disponivel) {
            Toast toast = Toast.makeText(ItemActivity.this, "Já existe um item com o nome escolhido", Toast.LENGTH_SHORT);
            toast.show();
        } else if(nome.length() <= 0 && quantidade.length() <= 0) {
            Toast toast = Toast.makeText(ItemActivity.this, "Preencha os campos Nome e Quantidade", Toast.LENGTH_SHORT);
            toast.show();
        } else if(nome.length() <= 0) {
            Toast toast = Toast.makeText(ItemActivity.this, "Preencha o campo nome", Toast.LENGTH_SHORT);
            toast.show();
        } else if(quantidade.length() <= 0) {
            Toast toast = Toast.makeText(ItemActivity.this, "Preencha o campo quantidade", Toast.LENGTH_SHORT);
            toast.show();
        } else {
            Toast toast = Toast.makeText(ItemActivity.this, "Quantidade inválida, MAX: 9999 MIN: 1", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

}
