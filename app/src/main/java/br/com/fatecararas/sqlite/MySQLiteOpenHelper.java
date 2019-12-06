package br.com.fatecararas.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;

public class MySQLiteOpenHelper extends SQLiteOpenHelper {

    private static final String BANCO = "dicionario.db";
    private static final String TABELA = "dicionario";
    private static final String CAMPO_PALAVRA = "palavra";
    private static final String CAMPO_DEFINICAO = "definicao";
    private static final int VERSAO = 1;

    public MySQLiteOpenHelper(@Nullable Context context)
    {
        super(context, BANCO, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "  + TABELA + "(_id integer primary key, " + CAMPO_PALAVRA +" TEXT," + CAMPO_DEFINICAO +" TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DELETE FROM "+TABELA);
        onCreate(db);
    }

    public long inserirRegistro(String palavra, String definicao) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CAMPO_PALAVRA, palavra);
        values.put(CAMPO_DEFINICAO, definicao);
        return sqLiteDatabase.insert(TABELA, null, values);
    }

    public String buscarDefinicaoPalavra(long id) {
        String resultado = "";
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT definicao FROM " + TABELA + " WHERE _id = ?",
                new String[]{String.valueOf(id)});
        if (cursor.getCount() == 1) {
            cursor.moveToFirst();
            resultado = cursor.getString(0);
        }
        return resultado;
    }

    public int atualizarRegistro(long id, String word, String definition) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("_id", id);
        values.put(CAMPO_PALAVRA, word);
        values.put(CAMPO_DEFINICAO, definition);
        return db.update(TABELA, values, "_id = ?",
                new String[]{String.valueOf(id)});
    }

    public int apagarRegistro(long id) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.delete(TABELA, "_id = ?",
                new String[]{String.valueOf(id)});
    }

    public long buscarIdPalavra(String palavra) {
        long resultado = -1;
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT _id FROM " + TABELA
                + " WHERE " + CAMPO_PALAVRA + " = ?", new String[]{palavra});
        Log.i("buscarIdPalavra","getCount()="+cursor.getCount());
        if (cursor.getCount() == 1) {
            cursor.moveToFirst();
            resultado = cursor.getInt(0);
        }
        return resultado;
    }

    public void salvarRegistro(String palavra, String definition){
        long id = buscarIdPalavra(palavra);
        if (id>0) {
            atualizarRegistro(id, palavra,definition);
        } else {
            inserirRegistro(palavra,definition);
        }
    }

    public Cursor buscarTodosRegistros() {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String query = "SELECT _id, " + CAMPO_PALAVRA + " FROM "
                + TABELA + " ORDER BY " + CAMPO_PALAVRA + " ASC";
        return sqLiteDatabase.rawQuery(query, null);
    }

}
