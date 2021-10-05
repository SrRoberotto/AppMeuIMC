package com.example.meuimc2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class listagem2 extends AppCompatActivity {
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    List<ItemModel> imcList;
    ArrayList<Integer> arrayIDs;
    UserListAdapter adapter;

    List<ItemModel> userList;

    private BancoDeDados bdCLASS = new BancoDeDados();
    private SQLiteDatabase imcDB;
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listagem2);

        context = getBaseContext();

        listarDados();
        initRecyclerView();
    }

//    private void initData() {
//        userList=new ArrayList<>();
//        userList.add(new ItemModel(R.drawable.magreza, 63.5,10,"10/10/2020"));
//        userList.add(new ItemModel(R.drawable.normal, 63.5,10,"10/10/2020"));
//        userList.add(new ItemModel(R.drawable.sobrepeso, 63.5,10,"10/10/2020"));
//        userList.add(new ItemModel(R.drawable.obesidade1, 63.5,10,"10/10/2020"));
//
//
//    }

    private void initRecyclerView() {
        recyclerView=findViewById(R.id.recyclerView);
        layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new UserListAdapter(imcList, this); //userList
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void listarDados(){
        try {
            Cursor meuCursor = bdCLASS.onSelecAll(context, imcDB);
            imcList = new ArrayList<>();
//            arrayIDs = new ArrayList<>();

            int x = 1;
            meuCursor.moveToFirst();
            while (meuCursor!=null) {
//            arrayIDs.add(meuCursor.getInt(0));
                imcList.add(new ItemModel(
                        meuCursor.getInt(0),
                        R.drawable.normal,
                        meuCursor.getFloat(1),
                        meuCursor.getFloat(2),
                        meuCursor.getString(3)));
                x++;
                meuCursor.moveToNext();
            }
            bdCLASS.onClose(context, imcDB);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}