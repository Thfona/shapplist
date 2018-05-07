package com.dispmov.shapplist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
            initConfirmarEditarListener(conteudo);
        } else {
            initConfirmarAdicionarListener();
        }

        initCancelarListener();

    }

    public void initConfirmarAdicionarListener() {
        Button btnCfm = findViewById(R.id.confirmarBtn);
        btnCfm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText edtNome = findViewById(R.id.nomeEdt);
                EditText edtQtd = findViewById(R.id.qtdEdt);
                String nomeInserido = edtNome.getText().toString();
                String qtdInserida = edtQtd.getText().toString();
                boolean disponibilidade = !dbHelper.verificarExistencia(nomeInserido);
                if(parametrosAtendemCondicoes(nomeInserido, qtdInserida, disponibilidade)) {
                    int qtdInt = Integer.parseInt(qtdInserida);
                    dbHelper.adicionarItem(nomeInserido, qtdInt);
                    Intent intent = new Intent(ItemActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    mensagens(nomeInserido, qtdInserida, disponibilidade);
                }
            }
        });
    }

    public void initConfirmarEditarListener(String[] conteudo) {
        Button btnCfm = findViewById(R.id.confirmarBtn);
        btnCfm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

    public boolean parametrosAtendemCondicoes(String nome, String quantidade, boolean disponivel) {
        if(nome.length() > 0 && nome.length() <= 10 && quantidade.length() > 0 && disponivel) {
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

    public void mensagens(String nome, String quantidade, boolean disponivel) {
        if(!disponivel) {
            Toast toast = Toast.makeText(ItemActivity.this, "Já existe um item com o nome escolhido", Toast.LENGTH_SHORT);
            toast.show();
        } else if(nome.length() <= 0 && quantidade.length() <= 0) {
            Toast toast = Toast.makeText(ItemActivity.this, "Preencha os campos Nome e Quantidade", Toast.LENGTH_SHORT);
            toast.show();
        } else if(nome.length() <= 0) {
            Toast toast = Toast.makeText(ItemActivity.this, "Preencha o campo nome", Toast.LENGTH_SHORT);
            toast.show();
        } else if(nome.length() > 10) {
            Toast toast = Toast.makeText(ItemActivity.this, "Nome Inválido, MAX: 10 caracteres", Toast.LENGTH_SHORT);
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
