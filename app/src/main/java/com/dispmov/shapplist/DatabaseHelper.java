package com.dispmov.shapplist;

import android.content.ContentValues;
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
        ContentValues cv = new ContentValues();
        cv.put(KEY_NOME, nome);
        cv.put(KEY_QTD, quantidade);
        db.insert(TABLE_NAME, null, cv);
        db.close();
    }

    public void alterarItem(String nome, int quantidade) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(KEY_NOME, nome);
        cv.put(KEY_QTD, quantidade);
        db.update(TABLE_NAME, cv, KEY_NOME + " = ?", new String[]{nome});
        db.close();
    }

    public void removerItem(String nome) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, KEY_NOME + " = ?", new String[]{nome});
        db.close();
    }

    public Cursor getDados() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{KEY_ID, KEY_NOME, KEY_QTD}, null, null, null, null, null);
        return cursor;
    }

    public boolean verificarExistencia(String nome) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{KEY_NOME}, KEY_NOME + " = ?", new String[]{nome}, null, null, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            db.close();
            return false;
        }
        cursor.close();
        db.close();
        return true;
    }

}
