package upv.dadm.cubatometro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;

import upv.dadm.cubatometro.Lib.OnItemClickListener;
import upv.dadm.cubatometro.Lib.OnItemLongClickListener;
import upv.dadm.cubatometro.adapter.ListGroupsAdapter;
import upv.dadm.cubatometro.entidades.Grupo;

public class GroupsActivity extends AppCompatActivity {
    private ListGroupsAdapter adapter;
    private RecyclerView recyclerView;
    private ArrayList<Grupo> data;
    private OnItemClickListener clickListener;
    private OnItemLongClickListener longClickListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups);

        clickListener = new OnItemClickListener() {
            @Override
            public void onClickListener(int position) {
                rankingCallback();
            }
        };

        longClickListener = new OnItemLongClickListener() {
            @Override
            public void onLongClickListener(int position) {
                showDialog();
            }
        };

        recyclerView = findViewById(R.id.recyclerview_groups);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);

        adapter = new ListGroupsAdapter(data, clickListener, longClickListener);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_create_group:
                startActivity(new Intent(GroupsActivity.this, CreateGroupActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void rankingCallback(){
        Intent intent = new Intent(GroupsActivity.this, RankingActivity.class);
        startActivity(intent);
    }

    public void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(GroupsActivity.this);
        builder.setMessage(R.string.dialog_message);

        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                /******* Borrar el grupo de firebase *******/
            }
        });

        builder.setNegativeButton(R.string.no, null);

        builder.create().show();
    }
}
