package upv.dadm.cubatometro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import upv.dadm.cubatometro.Database.DAO;
import upv.dadm.cubatometro.Database.FirebaseIni;
import upv.dadm.cubatometro.Listeners.RegistrosListener;
import upv.dadm.cubatometro.entidades.Registro;
import upv.dadm.cubatometro.entidades.User;

public class ContadorActivity extends AppCompatActivity {
    private String groupID;
    TextView numBotellas;
    TextView numMediasBotellas;
    TextView numJarras;
    TextView numLitros;
    TextView numLatas;
    TextView numVino;
    TextView numChupitos;

    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contador);

        groupID = getSharedPreferences("groupDetails", MODE_PRIVATE).getString("groupID", "");
        mAuth = FirebaseAuth.getInstance();
        final String userID = mAuth.getCurrentUser().getUid();
        final ArrayList<String> miembrosID = getIntent().getStringArrayListExtra("miembrosID");
        Log.d("MIEMBROS", miembrosID.toString());

        numBotellas = findViewById(R.id.botellas_contador_textview);
        numMediasBotellas = findViewById(R.id.mediasbotellas_contador_textview);
        numJarras = findViewById(R.id.jarras_contador_textview);
        numLitros = findViewById(R.id.litros_contador_textview);
        numLatas = findViewById(R.id.latas_contador_textview);
        numVino = findViewById(R.id.vino_contador_textview);
        numChupitos = findViewById(R.id.chupitos_contador_textview);

        actualizarBotellas();
        actualizarChupitos();
        actualizarJarras();
        actualizarLatas();
        actualizarLitros();
        actualizarMediasBotellas();
        actualizarVino();

        mAuth = FirebaseAuth.getInstance();
        final DAO dao = new DAO();

        final Button actualizar = findViewById(R.id.actualizar_registro_button);
        actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(numBotellas.getText().toString().equals("0") || numVino.getText().toString().equals("0") || numChupitos.getText().toString().equals("0") || numJarras.getText().toString().equals("0") || numLatas.getText().toString().equals("0") || numMediasBotellas.getText().toString().equals("0") || numLitros.getText().toString().equals("0")){
                    Toast.makeText(ContadorActivity.this, "Alguno de los contadores es igual a 0", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d("PUNTOS", calcularPuntos() + "");
                    Registro registro = new Registro();
                    registro.setNumLitrosCerveza(Integer.parseInt(numLitros.getText().toString()));
                    registro.setNumMediasBotellas(Integer.parseInt(numMediasBotellas.getText().toString()));
                    registro.setNumLatasCerveza(Integer.parseInt(numLatas.getText().toString()));
                    registro.setNumJarrasCerveza(Integer.parseInt(numJarras.getText().toString()));
                    registro.setNumChupitos(Integer.parseInt(numChupitos.getText().toString()));
                    registro.setNumBotellasVino(Integer.parseInt(numVino.getText().toString()));
                    registro.setNumBotellas(Integer.parseInt(numBotellas.getText().toString()));
                    Log.d("REGISTRO", registro.getNumBotellas() + "");
                    assert miembrosID != null;
                    dao.setRegistro(miembrosID, userID, groupID, registro);
                }


                //startActivity(new Intent(ContadorActivity.this, RankingActivity.class));
            }


        });
    }

    public int calcularPuntos(){
        /*** Declarar los contadores como atributos de la clase??? La aplicación explota ***/
        int botellas = Integer.parseInt(numBotellas.getText().toString());
        int mediasBotellas = Integer.parseInt(numMediasBotellas.getText().toString());
        int jarras = Integer.parseInt(numJarras.getText().toString());
        int litros = Integer.parseInt(numLitros.getText().toString());
        int latas = Integer.parseInt(numLatas.getText().toString());
        int vino = Integer.parseInt(numVino.getText().toString());
        int chupitos = Integer.parseInt(numChupitos.getText().toString());

        return (botellas*20 + mediasBotellas*8 + jarras*2 + litros*3 + latas + vino*4 + chupitos);

    }

    public void inicializaContadores(){
        /*** Inicializar contadores con los valores del registro del usuario ***/
    }

    public void actualizarBotellas(){
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
