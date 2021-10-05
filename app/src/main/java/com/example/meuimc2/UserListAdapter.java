package com.example.meuimc2;

import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

//import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.ViewHolder> {
    private BancoDeDados bdCLASS = new BancoDeDados();
    private SQLiteDatabase imcDB;
    private List<ItemModel> userList;
    private Context context;
    public UserListAdapter(List<ItemModel> userList, Context context){
        this.userList=userList;
        this.context=context;
    }



    @Override
    public UserListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_design,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UserListAdapter.ViewHolder holder, int position) {
        int image=userList.get(position).getImcImage();
        double userPeso=userList.get(position).getUserPeso();
        double userIMC=userList.get(position).getUserIMC();
        String sampleDate=userList.get(position).getSampleDateStr();

        holder.setData(position,image,userPeso,userIMC,sampleDate);
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private int position;
        private ImageView imageView;
        private TextView textView1;
        private TextView textView2;
        private TextView textView3;

        public ViewHolder(View itemView) {
            super(itemView);

            imageView=itemView.findViewById(R.id.imgUserList);
            textView1=itemView.findViewById(R.id.txtPesoList);
            textView2=itemView.findViewById(R.id.txtIMCList);
            textView3=itemView.findViewById(R.id.txtDateList);

            itemView.findViewById(R.id.btnExcluirList).setOnClickListener(view -> {
                confirmaExcluir(userList.get(position).getUserId());
            });

        }

        public void setData(int position, int image, double userPeso, double userIMC, String sampleDate) {
            imageView.setImageResource(image);
            textView1.setText(formataTexto(userPeso,"","kg",1));
            textView2.setText(formataTexto(userIMC,"IMC: ","",1));
            textView3.setText(sampleDate);
            this.position=position;

        }
    }

    public void confirmaExcluir(int id) {
        AlertDialog.Builder msgBox = new AlertDialog.Builder(context);
        msgBox.setTitle("Excluir");
        msgBox.setIcon(android.R.drawable.ic_menu_delete);
        String coisa = "";//listarUm(id);
        msgBox.setMessage("Você realmente deseja excluir " + id +"?");
        msgBox.setPositiveButton("Sim", (dialogInterface, i) -> {
            int resultado = bdCLASS.onDelete(context, imcDB, "id = ?", id);
            if (resultado > 0) {
                Toast.makeText(context, "Registro apagado com sucesso " + resultado, Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(context, "Não foi possível apagar o registro", Toast.LENGTH_SHORT).show();
            }
        });
        msgBox.setNegativeButton("Não", (dialogInterface, i) -> {
        });
        msgBox.show();
    }


    String formataTexto(double valor, String texto, String unidade, int decimais){
        return String.format("%s %.0"+decimais+"f %s", texto, valor, unidade);
    }
}
