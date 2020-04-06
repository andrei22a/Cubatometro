package upv.dadm.cubatometro;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

import upv.dadm.cubatometro.R;

public class RankingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        ArrayList<HashMap<String, String>> array = new ArrayList<>();

        HashMap<String, String> item = new HashMap<>();
        item.put("name", "David");
        item.put("points", "100");
        array.add(item);


        Log.d("ARRAY CONTENTS", array.toString());


        ListView lv = findViewById(R.id.rankingmiembros_listview_ranking);
        SimpleAdapter adapter = new SimpleAdapter(
                this, array, android.R.layout.simple_list_item_2, new String[]{"name", "points"}, new int[]{android.R.id.text1, android.R.id.text2}
        );

        lv.setAdapter(adapter);

    }
}
