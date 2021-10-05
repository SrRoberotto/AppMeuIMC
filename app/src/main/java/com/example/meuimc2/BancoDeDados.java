package com.example.meuimc2;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class BancoDeDados {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "imcDB.db";
    private static final String TABLE_NAME = "imcTable";
    private static final String SQL_SELECT_ALL = "SELECT * FROM "+TABLE_NAME;
    private static final String SQL_SELECT_ID = "SELECT * FROM "+TABLE_NAME+" WHERE id = ?";
    /* SQL de criação do banco de dados. */
    private static final String SQL_CREATE = "CREATE TABLE IF NOT EXISTS "+TABLE_NAME+"(id INTEGER PRIMARY KEY AUTOINCREMENT, peso REAL, imc REAL, data TEXT)";

    public void onCreate(Context ctx,SQLiteDatabase db){
        //openOrCreateDatabase --> Cria ou Abre banco de dados
        //(nome.db,permissão (modo), ...)
        // MODE_PRIVATE --> Priva o acesso do banco para somente aplicação
        db = ctx.openOrCreateDatabase(this.DATABASE_NAME,Context.MODE_PRIVATE, null);
        db.execSQL(this.SQL_CREATE); //Criando tabela caso não exista!!
        db.close();
    }

    public void onUpgrade(Context ctx,SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + this.TABLE_NAME);

        // Create tables again
        onCreate(ctx,db);
    }

    public long onWrite(Context ctx,SQLiteDatabase db,String row,ContentValues ctv){
        db = ctx.openOrCreateDatabase(this.DATABASE_NAME,Context.MODE_PRIVATE,null);
        long lg = db.insert(this.TABLE_NAME,row,ctv);
        db.close();
        return lg;
    }

    public int onUpdate(Context ctx,SQLiteDatabase db,ContentValues ctv,String row,int id){
        db = ctx.openOrCreateDatabase(this.DATABASE_NAME,Context.MODE_PRIVATE,null);
        int x = db.update(this.TABLE_NAME, ctv, row, new String[]{String.valueOf(id)});
        db.close();
        return x;
    }

    public Cursor onSelecAll(Context ctx,SQLiteDatabase db){
        db = ctx.openOrCreateDatabase(this.DATABASE_NAME,Context.MODE_PRIVATE,null);
        Cursor cursor = db.rawQuery(this.SQL_SELECT_ALL, null);
        return cursor;
    }

    public Cursor onSelecId(Context ctx,SQLiteDatabase db,int id){
        db = ctx.openOrCreateDatabase(this.DATABASE_NAME,Context.MODE_PRIVATE,null);
        Cursor cursor = db.rawQuery(SQL_SELECT_ID, new String[]{String.valueOf(id)});
        return cursor;
    }

    public int onDelete(Context ctx,SQLiteDatabase db,String row,int id){
        db = ctx.openOrCreateDatabase(this.DATABASE_NAME,Context.MODE_PRIVATE,null);
        int x = db.delete(this.TABLE_NAME, row, new String[]{String.valueOf(id)});
        db.close();
        return x;
    }

    public void onClose(Context ctx,SQLiteDatabase db){
        db = ctx.openOrCreateDatabase(this.DATABASE_NAME,Context.MODE_PRIVATE,null);
        db.close();
    }

}
