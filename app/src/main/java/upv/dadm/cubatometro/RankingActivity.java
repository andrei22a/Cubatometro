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
import upv.dadm.cubatometro.Fragments.ScoreFragment;
import upv.dadm.cubatometro.Listeners.MiembrosConRegistroListener;
import upv.dadm.cubatometro.entidades.Grupo;
import upv.dadm.cubatometro.entidades.Ranking;
import upv.dadm.cubatometro.adapter.RankingAdapter;
import upv.dadm.cubatometro.entidades.Registro;
import upv.dadm.cubatometro.entidades.User;

public class RankingActivity extends AppCompatActivity {
    private String groupID;
    private RecyclerView recyclerView;
    private RankingAdapter adapter;
    private ArrayList<Ranking> data = new ArrayList<>();
    private DAO dao = new DAO();
    private FirebaseAuth mAuth;
    private ArrayList<String> miembrosID = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        groupID = getSharedPreferences("groupDetails", MODE_PRIVATE).getString("groupID", "");
        mAuth = FirebaseAuth.getInstance();
        recyclerView = findViewById(R.id.rankingmiembros_listview_ranking);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        data.clear();

        /*** Obtener los miembros del grupo y guardarlos en el array members ***/

        /*** Obtener los registros de cada miembro y añadir al array data los nombres de los miembros y su puntuación cada 20 segundos ***/
        dao.getMiembrosConRegistros(mAuth.getCurrentUser().getUid(), groupID, new MiembrosConRegistroListener() {
            @Override
            public void onMiembrosReceived(List<User> miembros) {
                miembrosID.clear();
                data.clear();
                Log.d("DATA RANKING", data.toString());
                for(User user : miembros){
                    int puntos = calcularPuntos(user);
                    String userID = user.getUserID();
                    miembrosID.add(userID);
                    Log.d("miembrosID", miembrosID.toString());
                    data.add(new Ranking(user.getUsername(), puntos, userID, user.getProfilePic()));
                }
                Collections.sort(data);
                Log.d("DATA RANKING", data.toString());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Throwable error) {

            }
        });

        adapter = new RankingAdapter(data, getApplicationContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(RankingActivity.this, GroupsActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_ranking, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_actualizar_registros:
                Intent registrosIntent = new Intent(RankingActivity.this, ContadorActivity.class);
                registrosIntent.putStringArrayListExtra("miembrosID", miembrosID);
                startActivity(registrosIntent);
                return true;
            case R.id.menu_view_stadistics:
                Intent stadisticsIntent = new Intent(RankingActivity.this, StadisticsActivity.class);
                startActivity(stadisticsIntent);
                return true;
            case R.id.menu_info_bebidas:
                ScoreFragment myScoreFragment = new ScoreFragment();
                myScoreFragment.show(getSupportFragmentManager(), "Score Fragment");
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
            res += botellas*20 + mediasBotellas*8 + jarras*2 + litros*3 + latas + vino*4 + chupitos;
        }

        return res;
    }
}
