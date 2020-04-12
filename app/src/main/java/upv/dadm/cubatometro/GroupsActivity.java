package upv.dadm.cubatometro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import upv.dadm.cubatometro.Database.DAO;
import upv.dadm.cubatometro.Lib.OnItemClickListener;
import upv.dadm.cubatometro.Lib.OnItemLongClickListener;
import upv.dadm.cubatometro.Listeners.GroupsListener;
import upv.dadm.cubatometro.adapter.ListGroupsAdapter;
import upv.dadm.cubatometro.entidades.Grupo;
import upv.dadm.cubatometro.entidades.User;

public class GroupsActivity extends AppCompatActivity {
    private ListGroupsAdapter adapter;
    private RecyclerView recyclerView;
    private ArrayList<Grupo> data = new ArrayList<>();
    private OnItemClickListener clickListener;
    private OnItemLongClickListener longClickListener;
    private DAO dao = new DAO();
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups);

        mAuth = FirebaseAuth.getInstance();

        /* Listener que cambia de actividad cuando se pulsa sobre un grupo */
        clickListener = new OnItemClickListener() {
            @Override
            public void onClickListener(int position) {
                rankingIntent();
            }
        };

        /* Listener que permite borrar un grupo cuando se mantiene pulsado sobre él */
        longClickListener = new OnItemLongClickListener() {
            @Override
            public void onLongClickListener(int position) {
                showDialog();
            }
        };

        recyclerView = findViewById(R.id.recyclerview_groups);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);

        dao.getGroups(mAuth.getCurrentUser().getUid(), new GroupsListener() {
            @Override
            public void onGroupsReceived(List<Grupo> grupos) {
                data = (ArrayList<Grupo>) grupos;
                adapter = new ListGroupsAdapter((ArrayList<Grupo>) data, clickListener, longClickListener);
                recyclerView.setAdapter(adapter);            }

            @Override
            public void onError(Throwable error) {

            }
        });
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

    public void rankingIntent(){
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
