package com.thfona.listacompras;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "lista.db";
    private static final String TABLE_NAME = "Lista";
    private static final String KEY_NOME = "Nome";
    private static final String KEY_QTD = "Quantidade";
    private static final String CREATE_LISTA_TABLE = "CREATE TABLE " + TABLE_NAME + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_NOME + " TEXT, " + KEY_QTD +" INTEGER);";
    private static final String DROP_LISTA_TABLE = "DROP TABLE " + TABLE_NAME;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_LISTA_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void create() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(CREATE_LISTA_TABLE);
    }

    public void drop() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(DROP_LISTA_TABLE);
    }

    public void adicionarItem(String nome, int quantidade) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("INSERT INTO Lista VALUES (_ROWID_, " + nome + ", " + quantidade + ")");
    }

    public void removerItem(String nome) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, "Nome=?" , new String[]{nome});
    }

    public Cursor getDados() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor qry = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return qry;
    }

}
