package upv.dadm.cubatometro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;

import upv.dadm.cubatometro.Database.DAO;
import upv.dadm.cubatometro.entidades.Ranking;
import upv.dadm.cubatometro.adapter.RankingAdapter;
import upv.dadm.cubatometro.entidades.Registro;
import upv.dadm.cubatometro.entidades.User;

public class RankingActivity extends AppCompatActivity {
    private ListView listView;
    private RankingAdapter adapter;
    private ArrayList<Ranking> data = new ArrayList<>();
    private ArrayList<User> members = new ArrayList<>();
    private ArrayList<Registro> registro = new ArrayList<>();
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        /*** Obtener los miembros del grupo y guardarlos en el array members ***/

        /*** Obtener los registros de cada miembro y añadir al array data los nombres de los miembros y su puntuación cada 20 segundos ***/
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

            }
        },0, 20000);

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
}
