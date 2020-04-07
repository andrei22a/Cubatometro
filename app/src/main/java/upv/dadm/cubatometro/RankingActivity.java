package upv.dadm.cubatometro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

import upv.dadm.cubatometro.entidades.Ranking;
import upv.dadm.cubatometro.adapter.RankingAdapter;

public class RankingActivity extends AppCompatActivity {
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        /* Sustituir por llamada al método getGroupMembers() */
        ArrayList<Ranking> array = new ArrayList<>();
        array.add(new Ranking("1", "David", "0"));
        array.add(new Ranking("2", "Pepe", "0"));
        array.add(new Ranking("1", "David", "0"));
        array.add(new Ranking("2", "Pepe", "0"));


        recyclerView = findViewById(R.id.rankingmiembros_recyclerview_ranking);

        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);

        RankingAdapter adapter = new RankingAdapter(array);
        recyclerView.setAdapter(adapter);


    }
}
