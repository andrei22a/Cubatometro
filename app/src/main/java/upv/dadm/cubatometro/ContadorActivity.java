package upv.dadm.cubatometro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import upv.dadm.cubatometro.entidades.Registro;
import upv.dadm.cubatometro.entidades.User;

public class ContadorActivity extends AppCompatActivity {
    private ArrayList<Registro> data = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contador);

        actualizarBotellas();
        actualizarChupitos();
        actualizarJarras();
        actualizarLatas();
        actualizarLitros();
        actualizarMediasBotellas();
        actualizarVino();

        Button actualizar = findViewById(R.id.actualizar_registro_button);
        actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("PUNTOS", calcularPuntos() + "");
                Intent intent = new Intent(ContadorActivity.this, RankingActivity.class);
                startActivity(intent);
            }
        });

    }

    public int calcularPuntos(){
        /*** Declarar los contadores como atributos de la clase??? La aplicación explota ***/
        TextView numBotellas = findViewById(R.id.botellas_contador_textview);
        TextView numMediasBotellas = findViewById(R.id.mediasbotellas_contador_textview);
        TextView numJarras = findViewById(R.id.jarras_contador_textview);
        TextView numLitros = findViewById(R.id.litros_contador_textview);
        TextView numLatas = findViewById(R.id.latas_contador_textview);
        TextView numVino = findViewById(R.id.vino_contador_textview);
        TextView numChupitos = findViewById(R.id.chupitos_contador_textview);
        int botellas = Integer.parseInt(numBotellas.getText().toString());
        int mediasBotellas = Integer.parseInt(numMediasBotellas.getText().toString());
        int jarras = Integer.parseInt(numJarras.getText().toString());
        int litros = Integer.parseInt(numLitros.getText().toString());
        int latas = Integer.parseInt(numLatas.getText().toString());
        int vino = Integer.parseInt(numVino.getText().toString());
        int chupitos = Integer.parseInt(numChupitos.getText().toString());

        return (botellas*20 + mediasBotellas*8 + jarras*2 + litros*3 + latas*1 + vino*4 + chupitos*1);

    }

    public void inicializaContadores(){
        /*** Inicializar contadores con los valores del registro del usuario ***/
    }

    public void actualizarBotellas(){
        final TextView numBotellas = findViewById(R.id.botellas_contador_textview);
        final int valorActual = Integer.parseInt(numBotellas.getText().toString());
        ImageButton anyadir = findViewById(R.id.botellas_añadir_button);
        anyadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numBotellas.setText(Integer.parseInt(numBotellas.getText().toString()) + 1 + "");
            }
        });

        ImageButton quitar = findViewById(R.id.botellas_quitar_button);
        quitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Integer.parseInt(numBotellas.getText().toString()) != valorActual) {
                    numBotellas.setText(Integer.parseInt(numBotellas.getText().toString()) - 1 + "");
                }
            }
        });
    }

    public void actualizarMediasBotellas(){
        final TextView numMediasBotellas = findViewById(R.id.mediasbotellas_contador_textview);
        final int valorActual = Integer.parseInt(numMediasBotellas.getText().toString());
        ImageButton anyadir = findViewById(R.id.mediasbotellas_añadir_button);
        anyadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numMediasBotellas.setText(Integer.parseInt(numMediasBotellas.getText().toString()) + 1 + "");
            }
        });

        ImageButton quitar = findViewById(R.id.mediasbotellas_quitar_button);
        quitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Integer.parseInt(numMediasBotellas.getText().toString()) != valorActual) {
                    numMediasBotellas.setText(Integer.parseInt(numMediasBotellas.getText().toString()) - 1 + "");
                }
            }
        });
    }

    public void actualizarJarras(){
        final TextView numJarras = findViewById(R.id.jarras_contador_textview);
        final int valorActual = Integer.parseInt(numJarras.getText().toString());
        ImageButton anyadir = findViewById(R.id.jarras_añadir_button);
        anyadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numJarras.setText(Integer.parseInt(numJarras.getText().toString()) + 1 + "");
            }
        });

        ImageButton quitar = findViewById(R.id.jarras_quitar_button);
        quitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Integer.parseInt(numJarras.getText().toString()) != valorActual) {
                    numJarras.setText(Integer.parseInt(numJarras.getText().toString()) - 1 + "");
                }
            }
        });
    }

    public void actualizarLitros(){
        final TextView numLitros = findViewById(R.id.litros_contador_textview);
        final int valorActual = Integer.parseInt(numLitros.getText().toString());
        ImageButton anyadir = findViewById(R.id.litros_añadir_button);
        anyadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numLitros.setText(Integer.parseInt(numLitros.getText().toString()) + 1 + "");
            }
        });

        ImageButton quitar = findViewById(R.id.litros_quitar_button);
        quitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Integer.parseInt(numLitros.getText().toString()) != valorActual) {
                    numLitros.setText(Integer.parseInt(numLitros.getText().toString()) - 1 + "");
                }
            }
        });
    }

    public void actualizarLatas(){
        final TextView numLatas = findViewById(R.id.latas_contador_textview);
        final int valorActual = Integer.parseInt(numLatas.getText().toString());
        ImageButton anyadir = findViewById(R.id.latas_añadir_button);
        anyadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numLatas.setText(Integer.parseInt(numLatas.getText().toString()) + 1 + "");
            }
        });

        ImageButton quitar = findViewById(R.id.latas_quitar_button);
        quitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Integer.parseInt(numLatas.getText().toString()) != valorActual) {
                    numLatas.setText(Integer.parseInt(numLatas.getText().toString()) - 1 + "");
                }
            }
        });
    }

    public void actualizarVino(){
        final TextView numVino = findViewById(R.id.vino_contador_textview);
        final int valorActual = Integer.parseInt(numVino.getText().toString());
        ImageButton anyadir = findViewById(R.id.vino_añadir_button);
        anyadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numVino.setText(Integer.parseInt(numVino.getText().toString()) + 1 + "");
            }
        });

        ImageButton quitar = findViewById(R.id.vino_quitar_button);
        quitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Integer.parseInt(numVino.getText().toString()) != valorActual) {
                    numVino.setText(Integer.parseInt(numVino.getText().toString()) - 1 + "");
                }
            }
        });
    }

    public void actualizarChupitos(){
        final TextView numChupitos = findViewById(R.id.chupitos_contador_textview);
        final int valorActual = Integer.parseInt(numChupitos.getText().toString());
        ImageButton anyadir = findViewById(R.id.chupitos_añadir_button);
        anyadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numChupitos.setText(Integer.parseInt(numChupitos.getText().toString()) + 1 + "");
            }
        });

        ImageButton quitar = findViewById(R.id.chupitos_quitar_button);
        quitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Integer.parseInt(numChupitos.getText().toString()) != valorActual) {
                    numChupitos.setText(Integer.parseInt(numChupitos.getText().toString()) - 1 + "");
                }
            }
        });
    }

}
