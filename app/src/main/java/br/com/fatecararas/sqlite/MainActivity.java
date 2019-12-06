package br.com.fatecararas.sqlite;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText mEditTextPalavra;
    EditText mEditTextDefinicao;
    MySQLiteOpenHelper mDB;
    ListView mListView;
    List<Dicionario> mDataSource = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        mEditTextPalavra = findViewById(R.id.textInputPalavra);
        mEditTextDefinicao = findViewById(R.id.textInputSignificado);
        mListView = findViewById(R.id.listViewDicionario);

        mDB = new MySQLiteOpenHelper(this);

        exibirDadosEmLista();

        Button btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveRecord();
                Snackbar.make(view, "Registro adicionado com sucesso!", Snackbar.LENGTH_LONG).show();
            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("Position/ID: ",position + " - "+id);
                Toast.makeText(MainActivity.this,
                        mDB.buscarDefinicaoPalavra(id).toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });

        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this,
                     "Records deleted = " + mDB.apagarRegistro(id),Toast.LENGTH_SHORT).show();
                exibirDadosEmLista();
                return true;
            }
        });

    }

    public void exibirDadosEmLista(){
        mDataSource.clear();

        SQLiteDatabase sqLiteDatabase = mDB.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM dicionario",null);

        if (cursor.moveToFirst()){
            do {
                Dicionario dicionario = new Dicionario();
                dicionario.setId(cursor.getInt(cursor.getColumnIndex("_id")));
                dicionario.setPalavra(cursor.getString(cursor.getColumnIndex("palavra")));
                dicionario.setDefinicao(cursor.getString(cursor.getColumnIndex("definicao")));
                mDataSource.add(dicionario);
            }while (cursor.moveToNext());
        }

        SQLiteAdapter sqLiteAdapter =
                new SQLiteAdapter(MainActivity.this, mDataSource);
        sqLiteAdapter.notifyDataSetChanged();
        mListView.setAdapter(sqLiteAdapter);
        cursor.close();
    }

    private void saveRecord() {
        String palavra = mEditTextPalavra.getText().toString();
        String definicao = mEditTextDefinicao.getText().toString();
        mDB.salvarRegistro(palavra, definicao);
        mEditTextPalavra.setText("");
        mEditTextDefinicao.setText("");
        exibirDadosEmLista();
    }



}
