package upv.dadm.cubatometro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import upv.dadm.cubatometro.Database.DAO;
import upv.dadm.cubatometro.Listeners.MiembrosConRegistroListener;
import upv.dadm.cubatometro.entidades.Grupo;
import upv.dadm.cubatometro.entidades.Ranking;
import upv.dadm.cubatometro.adapter.RankingAdapter;
import upv.dadm.cubatometro.entidades.Registro;
import upv.dadm.cubatometro.entidades.User;

public class RankingActivity extends AppCompatActivity {
    private ListView listView;
    private RankingAdapter adapter;
    private ArrayList<Ranking> data = new ArrayList<>();
    private DAO dao = new DAO();
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);
        Log.d("GROUP ID", getIntent().getStringExtra("groupID"));
        mAuth = FirebaseAuth.getInstance();

        /*** Obtener los miembros del grupo y guardarlos en el array members ***/
        dao.getMiembrosConRegistros(mAuth.getCurrentUser().getUid(), getIntent().getStringExtra("groupID"), new MiembrosConRegistroListener() {
            @Override
            public void onMiembrosReceived(List<User> miembros) {
                for(User user : miembros){
                    int puntos = calcularPuntos(user);
                    data.add(new Ranking(user.getUsername(), puntos));
                }
            }

            @Override
            public void onError(Throwable error) {

            }
        });
        /*** Obtener los registros de cada miembro y añadir al array data los nombres de los miembros y su puntuación cada 20 segundos ***/
        /*new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

            }
        },0, 20000);*/

        listView = findViewById(R.id.rankingmiembros_listview_ranking);
        Collections.sort(data); // Ordenar el array descendentemente
        adapter = new RankingAdapter(data, this);
        listView.setAdapter(adapter);

        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_update_registro:
                startActivity(new Intent(RankingActivity.this, ContadorActivity.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    public int calcularPuntos(User user){
        int res = 0;
        for (Registro registro : user.getRegistros()){
            int botellas = registro.getNumBotellas();
            int mediasBotellas = registro.getNumMediasBotellas();
            int jarras = registro.getNumJarrasCerveza();
            int litros = registro.getNumLitrosCerveza();
            int latas = registro.getNumLatasCerveza();
            int vino = registro.getNumBotellasVino();
            int chupitos = registro.getNumChupitos();
            res = botellas*20 + mediasBotellas*8 + jarras*2 + litros*3 + latas*1 + vino*4 + chupitos*1;
        }

        return res;
    }
}
