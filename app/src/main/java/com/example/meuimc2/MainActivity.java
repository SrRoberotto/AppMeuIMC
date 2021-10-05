package com.example.meuimc2;

import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
//    private static final String DB_NAME ="imcDB";
//    private static final String TABLE_IMC ="imcTable";
//
//    private SQLiteDatabase dbInstance;
//    dbInstance = openOrCreateDatabase(DB_NAME, null);
//    public boolean dbStatus = false;
    private BancoDeDados bdCLASS = new BancoDeDados();
    private SQLiteDatabase imcDB;
    private Context context;

    private int pesoMininimo = 50, alturaMinima = 30;
    String strPeso = "Insira seu peso: ", undPeso = "Kg";
    String strAltura = "Insira sua altura: ", undAltura = "m";
    String strIdeal = "Peso ideal: ", undIdeal = "Kg";

//    bancoSQLite bancoDados = new bancoSQLite(getApplicationContext());
//    int[] cores = {Color.BLACK,Color.CYAN,Color.BLUE,Color.GREEN,Color.YELLOW,Color.DKGRAY,Color.RED};

    int peso = 750;
    int altura = 170;
    double pesoKG = 75.0;
    double alturaMetros = 1.7;
    double imc = 26;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // inciciar views
        final SeekBar skbPeso= findViewById(R.id.skbPeso);
        final SeekBar skbAltura=findViewById(R.id.skbAltura);
//        final RatingBar rtbIMC = findViewById(R.id.rtbIMC);
        final TextView txtPeso=findViewById(R.id.txtPeso);
        final TextView txtAltura=findViewById(R.id.txtAltura);
        final TextView txtPesoIdeal=findViewById(R.id.txtPesoIdeal);

        final Button btnCalcular =  findViewById(R.id.btnCalcular);
        final Button btnGuardar = findViewById(R.id.btnGuardar);
        final Button btnListar = findViewById(R.id.btnListar);

        context = getBaseContext(); //Context para usar na classe BancoDeDados
        bdCLASS.onCreate(context, imcDB); // Criar BD e tabela caso sejam necessários!!

        btnCalcular.setOnClickListener(view -> {
            alturaMetros = (double) altura / 100;
            pesoKG = (float) peso / 10;
            imc = pesoKG / (alturaMetros * alturaMetros);

            double imcIdeal = 22 * (alturaMetros * alturaMetros);

            atualizaIMC(imc);

            txtPesoIdeal.setVisibility(View.VISIBLE);
            atualizaLabel (imcIdeal,strIdeal,undIdeal,txtPesoIdeal);
        });

        btnGuardar.setOnClickListener(view -> {
            ContentValues valores = new ContentValues();
            valores.put("peso", pesoKG);
            valores.put("imc", imc);
            valores.put("data", "10/10/2020");

            long resultado = bdCLASS.onWrite(context,imcDB,null,valores);

            if (resultado>0){
                Toast.makeText(getApplicationContext(), "Peso e IMC gravados com sucesso " + resultado, Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getApplicationContext(), "Não foi possível gravar os dados", Toast.LENGTH_SHORT).show();
            }
        });

        btnListar.setOnClickListener(view -> {
                Intent intent = new Intent(MainActivity.this, listagem2.class);
                startActivity(intent);
        });

        // Método chamado ao deslizar a barra de peso
        skbPeso.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                peso = progress + pesoMininimo;
                float pesoKG = (float) peso / 10;

                atualizaLabel(pesoKG, strPeso,undPeso,txtPeso);
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }
        });

        // Método chamado ao deslizar a barra de altura
        skbAltura.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                altura = progress + alturaMinima;
                double alturaMetros = (double) altura / 100;
                atualizaLabel(alturaMetros, strAltura,undAltura,txtAltura,2);
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }
        });

        // Valores iniciais
        atualizaLabel(peso/10, strPeso,undPeso,txtPeso);
        atualizaLabel((float) altura/100, strAltura,undAltura,txtAltura,2);
        atualizaIMC(0);
    }

    // Método usado para atualizar os valores na tela
    void atualizaLabel(double valor, String texto, String unidade, TextView objeto){
        atualizaLabel (valor,texto,unidade,objeto,1);
    }
    void atualizaLabel(double valor, String texto, String unidade, TextView objeto, int decimais){
        objeto.setText(String.format("%s %.0"+decimais+"f %s", texto, valor, unidade));
    }

    void atualizaIMC(double imc){
        final TextView txtResultado=findViewById(R.id.txtResultado);
        final TextView txtResultadoTexto=findViewById(R.id.txtResultadoTexto);

        txtResultado.setText(String.format("%.1f", imc));

        String texto="";
        double estrelasBase = 5.0;
        double subEstrela = 0.0;

        if (imc == 0) {
            texto = "Não calculado";
            estrelasBase = 0;
        } else if(imc < 18.5){
            texto = "Magreza";
            estrelasBase = 1;
            subEstrela = (imc-0) / 9;
        } else if (imc < 25){
            texto = "Normal";
            estrelasBase = 3;
            subEstrela = ((imc - 18.5) / 5);
        } else if (imc < 30){
            texto = "Sobrepeso";
            estrelasBase = 5;
            subEstrela = (imc - 30) / 10;
        } else if (imc <= 40){
            texto = "Obesidade";
            estrelasBase = 6;
            subEstrela = (imc - 40) / 10;
        } else if (imc > 40) {
            texto = "Obesidade Grave";
            estrelasBase = 7;
            subEstrela = (imc - 50) / 10;
        }

        txtResultadoTexto.setText(texto);
        setRating(estrelasBase + subEstrela);
    }

    //calcula as estrelas
    void setRating(double valor){
        final RatingBar rtbIMC = findViewById(R.id.rtbIMC);

        rtbIMC.setRating((float) valor);

    }

}
