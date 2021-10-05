package com.example.meuimc2;

import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

public class bancoSQLite {
    private static final String DB_NAME ="imcDB";
    private static final String TABLE_IMC ="imcTable";

    private SQLiteDatabase dbInstance;
    private boolean dbStatus = false;

    private Context context;

    bancoSQLite(Context context){
        this.context=context;

        this.dbInstance = openOrCreateDatabase(DB_NAME,null);
        this.dbStatus = this.criarBancoDados();

    }

    private boolean criarBancoDados(){
        try {
            this.dbInstance.execSQL("CREATE TABLE IF NOT EXISTS "+TABLE_IMC+"(id INTEGER PRIMARY KEY AUTOINCREMENT, peso REAL, imc REAL, data TEXT)");
//            this.dbInstance.close();
            return true;
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    private void listarDados() {
        try {
            Cursor meuCursor = this.dbInstance.rawQuery("SELECT * FROM " +TABLE_IMC, null);

            ArrayList<String> linhas = new ArrayList<String>();
//            arrayIDs = new ArrayList<>();
//            ArrayAdapter meuAdapter = new ArrayAdapter<String>(
//                    this,
//                    android.R.layout.simple_list_item_1,
//                    android.R.id.text1,
//                    linhas
//            );
//
//            listViewDados.setAdapter(meuAdapter);
//            meuCursor.moveToFirst();
//            while (meuCursor!=null){
//                arrayIDs.add(meuCursor.getInt(0));
//                linhas.add(meuCursor.getString(1));
//                meuCursor.moveToNext();
//            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private String listarUm(Integer id) {
        try {
            dbInstance =openOrCreateDatabase(DB_NAME, null);
            Cursor meuCursor = dbInstance.rawQuery("SELECT id, nome FROM coisa WHERE id = "+id, null);

            meuCursor.moveToFirst();
            if (meuCursor!=null){
                return meuCursor.getString(1);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }

    private void excluir(int id) {
        //Toast.makeText(this, Integer.toString(position),Toast.LENGTH_SHORT).show();
        try {
            dbInstance =openOrCreateDatabase(DB_NAME, null);
            String sql = "DELETE FROM coisa WHERE id = ?";

            SQLiteStatement smtm = dbInstance.compileStatement(sql);
            smtm.bindLong(1,id);
            smtm.executeUpdateDelete();
            listarDados();

            dbInstance.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private boolean cadastrar(double peso, double imc, String data) {
            try {
                String sql = "INSERT INTO "+TABLE_IMC+" (peso,imc,data) VALUES (?, ?, ?)";
                SQLiteStatement sttm = this.dbInstance.compileStatement(sql);

                sttm.bindDouble(1,peso);
                sttm.bindDouble(2,imc);
                sttm.bindString(3,data);
                sttm.executeInsert();

                this.dbInstance.close();

                return true;
//                finish();
            }catch (Exception e){
                e.printStackTrace();
            }
            return false;
    }

    private static String formatDateTime(Context context, String timeToFormat) {

        String finalDateTime = "";

        SimpleDateFormat iso8601Format = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");

        Date date = null;
        if (timeToFormat != null) {
            try {
                date = iso8601Format.parse(timeToFormat);
            } catch (ParseException e) {
                date = null;
            }

            if (date != null) {
                long when = date.getTime();
                int flags = 0;
                flags |= android.text.format.DateUtils.FORMAT_SHOW_TIME;
                flags |= android.text.format.DateUtils.FORMAT_SHOW_DATE;
                flags |= android.text.format.DateUtils.FORMAT_ABBREV_MONTH;
                flags |= android.text.format.DateUtils.FORMAT_SHOW_YEAR;

                finalDateTime = android.text.format.DateUtils.formatDateTime(context,
                        when + TimeZone.getDefault().getOffset(when), flags);
            }
        }
        return finalDateTime;
    }
}
