package com.dispmov.shapplist;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "lista.db";
    private static final String TABLE_NAME = "Lista";
    private static final String KEY_ID = "_id";
    private static final String KEY_NOME = "Nome";
    private static final String KEY_QTD = "Quantidade";
    private static final String CREATE_LISTA_TABLE = "CREATE TABLE " + TABLE_NAME + " (" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_NOME + " TEXT, " + KEY_QTD +" INTEGER);";

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

    public void adicionarItem(String nome, int quantidade) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "INSERT INTO " + TABLE_NAME + " (" + KEY_NOME + ", " + KEY_QTD + ") " + "VALUES ('" + nome + "', " + quantidade + ")";
        db.execSQL(query);
    }

    public void alterarItem(String nome, int quantidade) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + KEY_NOME + " = '" + nome + "', " + KEY_QTD + " = " + quantidade + " WHERE " + KEY_NOME + " = '" + nome + "'";
        db.execSQL(query);
    }

    public void removerItem(String nome) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, "nome=?" , new String[]{nome});
    }

    public Cursor getDados() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + KEY_ID + ", " + KEY_NOME + ", " + KEY_QTD + " FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }

    public boolean verificarExistencia(String nome) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + KEY_NOME + " FROM " + TABLE_NAME + " WHERE " + KEY_NOME + " = '" + nome + "'";
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

}
